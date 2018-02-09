/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.client.core;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Client Configuration
 * @author gaoyanlong
 * @since 2018年1月9日
 */
@Configuration
@ComponentScan(basePackages = { "com.eg.egsc" })
public class ClientConfig {

	/**
	 * Get Rest Template
	 * @return RestTemplate
	 */
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplateBuilder()
				.additionalMessageConverters(
						new MappingJackson2HttpMessageConverter())
				.additionalMessageConverters(new FormHttpMessageConverter())
				.build();
	}
}
