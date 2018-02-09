/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.auth.web;

import com.eg.egsc.common.component.auth.model.User;

/**
 * Security Context
 * 
 * @author gaoyanlong
 * @since 2018年1月4日
 */
public class SecurityContext {

  private static ThreadLocal<User> userThreadLocal = new ThreadLocal<>();
  private static ThreadLocal<String> tokenLocal = new ThreadLocal<>();

  private SecurityContext() {

  }

  /**
   * @deprecated Replaced by @see #getUserPrincipal() since v1.0.1
   */
  @Deprecated
  public static User getCurrenctUser() {
    return userThreadLocal.get();
  }

  public static String getUserId() {
    return getUserPrincipal() != null ? getUserPrincipal().getUserId() : null;
  }

  public static String getUserName() {
    return getUserPrincipal() != null ? getUserPrincipal().getUserName() : null;
  }

  public static User getUserPrincipal() {
    return userThreadLocal.get();
  }

  public static void setUserPrincipal(User user) {
    userThreadLocal.set(user);
  }

  public static String getToken() {
    return tokenLocal.get();
  }

  public static void setToken(String token) {
    tokenLocal.set(token);
  }

  public static String getCourtUuid() {
    return getUserPrincipal() != null ? getUserPrincipal().getCourtUuid() : null;
  }
}
