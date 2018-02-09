/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.service.auth.adapter.dto;

import com.eg.egsc.framework.client.dto.BaseBusinessDto;

/**
 * UserLoginDto
 * 
 * @author gaoyanlong
 * @since 2018年1月4日
 */
public class UserLoginDto extends BaseBusinessDto {

  /**
   * @Field long serialVersionUID
   */
  private static final long serialVersionUID = 1L;
  private String username;
  private String password;
  private String verifyCode;

  public UserLoginDto() {
    super();
  }

  /**
   * @param username
   * @param password
   * @param verifyCode
   */
  public UserLoginDto(String username, String password, String verifyCode) {
    super();
    this.username = username;
    this.password = password;
    this.verifyCode = verifyCode;
  }

  /**
   * @param username
   * @param password
   */
  public UserLoginDto(String username, String password) {
    super();
    this.username = username;
    this.password = password;
  }

  public String getVerifyCode() {
    return verifyCode;
  }

  public void setVerifyCode(String verifyCode) {
    this.verifyCode = verifyCode;
  }

  /**
   * @Return the String username
   */
  public String getUsername() {
    return username;
  }

  /**
   * @Param String username to set
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * @Return the String password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @Param String password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }
}
