/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.client.dto;

import java.util.Map;

import com.eg.egsc.common.component.utils.JsonUtil;

/**
 * ResponseDto
 * @author wanghongben
 * @since 2018年1月24日
 */
public class ResponseDto {

  private String code;
  private Object data;
  private String message;

  public String getCode() {
    return code == null ? "" : code.trim();
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Object getData() {
    return data;
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public <T> T getData(Class<T> clz) {
    if (data instanceof Map) {
      return JsonUtil.fromMap((Map) data, clz);
    }

    return (T) data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "ResponseDto [code=" + code + ", data=" + data + ", message=" + message + "]";
  }

  public ResponseDto(String code, Object data, String message) {
    super();
    this.code = code;
    this.data = data;
    this.message = message;
  }

  public ResponseDto() {
    super();
  }
}
