/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.dao.base;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate conifg
 * @author gaoyanlong
 * @since 2018年1月25日
 */
@Configuration
@ComponentScan(basePackages = { "com.eg.egsc" })
@MapperScan(basePackages = { "com.eg.egsc" })
public class Config {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplateBuilder()
				.additionalMessageConverters(
						new MappingJackson2HttpMessageConverter())
				.additionalMessageConverters(new FormHttpMessageConverter())
				.build();
	}
}
