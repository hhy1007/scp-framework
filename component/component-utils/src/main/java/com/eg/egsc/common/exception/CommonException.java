/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.exception;

/**
 * Common Exception
 * 
 * @author douguoqiang
 * @since 2018年1月15日
 */
public class CommonException extends BaseException {

  /**
   * @Field long serialVersionUID
   */
  private static final long serialVersionUID = -4527567935254966321L;

  public CommonException(String code) {
    super(code, null, null, null);
  }

  public CommonException(String code, String message) {
    super(code, message, null, null);
  }

  public CommonException(String code, String message, Object[] values) {
    super(code, message, values, null);
  }

  public CommonException(String code, String message, Object[] values, Throwable cause) {
    super(code, message, values, cause);
  }
}
