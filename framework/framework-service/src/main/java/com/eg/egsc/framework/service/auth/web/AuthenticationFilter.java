/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.service.auth.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eg.egsc.common.constant.CommonConstant;
import com.eg.egsc.common.exception.CommonException;
import com.eg.egsc.framework.service.auth.service.AuthenticationService;
import com.eg.egsc.framework.service.util.HttpServletUtil;

/**
 * @Class Name JWTAuthFilter
 * @author gaoyanlong
 * @since 2017年12月20日
 */
public class AuthenticationFilter implements Filter {

  private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

  private AuthenticationService authenticationService;

  public AuthenticationService getAuthenticationService() {
    return authenticationService;
  }

  public void setAuthenticationService(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @Override
  public void destroy() {
    logger.info("destroy");
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
      throws IOException, ServletException {

    try {
      HttpServletUtil.initResponse(resp);

      if (authenticationService.authorize(req, resp)) {
        chain.doFilter(req, resp);
      } else {
        HttpServletUtil.populateResponse401(req, resp);
      }
    } catch (CommonException e) {
      logger.error(e.getMessage(), e);
      HttpServletUtil.populateResponse500(e.getCode(), e.getMessage(), req, resp);
      return;
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      HttpServletUtil.populateResponse500(CommonConstant.MESSAGE_KEY_INTERNAL_ERROR, "系统错误，请联系管理员！",
          req, resp);
      return;
    }
  }

  @Override
  public void init(FilterConfig arg0) throws ServletException {
    logger.info("init");
  }
}
