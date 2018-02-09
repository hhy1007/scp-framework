/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.job.support;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.eg.egsc.common.component.job.model.BusinessJobConfig;

/**
 * QuartzAdapter
 * 
 * @author songjie
 * @since 2018年1月17日
 */
@Component
public class QuartzAdapter {

	private static final Logger logger = LoggerFactory
			.getLogger(QuartzAdapter.class);

	/**
	 * 获取job配置信息
	 * 
	 * @param applicationContext
	 * @return List<BusinessJobConfig>
	 */
	public List<BusinessJobConfig> findAllJobConfigs(
			ApplicationContext applicationContext) {

		List<BusinessJobConfig> jobInfos = new ArrayList<BusinessJobConfig>();

		JobConfigService jobConfigService = null;
		try {
			jobConfigService = (JobConfigService) applicationContext
					.getBean("jobConfig");
			if (jobConfigService != null) {
				jobInfos = jobConfigService.getAllJobConfigs();
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}

		if (jobInfos == null) {
			logger.info("没有得到JOB配置信息！");
			jobInfos = new ArrayList<BusinessJobConfig>();
		}
		return jobInfos;
	}

}
