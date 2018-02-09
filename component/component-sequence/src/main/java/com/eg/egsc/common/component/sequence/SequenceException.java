/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.sequence;

import com.eg.egsc.common.exception.CommonException;

/**
 * Sequence Exception
 * 
 * @author gaoyanlong
 * @since 2018年1月4日
 */
public class SequenceException extends CommonException {

  /**
   * @Field long serialVersionUID 
   */
  private static final long serialVersionUID = 1L;

  /**
   * @param message
   */
  public SequenceException(String message) {
    super(message);
  }

  /**
   * @param code
   * @param message
   */
  public SequenceException(String code, String message) {
    super(code, message);
  }

  /**
   * @param code
   * @param message
   * @param values
   * @param cause
   */
  public SequenceException(String code, String message, Object[] values, Throwable cause) {
    super(code, message, values, cause);
  }

}
