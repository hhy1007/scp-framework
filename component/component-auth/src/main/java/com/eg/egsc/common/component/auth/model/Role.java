/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.auth.model;

import java.io.Serializable;
/**
 * Role
 * @author douguoqiang
 * @since 2018年1月15日
 */
public class Role implements Serializable {
  /**
   * @Field long serialVersionUID 
   */
  private static final long serialVersionUID = 1L;
  private String code;
  private String name;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "Role [code=" + code + ", name=" + name + "]";
  }

}
