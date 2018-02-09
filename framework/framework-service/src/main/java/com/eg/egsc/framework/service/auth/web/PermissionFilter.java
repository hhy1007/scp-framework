/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.service.auth.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.eg.egsc.common.component.utils.JsonUtil;
import com.eg.egsc.framework.service.auth.service.PermissionService;
import com.eg.egsc.framework.service.util.HttpServletUtil;
import com.eg.egsc.framework.service.util.MessageUtils;

/**
 * Permission Filter
 * 
 * @author gaoyanlong
 * @since 2018年1月11日
 */
public class PermissionFilter implements Filter {
  protected final Logger logger = LoggerFactory.getLogger(PermissionFilter.class);

  private PermissionService permissionService;

  public PermissionService getPermissionService() {
    return permissionService;
  }

  public void setPermissionService(PermissionService permissionService) {
    this.permissionService = permissionService;
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
   */
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    logger.debug("init");
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
   * javax.servlet.FilterChain)
   */
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletUtil.initResponse((HttpServletResponse) response);

    if (permissionService.hasPermission(request, response)) {
      chain.doFilter(request, response);
    } else {
      this.populateRespons401(request, response);
    }

  }

  private void populateRespons401(ServletRequest request, ServletResponse response)
      throws IOException {
    HttpServletResponse httpResponse = (HttpServletResponse) response;
    httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());

    PrintWriter print = response.getWriter();
    String msgKey = "framework.auth.unauthorized";
    String jsonResponse = JsonUtil.toJson(
        MessageUtils.getResponseByMessageKey(msgKey, "非法请求【没有权限】", (HttpServletRequest) request));
    logDebug("Response: %s", jsonResponse);
    print.write(jsonResponse);
    return;
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.servlet.Filter#destroy()
   */
  @Override
  public void destroy() {
    logger.debug("destroy");
  }

  private void logDebug(String format, Object... args) {
    if (logger.isDebugEnabled()) {
      logger.debug(String.format(format, args));
    }
  }
}
