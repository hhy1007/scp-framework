/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.datasource.druid;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.eg.egsc.framework.dao.intercepter.DialectHelper;
import com.eg.egsc.framework.dao.intercepter.PaginationInterceptor;
import com.eg.egsc.framework.dao.intercepter.PostgreSqlHelper;

/**
 * 分页拦截器插件配置
 * 
 * @author gaoyanlong
 * @since 2018年1月19日
 */
@Configuration
@MapperScan("com.eg.egsc.dao*")
public class MybatisPlusConfig {
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		DialectHelper dialectHelper = new PostgreSqlHelper();
		paginationInterceptor.setDialectHelper(dialectHelper);
		return paginationInterceptor;
	}
}
