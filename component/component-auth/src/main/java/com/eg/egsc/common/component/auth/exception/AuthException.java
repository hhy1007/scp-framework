/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.auth.exception;

import com.eg.egsc.common.exception.CommonException;

/**
 * 认证与鉴权组件异常定义
 * 
 * @author gaoyanlong
 * @since 2018年1月3日
 */
public class AuthException extends CommonException {

  /**
   * @Field long serialVersionUID 
   */
  private static final long serialVersionUID = 1L;

  /**
   * @param message
   */
  public AuthException(String message) {
    super(message);
  }
  
  /**
   * @param message
   */
  public AuthException(String message, Throwable cause) {
    super(null, message, null, cause);
  }

  /**
   * @param code
   * @param message
   */
  public AuthException(String code, String message) {
    super(code, message);
  }

  /**
   * @param code
   * @param message
   * @param values
   * @param cause
   */
  public AuthException(String code, String message, Object[] values, Throwable cause) {
    super(code, message, values, cause);
  }

}
