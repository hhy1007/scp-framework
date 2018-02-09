/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.service.util;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Spring Boot环境配置项工具类
 * 
 * @author douguoqiang
 * @since 2018-1-10
 */
@Component
public class SpringEnvUtil implements EnvironmentAware {
	
	private static final String EGSC_CONFIG_KEY_PERFIX = "egsc.config.";
	
	private Environment environment;

	/**
	 * 通过配置项的键获取对应的值
	 * 
	 * @param key 配置项的key键
	 * @return 返回配置项的值
	 */
	public String getProperty(String key) {
		RelaxedPropertyResolver rResolver = new RelaxedPropertyResolver(this.getEnvironment(), EGSC_CONFIG_KEY_PERFIX);
		return rResolver.getProperty(key);
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	public Environment getEnvironment() {
		return environment;
	}

}
