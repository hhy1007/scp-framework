/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.job.support;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * JobManager
 * 
 * @author songjie
 * @since 2018年1月17日
 */
@Component
public class JobManager {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(JobManager.class);

	public static final String TRIGGER_GROUP_NAME = "egsc_trigger";

	public static final String TRIGGER_NAME_PERFIX = "egsc_trigger_name";

	public static final String QUEUE_OBJECT_KEY = "queue_object_key";

	/**
	 * Private constructor
	 */
	private JobManager() {
	}

	/**
	 * 添加一个定时任务
	 * 
	 * @param job
	 *            void
	 */
	public void addJob(AbstractJob job) {
		Scheduler scheduler = SchedulerManager.getInstanse();
		String jobName = job.getName();
		String triggerName = TRIGGER_NAME_PERFIX + " at " + job.getName();
		LOGGER.info("Add a job[" + jobName + "] start...");
		try {
			JobDetail jobDetail = newJob(job.getClass())
					.withIdentity(jobName, job.getGroupName())
					.usingJobData(job.getAttributeMap()).build();
			Trigger trigger = newTrigger()
					.withIdentity(triggerName, TRIGGER_GROUP_NAME)
					.withSchedule(cronSchedule(job.getCronExpression()))
					.build();
			scheduler.scheduleJob(jobDetail, trigger);
			if (!scheduler.isShutdown()) {
				scheduler.start();
			}

		} catch (Exception e) {
			LOGGER.error("Add a time in [" + job.getCronExpression()
					+ "] triggered of the job [" + job.getName()
					+ "] is error!", e);
		}
		LOGGER.info("Add a job[" + jobName + "] end.");
	}

	/**
	 * 修改一个任务的触发时间
	 * 
	 * @param jobName
	 *            任务名称
	 * @param cronExpression
	 *            void 定时器表达式
	 */
	public void modifyJobTime(String jobName, String cronExpression) {
		Scheduler scheduler = SchedulerManager.getInstanse();
		LOGGER.info("Modify a job[" + jobName + "] start...");
		try {
			String triggerName = TRIGGER_NAME_PERFIX + " at " + jobName;
			TriggerKey triggerKey = TriggerKey.triggerKey(triggerName,
					TRIGGER_GROUP_NAME);
			Trigger trigger = scheduler.getTrigger(triggerKey);
			if (trigger != null) {
				CronTrigger ct = (CronTrigger) trigger;
				// 修改时间
				ct.getTriggerBuilder()
						.withSchedule(
								CronScheduleBuilder
										.cronSchedule(cronExpression))
						.startNow().build();
				// 重启触发器
				scheduler.resumeTrigger(triggerKey);
			}
		} catch (Exception e) {
			LOGGER.error("Add a time in [" + cronExpression
					+ "] triggered of the job [" + jobName + "] is error!", e);
		}
		LOGGER.info("Modify a job[" + jobName + "] end.");
	}

	/**
	 * 移除一个任务
	 * 
	 * @param jobName
	 * @param jobGroupName
	 * @return boolean
	 */
	public boolean removeJob(String jobName, String jobGroupName) {
		Scheduler scheduler = SchedulerManager.getInstanse();
		LOGGER.info("Remove a job[" + jobName + "] start...");
		boolean b = false;
		try {
			JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
			b = scheduler.deleteJob(jobKey); // 删除任务
		} catch (Exception e) {
			LOGGER.error("Delete a job [" + jobName + "] is error!", e);
		}
		LOGGER.info("Remove a job[" + jobName + "] end.");
		return b;
	}

	/**
	 * 查询是否某任务是否已经存在
	 * 
	 * @param jobName
	 * @param jobGroupName
	 * @return boolean
	 */
	public boolean isJobExist(String jobName, String jobGroupName) {
		Scheduler scheduler = SchedulerManager.getInstanse();
		LOGGER.info("Find a job[" + jobName + "] start...");
		try {
			GroupMatcher<JobKey> groupMatcher = GroupMatcher
					.groupContains(jobGroupName);
			Set<JobKey> jobKeySet = scheduler.getJobKeys(groupMatcher);
			for (JobKey jobKey : jobKeySet) {
				if (jobKey.getName().equals(jobName)) {
					return true;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Find a job[" + jobName + "] error.", e);
		}
		LOGGER.info("Find a job[" + jobName + "] end.");
		return false;
	}

	/**
	 * 查询所有执行中的任务
	 * 
	 * @return List<Object[]>
	 */
	public List<Object[]> getAllRunningJob() {
		Scheduler scheduler = SchedulerManager.getInstanse();
		List<Object[]> list = null;
		try {
			List<JobExecutionContext> runningJobs = scheduler
					.getCurrentlyExecutingJobs();
			if (CollectionUtils.isEmpty(runningJobs)) {
				return null;
			}
			list = new ArrayList<Object[]>(runningJobs.size());
			for (JobExecutionContext jeContext : runningJobs) {
				JobDetail jobDetail = jeContext.getJobDetail();
				Trigger jobTrigger = jeContext.getTrigger();
				JobDataMap jobDataMap = jobDetail.getJobDataMap();
				list.add(new Object[] { jobDetail, jobTrigger, jobDataMap });
			}
		} catch (SchedulerException e) {
			LOGGER.error(e.getMessage());
		}
		return list;
	}
}
