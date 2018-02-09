/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.service.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.eg.egsc.framework.service.auth.service.AuthenticationService;
import com.eg.egsc.framework.service.auth.service.PermissionService;
import com.eg.egsc.framework.service.auth.web.AuthenticationFilter;
import com.eg.egsc.framework.service.auth.web.PermissionFilter;

/**
 * 认证与鉴权Filter配置类
 * 
 * @author douguoqiang
 * @since 2018年1月16日
 */
@Configuration
@ConditionalOnExpression("${egsc.config.auth.enabled:false} && !'${spring.application.name}'.equals('gateway-server')")
public class AuthFilterConfig {

  @Autowired
  private ApplicationContext applicationContext;
  
  @Bean
  public FilterRegistrationBean jwtAuthFilterRegistrationBean() {
    AuthenticationService jWTAuthService =
        (AuthenticationService) applicationContext.getBean(AuthenticationService.class);
    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
    AuthenticationFilter jwtFilter = new AuthenticationFilter();
    registrationBean.setFilter(jwtFilter);
    registrationBean.setOrder(Integer.MAX_VALUE - 1);
    registrationBean.addUrlPatterns("/*");
    jwtFilter.setAuthenticationService(jWTAuthService);
    return registrationBean;
  }
  
  @Bean
  public FilterRegistrationBean permissionFilterRegistrationBean() {
	  PermissionService permissionService = (PermissionService) applicationContext
			.getBean(PermissionService.class);
  	FilterRegistrationBean registrationBean = new FilterRegistrationBean();
  	PermissionFilter permissionFilter = new PermissionFilter();
  	registrationBean.setFilter(permissionFilter);
  	registrationBean.setOrder(Integer.MAX_VALUE);
  	registrationBean.addUrlPatterns("/*");
  	permissionFilter.setPermissionService(permissionService);
  	return registrationBean;
  }
}
