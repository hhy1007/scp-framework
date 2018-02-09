/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.service.core.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * CoreConfig
 * @author wanghongben
 * @since 2018年1月25日
 */
@Configuration
@EnableHystrix
@EnableFeignClients(basePackages = { "com.eg.egsc.**.feign.**",
		"com.eg.egsc.**.api.**" })
@Import({ SwaggerConfig.class })
public class CoreConfig {

}
