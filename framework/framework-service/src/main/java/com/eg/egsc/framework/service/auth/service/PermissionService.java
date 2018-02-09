/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.service.auth.service;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eg.egsc.common.component.auth.model.User;
import com.eg.egsc.common.component.auth.utils.RequestIgnoreList;
import com.eg.egsc.common.component.auth.web.SecurityContext;
import com.eg.egsc.common.constant.CommonConstant;

/**
 * 鉴权服务
 * 
 * @author gaoyanlong
 * @since 2017年12月20日
 */
@Service
public class PermissionService {

  private static final Logger logger = LoggerFactory.getLogger(PermissionService.class);

  @Autowired
  private RequestIgnoreList requestIgnoreList;

  public boolean hasPermission(ServletRequest request, ServletResponse response) {

    HttpServletRequest httpRequest = (HttpServletRequest) request;
    String requestURI = httpRequest.getRequestURI();
    logDebug("Request URI: %s", requestURI);
    logDebug("Response charset: %s, contentType: %s", response.getCharacterEncoding(),
        response.getContentType());

    if (requestIgnoreList.match(request)) {
      return true;
    }

    if (isFromOtherApp(request)) {
      return true;
    }

    User user = SecurityContext.getUserPrincipal();
    if (user != null) {

      List<String> serviceIds = user.getServiceIds();

      if (serviceIds != null) {
        for (String serviceId : serviceIds) {
          if (!StringUtils.isEmpty(serviceId) && serviceId.startsWith("/")
              && requestURI.startsWith(serviceId)) {
            return true;
          }
        }
      } else {
        logger.error("用户服务权限列表为空！");
      }
    } else {
      logger.error("用户信息为空！");
    }

    return false;
  }

  /**
   * 是否系统间调用
   * 
   * @param request
   * @return boolean
   */
  private boolean isFromOtherApp(ServletRequest request) {

    HttpServletRequest httpRequest = ((HttpServletRequest) request);
    String requestURI = httpRequest.getRequestURI();
    return isApiURI(requestURI);
  }

  /**
   * Is API url
   * 
   * @param requestURI
   * @return boolean
   */
  protected boolean isApiURI(String requestURI) {
    if (requestURI.matches("/.*?/api/.*")) {
      return true;
    }
    return false;
  }

  private void logDebug(String format, Object... args) {
    if (logger.isDebugEnabled()) {
      logger.debug(String.format(format, args));
    }
  }

  @SuppressWarnings("unused")
  private String getTokenFromCookie(ServletRequest request) {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    Cookie[] cookies = httpRequest.getCookies();
    String token = null;

    if (null != cookies) {
      for (Cookie cookie : cookies) {
        if (CommonConstant.TOKEN_COOKIE_NAME.equals(cookie.getName())) {
          token = cookie.getValue();
        }
      }
    }

    return token;
  }
}
