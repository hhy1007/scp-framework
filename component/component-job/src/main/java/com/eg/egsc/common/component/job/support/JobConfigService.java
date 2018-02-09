/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.job.support;

import java.util.List;

import com.eg.egsc.common.component.job.model.BusinessJobConfig;

/**
 * JobInfoService接口
 * 
 * @author songjie
 * @since 2018年1月17日
 */
public interface JobConfigService {

	/**
	 * 获取job配置信息
	 * 
	 * @return List<BusinessJobConfig>
	 */
	List<BusinessJobConfig> getAllJobConfigs();

}
