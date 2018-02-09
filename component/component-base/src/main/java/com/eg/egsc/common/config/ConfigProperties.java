/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 通过Getter的方式暴露配置文件中的键值对信息
 * 
 * @author songjie
 * @since 2018年1月22日
 */
@Component(value = "configPropertiesFactory")
public class ConfigProperties {
	//应用或组件英文简称
	private String appshortname;

	@Autowired
	private Environment env;

	/**
	 * 读取配置文件
	 * @return ConfigProperties
	 */
	@Bean
	public ConfigProperties configProperties() {
		ConfigProperties properties = new ConfigProperties();
		appshortname = env.getProperty("egsc.config.appshortname");
		properties.setAppshortname(appshortname);
		return properties;
	}

	/**
	 * 取得应用与组件简称
	 * @return String
	 */
	public String getAppshortname() {
		return appshortname;
	}
  /**
   * 设置应用与组件简称
   * @return String
   */
	public void setAppshortname(String appshortname) {
		this.appshortname = appshortname;
	}

}
