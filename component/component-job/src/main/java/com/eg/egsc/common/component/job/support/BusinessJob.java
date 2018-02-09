/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.job.support;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationContext;

import com.eg.egsc.common.component.auth.service.SuperviserService;
import com.eg.egsc.common.component.job.model.BusinessJobConfig;
import com.eg.egsc.common.component.job.util.IPUtils;
import com.eg.egsc.common.component.redis.RedisUtils;

/**
 * job基类
 * 
 * @author songjie
 * @since 2018年1月17日
 */
public class BusinessJob extends AbstractJob {

  private static final Logger logger = LoggerFactory.getLogger(BusinessJob.class);
  public static final String BUSINESS_JOB_GROUP_NAME = JOB_GROUP_NAME + "business_jobs";
  private String jobName;
  private String cronExpression;

  /**
   * Constructor
   */
  public BusinessJob() {}

  /**
   * Constructor
   * 
   * @param jobName
   * @param cronExpression
   */
  public BusinessJob(String jobName, String cronExpression) {
    this.jobName = jobName;
    this.cronExpression = cronExpression;
  }

  /**
   * job执行类
   * 
   * @param context
   * @throws JobExecutionException if there is an exception while executing the job.
   */
  @Override
  @SuppressWarnings("unchecked")
  public void execute(JobExecutionContext context) throws JobExecutionException {
    JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
    BusinessJobConfig businessJobInfo = (BusinessJobConfig) jobDataMap.get("businessJobInfo");
    ApplicationContext appContext = (ApplicationContext) jobDataMap.get("applicationContext");
    RedisUtils redisUtils = (RedisUtils) appContext.getBean("redisUtils");
    JobDetail jobDetail = context.getJobDetail();
    String jobName = "";
    if (null != jobDetail) {
      jobName = jobDetail.getKey().getName();
    }
    String serviceName = businessJobInfo.getServiceName();
    String methodName = businessJobInfo.getMethodName();
    Object bjs = appContext.getBean(serviceName);
    String appName = appContext.getEnvironment().getProperty("spring.application.name");
    if (StringUtils.isBlank(appName)) {
      logger.error("error 缺少配置 spring.application.name");
      return;
    }
    Class<?> clazz = bjs.getClass();
    try {
      Method m1 = clazz.getDeclaredMethod(methodName);
      if (null == m1) {
        logger.error("execute Job Method is null");
        return;
      }
      // 添加调度日志
      logger.info("execute start:" + businessJobInfo);
      if (redisUtils.isvalidLockBusiness(jobName, 30)) {
        logger.info("JOB正在运行中，jobName = " + jobName);
        return;
      } else {
        redisUtils.unlockBusiness(jobName);
      }
      if (!redisUtils.lockBusiness(jobName, 30)) {
        logger.info("JOB加锁失败，jobName = " + jobName);
        return;
      }

      // 查询redis中是否已存在job
      Map<String, String> map = (Map<String, String>) redisUtils.get(jobName);
      // job是否正在执行
      if (!isRunning(appContext, appName, map)) {

        if (businessJobInfo.isRunAsAdmin()) {
          SuperviserService superviserService =
              (SuperviserService) appContext.getBean("superviserServiceImpl");
          superviserService.loginAsAdmin();
        }

        // 如果job非运行状态，更新redis中的jobMap，执行job
        Map<String, String> jobMap = new HashMap<String, String>();
        jobMap.put("jobName", jobName);
        jobMap.put("ip", IPUtils.getIp());
        redisUtils.set(jobName, jobMap);
        m1.invoke(bjs);
        // job执行完删除则jobMap
        redisUtils.del(jobName);
        logger.info("execute end:" + businessJobInfo);
      }
    } catch (NoSuchMethodException | SecurityException | IllegalAccessException
        | IllegalArgumentException | InvocationTargetException e) {
      logger.error(e.getMessage(), e);
    }
    redisUtils.unlockBusiness(this.jobName);// 对当前job解锁
  }

  /**
   * job是否正在运行
   * 
   * @param appContext
   * @param appName
   * @param jobMap
   * @return boolean true.是, false.否
   */
  private boolean isRunning(ApplicationContext appContext, String appName,
      Map<String, String> jobMap) {
    if (null == appContext.getBean(DiscoveryClient.class) || null == jobMap
        || StringUtils.isBlank(appName)) {
      return false;
    }
    String ip = jobMap.get("ip");
    if (StringUtils.isNotBlank(ip)) {
      // 查询到此job正在进行中，判断此job的执行节点是否正常【是否宕机】
      DiscoveryClient discoveryClient = (DiscoveryClient) appContext.getBean(DiscoveryClient.class);
      List<ServiceInstance> instanceList = discoveryClient.getInstances(appName);
      if (CollectionUtils.isEmpty(instanceList)) {
        return false;
      }
      for (ServiceInstance instance : instanceList) {
        if (null != instance && ip.equals(instance.getHost())) {
          logger.error("JOB正在运行中，jobName = " + jobMap.get("jobName") + ", 执行节点ip = " + ip);
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public String getCronExpression() {
    return this.cronExpression;
  }

  @Override
  public String getName() {
    return this.jobName;
  }

  @Override
  public String getGroupName() {
    return BUSINESS_JOB_GROUP_NAME;
  }
}
