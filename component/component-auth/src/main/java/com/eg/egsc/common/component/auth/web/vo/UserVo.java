/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.auth.web.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * UserVo
 * 
 * @author gaoyanlong
 * @since 2018年1月4日
 */
public class UserVo implements Serializable {
  /**
   * @Field long serialVersionUID
   */
  private static final long serialVersionUID = 1L;
  private String status = "";
  private String token = "";
  private String userId = "";
  private String userName = "";
  private String department = "";
  private List<RoleVo> roles = new ArrayList<RoleVo>();
  private List<UIResourceVo> uiResources = new ArrayList<UIResourceVo>();

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * @Return the String userId
   */
  public String getUserId() {
    return userId;
  }

  /**
   * @Param String userId to set
   */
  public void setUserId(String userId) {
    this.userId = userId;
  }

  /**
   * @Return the String userName
   */
  public String getUserName() {
    return userName;
  }

  /**
   * @Param String userName to set
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  /**
   * @Return the String department
   */
  public String getDepartment() {
    return department;
  }

  /**
   * @Param String department to set
   */
  public void setDepartment(String department) {
    this.department = department;
  }

  /**
   * @Return the List<RoleVo> roles
   */
  public List<RoleVo> getRoles() {
    return roles;
  }

  /**
   * @Param List<RoleVo> roles to set
   */
  public void setRoles(List<RoleVo> roles) {
    this.roles = roles;
  }

  /**
   * @Return the List<UIResourceVo> uiResources
   */
  public List<UIResourceVo> getUiResources() {
    return uiResources;
  }

  /**
   * @Param List<UIResourceVo> uiResources to set
   */
  public void setUiResources(List<UIResourceVo> uiResources) {
    this.uiResources = uiResources;
  }

  /**
   * @Return the String token
   */
  public String getToken() {
    return token;
  }

  /**
   * @Param String token to set
   */
  public void setToken(String token) {
    this.token = token;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "UserVo [userId=" + userId + ", userName=" + userName + ", department=" + department
        + ", roles=" + roles + ", uiResources=" + uiResources + ", token=" + token + "]";
  }
}
