/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.auth.utils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eg.egsc.common.constant.CommonConstant;

/**
 * URL Ignore List
 * 
 * @author gaoyanlong
 * @since 2018年1月15日
 */
@Component
public class RequestIgnoreList {

  private static final Logger logger = LoggerFactory.getLogger(RequestIgnoreList.class);

  @Autowired
  private WhiteResourceList whiteResourceList;

  private static List<String> ignoreCommonUrls = new ArrayList<>(16);
  static {
    ignoreCommonUrls.add(".*/login");
    ignoreCommonUrls.add(".*/login[/?].*");
    ignoreCommonUrls.add(".*swagger.*");
    ignoreCommonUrls.add(".*/api-docs.*");
    ignoreCommonUrls.add(".*/api/user/queryFunctionAuthority");
    ignoreCommonUrls.add(".*/api/account/loginByPasswordOrVerifycode");
  }

  protected static boolean containsUrl(String url) {
    boolean result = false;
    for (String ignoreUrl : ignoreCommonUrls) {
      if (url.matches(ignoreUrl)) {
        result = true;
        break;
      }
    }

    return result;
  }

  /**
   * 与白名单进行比较
   * 
   * @param request
   * @return boolean
   */
  public boolean match(ServletRequest request) {
    boolean match = false;
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    String requestURI = httpRequest.getRequestURI();

    match = containsUrl(requestURI);

    for (WhiteResourceItem wr : whiteResourceList.getList()) {
      match = match || match(wr, request);
    }

    if (logger.isDebugEnabled()) {
      logger.debug(String.format("%s is%s in white list", requestURI, match ? "" : " not"));
    }
    return match;
  }

  /**
   * 如果三个配置fronttype, sourceips 和 url 都有值， 三者之间的是与的关系，都满足才放行
   * 
   * @param match
   * @param httpRequest
   * @param requestURI
   * @param wr
   * @return boolean
   */
  protected boolean match(WhiteResourceItem wr, ServletRequest request) {
    HttpServletRequest httpRequest = (HttpServletRequest) request;

    String frontType = httpRequest.getHeader(CommonConstant.FRONT_TYPE);
    String requestIP = httpRequest.getRemoteAddr();
    String requestURI = httpRequest.getRequestURI();

    if (logger.isDebugEnabled()) {
      logger.debug(
          String.format("Request front:%s, ip: %s, uri: %s", frontType, requestIP, requestURI));
    }

    return match(wr, frontType, requestIP, requestURI);
  }

  /**
   * 如果三个配置fronttype, sourceips 和 url 都有值， 三者之间的是与的关系，都满足才放行
   * 
   * @param wr
   * @param match
   * @param requestIP
   * @param requestURI
   * @return boolean
   */
  @SuppressWarnings("null")
  protected boolean match(WhiteResourceItem wr, String requestFrontType, String requestIP,
      String requestURI) {

    boolean match = false;
    List<String> fromIps = wr.getFromips();
    List<String> fromUrls = wr.getUrls();
    String fromFront = wr.getFromfront();
    if (logger.isDebugEnabled()) {
      logger.debug(String.format("white list - fromfront: %s, fromips: %s, uris: %s", fromFront,
          fromIps, fromUrls));
    }

    if (StringUtils.isEmpty(fromFront) || fromFront.equalsIgnoreCase(requestFrontType)) {
      match = true;
    } else {
      match = false;
    }

    if (match && fromIps != null && !fromIps.isEmpty()) {
        match = contains(fromIps, requestIP);
    }

    if (match) {
      if (fromUrls == null || fromUrls.isEmpty()) {
        match = false;
      } else {
        match = contains(fromUrls, requestURI);
      }
    }

    return match;
  }

  /**
   * contain any
   * 
   * @param whiteIPs
   * @param requestIP
   * @return boolean
   */
  protected static boolean contains(List<String> whiteIPs, String requestIP) {
    boolean contain = false;

    if (whiteIPs != null) {
      for (String whiteIP : whiteIPs) {
        if (StringUtils.contains(requestIP, whiteIP)) {
          contain = true;
          break;
        }
      }
    }
    return contain;
  }
}
