/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.job.support;

import java.util.UUID;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.springframework.util.CollectionUtils;

/**
 * Abstract job class.
 * 
 * @author songjie
 * @since 2018年1月17日
 */
abstract class AbstractJob implements Job {

	public static final String JOB_NAME_PREFIX = "egsc_job_name_";

	public static final String JOB_GROUP_NAME = "egsc_job_group_name_";

	private JobDataMap attributeMap;

	/**
	 * Constructor
	 */
	public AbstractJob() {
	}

	public abstract String getName();

	public abstract String getGroupName();

	public abstract String getCronExpression();

	protected String genDefaultName() {
		return JOB_NAME_PREFIX + getUUID();
	}

	/**
	 * 获取job前缀名
	 * 
	 * @return String
	 */
	protected String getGroupDefaultName() {
		return JOB_NAME_PREFIX;
	}

	/**
	 * 设置属性
	 * 
	 * @param name
	 * @param value
	 *            void
	 */
	public void addAttribute(String name, String value) {
		if (CollectionUtils.isEmpty(attributeMap)) {
			attributeMap = new JobDataMap();
		}
		attributeMap.put(name, value);
	}

	/**
	 * 获取属性
	 * 
	 * @param name
	 * @return Object
	 */
	public Object getAttribute(String name) {
		if (CollectionUtils.isEmpty(attributeMap)
				|| !attributeMap.containsKey(name)) {
			return null;
		}
		return attributeMap.get(name);
	}

	/**
	 * 获取JobDataMap
	 * 
	 * @return JobDataMap
	 */
	public JobDataMap getAttributeMap() {
		if (CollectionUtils.isEmpty(attributeMap)) {
			return new JobDataMap();
		}
		return attributeMap;
	}

	/**
	 * 设置JobDataMap
	 * 
	 * @param attributeMap
	 *            void
	 */
	public void setAttributeMap(JobDataMap attributeMap) {
		this.attributeMap = attributeMap;
	}

	/**
	 * 获取uuid
	 * 
	 * @return String
	 */
	private static final synchronized String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
