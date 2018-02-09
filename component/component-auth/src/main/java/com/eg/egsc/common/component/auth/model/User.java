/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.auth.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于返回用户所拥有的角色,服务资源,应用程序资源和菜单按钮资源
 * 
 * @author fangwei
 * @since 2017年12月29日
 */
public class User implements Serializable {
  /**
   * @Field long serialVersionUID
   */
  private static final long serialVersionUID = 1L;
  private String status;
  private String userId;
  private String userName;
  private String department;
  private String uuid;
  private String phone;
  private String courtUuid;
  private List<Role> roles;
  private List<String> serviceIds = new ArrayList<String>();
  private List<UIResource> uiResources = new ArrayList<UIResource>();

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getCourtUuid() {
    return courtUuid;
  }

  public void setCourtUuid(String courtUuid) {
    this.courtUuid = courtUuid;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public List<Role> getRoles() {
    return roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }

  public List<String> getServiceIds() {
    return serviceIds;
  }

  public void setServiceIds(List<String> serviceIds) {
    this.serviceIds = serviceIds;
  }

  /**
   * @Return the List<UIResource> uiResources
   */
  public List<UIResource> getUiResources() {
    return uiResources;
  }

  /**
   * @Param List<UIResource> uiResources to set
   */
  public void setUiResources(List<UIResource> uiResources) {
    this.uiResources = uiResources;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "User [userId=" + userId + ", userName=" + userName + ", department=" + department
        + ", roles=" + roles + ", serviceIds=" + serviceIds + ", uiResources=" + uiResources + "]";
  }
}
