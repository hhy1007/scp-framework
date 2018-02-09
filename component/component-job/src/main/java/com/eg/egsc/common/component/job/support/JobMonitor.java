/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.job.support;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobDataMap;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Component;

import com.eg.egsc.common.component.job.model.BusinessJobConfig;
import com.eg.egsc.common.component.job.model.JobStatus;

/**
 * JobMonitor
 * 
 * @author songjie
 * @since 2018年1月17日
 */
@Component
public class JobMonitor implements ApplicationContextAware {

	@Autowired
	private QuartzAdapter quartzAdapterImpl;

	private ApplicationContext applicationContext;

	@Autowired
	private JobManager jobManager;

	private boolean active = false;

	/**
	 * job启动类
	 */
	public void start() {
		List<BusinessJobConfig> jobConfigs = quartzAdapterImpl
				.findAllJobConfigs(applicationContext);
		if (!active) {
			// if no managed jobs, ignore
			if (CollectionUtils.isEmpty(jobConfigs)) {
				return;
			}
			// startup all managed jobs.
			for (BusinessJobConfig jobConfig : jobConfigs) {
				if (JobStatus.DISABLE.getValue().equals(jobConfig.getStatus())) {
					continue;
				}
				String jobName = BusinessJob.JOB_NAME_PREFIX
						+ jobConfig.getServiceName() + "#"
						+ jobConfig.getMethodName();
				String jobGroupName = BusinessJob.BUSINESS_JOB_GROUP_NAME;
				if (jobManager.isJobExist(jobName, jobGroupName)) {
					continue;
				}
				String cronExpression = jobConfig.getCronExpression();
				BusinessJob mj = new BusinessJob(jobName, cronExpression);
				JobDataMap attributeMap = new JobDataMap();
				attributeMap.put("businessJobInfo", jobConfig);
				attributeMap.put("applicationContext", applicationContext);
				mj.setAttributeMap(attributeMap);
				jobManager.addJob(mj);
			}
			active = true;
		}
	}

	/**
	 * job停止
	 */
	public void stop() {
		if (active) {
			/*
			 * List<BusinessJobConfig> bjiList = this.getBusinessJobInfoList();
			 * for (BusinessJobConfig bji : bjiList) { String jobName =
			 * AbstractJob.JOB_NAME_PREFIX + bji.getServiceName() + "#" +
			 * bji.getMethodName(); String jobGroupName =
			 * BusinessJob.BUSINESS_JOB_GROUP_NAME;
			 * jobManager.removeJob(jobName, jobGroupName); }
			 */
			active = false;
		}
	}

	/**
	 * job重启
	 */
	public void restart() {
		stop();
		start();
	}

	/**
	 * 是否活动
	 * 
	 * @return boolean
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @return List<BusinessJobConfig>
	 */
	public List<BusinessJobConfig> getBusinessJobInfoList() {
		return null;// nlbpSysBusinessJobInfoDao.findList(new
					// NlbpSysBusinessJobInfoModel());
	}

	/**
	 * Set the ApplicationContext that this object runs in.
	 * 
	 * @param applicationContext
	 *            the ApplicationContext object to be used by this object
	 * @throws ApplicationContextException
	 *             in case of context initialization errors
	 * @throws BeansException
	 *             if thrown by application context methods
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;

	}
}
