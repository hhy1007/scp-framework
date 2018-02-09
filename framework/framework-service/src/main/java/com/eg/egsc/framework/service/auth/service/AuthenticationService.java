/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.service.auth.service;

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
import com.eg.egsc.common.component.auth.service.SuperviserService;
import com.eg.egsc.common.component.auth.utils.JWTUtils;
import com.eg.egsc.common.component.auth.utils.RequestIgnoreList;
import com.eg.egsc.common.component.auth.web.SecurityContext;
import com.eg.egsc.common.component.redis.RedisUtils;
import com.eg.egsc.common.constant.CommonConstant;
import com.eg.egsc.framework.service.util.HttpServletUtil;

/**
 * JWTAuthService
 * 
 * @author gaoyanlong
 * @since 2018年1月22日
 */
@Service
public class AuthenticationService {

  private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

  @Autowired
  private RedisUtils redisUtils;

  @Autowired
  private RequestIgnoreList requestIgnoreList;
  
  @Autowired
  private SuperviserService superviserServiceImpl;

  /**
   * doAuth
   * 
   * @param request
   * @param response
   * @return boolean
   */
  public boolean authorize(ServletRequest request, ServletResponse response) {

    HttpServletRequest httpRequest = (HttpServletRequest) request;
    String requestURI = httpRequest.getRequestURI();
    logDebug("Request URL: %s", requestURI);

    if (requestIgnoreList.match(request)) {
      if (HttpServletUtil.isFromEgscUI(request)) {
        superviserServiceImpl.loginAsAdmin();
      }
      return true;
    }

    if (verifyToken(request, response)) {
      return putTokenAndUserInContext(request, response);
    }

    return false;
  }

  /**
   * verifyToken
   * 
   * @param request
   * @param response
   * @return boolean
   */
  private boolean verifyToken(ServletRequest request, ServletResponse response) {
    boolean isValid = true;
    String authToken = getTokenFromHeader(request);
    logDebug("Get token from header: ", authToken);
    logDebug("Response charset: %s, contentType: %s", response.getCharacterEncoding(),
        response.getContentType());

    // if token does not exist, return 401
    if (StringUtils.isBlank(authToken)) {
      isValid = false;
    }
    // token exists, then check it.
    else {

      logDebug("Start to check token");
      JWTUtils.JWTResult jwtResult = JWTUtils.checkToken(authToken);
      logDebug("End to check token with result : %s", jwtResult);

      // token fails
      if (!jwtResult.isSuccess()) {
        isValid = false;
      }
    }

    return isValid;
  }

  /**
   * putTokenAndUserInContext
   * 
   * @param request
   * @param response
   * @return boolean
   */
  private boolean putTokenAndUserInContext(ServletRequest request, ServletResponse response) {
    String authToken = getTokenFromHeader(request);
    User user = (User) redisUtils.getOrigin(CommonConstant.REDIS_KEY_SSO_USER + authToken);
    logDebug("Get user from redis : %s", user);
    logDebug("Response charset: %s, contentType: %s", response.getCharacterEncoding(),
        response.getContentType());

    if (user == null) {
      logger.warn("user is not in redis");
      return false;
    } else {

      // save it to securedContext
      logDebug("Save user to securedContext");
      SecurityContext.setUserPrincipal(user);
      SecurityContext.setToken(getTokenFromHeader(request));
    }
    return true;
  }

  private void logDebug(String format, Object... args) {
    if (logger.isDebugEnabled()) {
      logger.debug(String.format(format, args));
    }
  }

  /**
   * getTokenFromHeader
   * 
   * @param request
   * @return String
   */
  private String getTokenFromHeader(ServletRequest request) {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    String authToken = httpRequest.getHeader("Authorization");

    logDebug("token : %s", authToken);
    return authToken;
  }

  /**
   * getTokenFromCookie
   * 
   * @param request
   * @return String
   */
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
