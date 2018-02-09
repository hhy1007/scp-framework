/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.service.core.config;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * WebConfig  web组件配置
 * @author wanghongben
 * @since 2018年1月25日
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	private static final Logger log = LoggerFactory
			.getLogger(WebConfig.class);
	@Value("${characterEncoding:UTF-8}")
	protected String characterEncoding;
	@Value("${allow.cross.domain:*}")
	private String allowDomain;

	/**
	 * 初始化
	 * @return WebMvcConfigurer
	 */
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api*").allowedOrigins(allowDomain);
			}
		};
	}

	@Bean
	public FilterRegistrationBean characterEncodingFilter() {

		log.debug("autoconfig characterEncodingFilter ...");

		FilterRegistrationBean reg = new FilterRegistrationBean();

		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding(characterEncoding);
		filter.setForceEncoding(true);
		reg.setFilter(filter);

		reg.addUrlPatterns("/*");
		return reg;
	}

	@Bean
	public StringHttpMessageConverter stringHttpMessageConverter() {

		log.debug("autoconfig stringHttpMessageConverter ...");

		return new StringHttpMessageConverter(
				Charset.forName(characterEncoding));
	}

	@Bean
	public RequestContextListener requestContextListener() {
		log.debug("autoconfig requestContextListener ...");

		return new RequestContextListener();
	}

}
