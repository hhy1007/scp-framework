/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.constant;

/**
 * Common Constant
 * 
 * @author gaoyanlong
 * @since 2018年1月6日
 */
public final class CommonConstant {
  
  private CommonConstant() {
  }

  // authentication & authorization
  public static final String REDIS_KEY_SSO_USER = "egsc_scp_key_sso_user_";
  public static final int JWT_MAX_AGE_MINUTES = 30;
  public static final String TOKEN_COOKIE_NAME = "jwt-token";

  // error code
  public static final String SUCCESS_CODE = "00000";
  public static final String DEFAULT_SYS_ERROR_CODE = "00099";

  // system code
  public static final String FRAMEWORK_SYS_CODE = "COMMON";
  
  // front_type
  public static final String FRONT_TYPE = "FrontType";
  
  // message_key_internal_error
  public static final String MESSAGE_KEY_INTERNAL_ERROR = "framework.auth.login.internal.error";
}
