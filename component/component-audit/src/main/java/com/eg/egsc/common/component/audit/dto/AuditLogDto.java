/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.audit.dto;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.eg.egsc.common.component.utils.JsonUtil;

/**
 * Log Dto
 * 
 * @author guofeng
 * @create In 2018年1月29日
 */
public class AuditLogDto {

  /**
   * @Field long serialVersionUID
   */
  private static final long serialVersionUID = -23090409490904942L;
  private String businessId;
  private String sourceSysId;
  private String targetSysId;
  private Map<String, Object> extAttributes = new HashMap<String, Object>();

  /**
   * TODO
   * 
   * @return String
   */
  public String getBusinessId() {
    return businessId;
  }

  /**
   * TODO
   * 
   * @param businessId void
   */
  public void setBusinessId(String businessId) {
    this.businessId = businessId;
  }

  /**
   * TODO
   * 
   * @return String
   */
  public String getSourceSysId() {
    return sourceSysId;
  }

  /**
   * TODO
   * 
   * @param sourceSysId void
   */
  public void setSourceSysId(String sourceSysId) {
    this.sourceSysId = sourceSysId;
  }

  /**
   * TODO
   * 
   * @return String
   */
  public String getTargetSysId() {
    return targetSysId;
  }

  /**
   * TODO
   * 
   * @param targetSysId void
   */
  public void setTargetSysId(String targetSysId) {
    this.targetSysId = targetSysId;
  }

  /**
   * TODO
   * 
   * @return Map<String,Object>
   */
  public Map<String, Object> getExtAttributes() {
    return extAttributes;
  }

  /**
   * TODO
   * 
   * @param extAttributes void
   */
  public void setExtAttributes(Map<String, Object> extAttributes) {
    this.extAttributes = extAttributes;
  }

  public void clearMetaData() {
    this.businessId = null;
    this.sourceSysId = null;
    this.targetSysId = null;
    this.extAttributes = null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return JsonUtil.toJsonString(this);
  }

  /**
   * @Field long serialVersionUID
   */

  private String ip;

  private String url;

  private String operatorId;

  private String operatorName;

  private String type;

  private String business;

  private String operation;

  private Date operateTime;

  private Date completeTime;

  private String isSucess;

  private String errorDetail;

  private String ext1;

  private String ext2;

  private String ext3;

  private String createUser;

  private Date createTime;

  private Date updateTime;

  private String updateUser;

  private String courtUuid;

  private String responseMessage;

  private String requestMessage;

  private String consuming;

  private String integrate;

  /**
   * @Return the String integrate
   */
  /**
   * TODO
   * 
   * @return String
   */
  public String getIntegrate() {
    return integrate;
  }

  /**
   * @Param String integrate to set
   */
  public void setIntegrate(String integrate) {
    this.integrate = integrate;
  }

  /**
   * @Return the String ip
   */
  public String getIp() {
    return ip;
  }

  /**
   * @Param String ip to set
   */
  public void setIp(String ip) {
    this.ip = ip;
  }

  /**
   * @Return the String url
   */
  public String getUrl() {
    return url;
  }

  /**
   * @Param String url to set
   */
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * @Return the String operatorId
   */
  public String getOperatorId() {
    return operatorId;
  }

  /**
   * @Param String operatorId to set
   */
  public void setOperatorId(String operatorId) {
    this.operatorId = operatorId;
  }

  /**
   * @Return the String operatorName
   */
  public String getOperatorName() {
    return operatorName;
  }

  /**
   * @Param String operatorName to set
   */
  public void setOperatorName(String operatorName) {
    this.operatorName = operatorName;
  }

  /**
   * @Return the String type
   */
  public String getType() {
    return type;
  }

  /**
   * @Param String type to set
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * @Return the String business
   */
  public String getBusiness() {
    return business;
  }

  /**
   * @Param String business to set
   */
  public void setBusiness(String business) {
    this.business = business;
  }

  /**
   * @Return the String operation
   */
  public String getOperation() {
    return operation;
  }

  /**
   * @Param String operation to set
   */
  public void setOperation(String operation) {
    this.operation = operation;
  }

  /**
   * @Return the Date operateTime
   */
  public Date getOperateTime() {
    return operateTime;
  }

  /**
   * @Param Date operateTime to set
   */
  public void setOperateTime(Date operateTime) {
    this.operateTime = operateTime;
  }

  /**
   * @Return the Date completeTime
   */
  public Date getCompleteTime() {
    return completeTime;
  }

  /**
   * @Param Date completeTime to set
   */
  public void setCompleteTime(Date completeTime) {
    this.completeTime = completeTime;
  }

  /**
   * @Return the String isSucess
   */
  public String getIsSucess() {
    return isSucess;
  }

  /**
   * @Param String isSucess to set
   */
  public void setIsSucess(String isSucess) {
    this.isSucess = isSucess;
  }

  /**
   * @Return the String errorDetail
   */
  public String getErrorDetail() {
    return errorDetail;
  }

  /**
   * @Param String errorDetail to set
   */
  public void setErrorDetail(String errorDetail) {
    this.errorDetail = errorDetail;
  }

  /**
   * @Return the String ext1
   */
  public String getExt1() {
    return ext1;
  }

  /**
   * @Param String ext1 to set
   */
  public void setExt1(String ext1) {
    this.ext1 = ext1;
  }

  /**
   * @Return the String ext2
   */
  public String getExt2() {
    return ext2;
  }

  /**
   * @Param String ext2 to set
   */
  public void setExt2(String ext2) {
    this.ext2 = ext2;
  }

  /**
   * @Return the String ext3
   */
  public String getExt3() {
    return ext3;
  }

  /**
   * @Param String ext3 to set
   */
  public void setExt3(String ext3) {
    this.ext3 = ext3;
  }

  /**
   * @Return the String createUser
   */
  public String getCreateUser() {
    return createUser;
  }

  /**
   * @Param String createUser to set
   */
  public void setCreateUser(String createUser) {
    this.createUser = createUser;
  }

  /**
   * @Return the Date createTime
   */
  public Date getCreateTime() {
    return createTime;
  }

  /**
   * @Param Date createTime to set
   */
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  /**
   * @Return the Date updateTime
   */
  public Date getUpdateTime() {
    return updateTime;
  }

  /**
   * @Param Date updateTime to set
   */
  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  /**
   * @Return the String updateUser
   */
  public String getUpdateUser() {
    return updateUser;
  }

  /**
   * @Param String updateUser to set
   */
  public void setUpdateUser(String updateUser) {
    this.updateUser = updateUser;
  }

  /**
   * @Return the String courtUuid
   */
  public String getCourtUuid() {
    return courtUuid;
  }

  /**
   * @Param String courtUuid to set
   */
  public void setCourtUuid(String courtUuid) {
    this.courtUuid = courtUuid;
  }

  /**
   * @Return the String responseMessage
   */
  public String getResponseMessage() {
    return responseMessage;
  }

  /**
   * @Param String responseMessage to set
   */
  public void setResponseMessage(String responseMessage) {
    this.responseMessage = responseMessage;
  }

  /**
   * @Return the String requestMessage
   */
  public String getRequestMessage() {
    return requestMessage;
  }

  /**
   * @Param String requestMessage to set
   */
  public void setRequestMessage(String requestMessage) {
    this.requestMessage = requestMessage;
  }

  /**
   * @Return the String consuming
   */
  public String getConsuming() {
    return consuming;
  }

  /**
   * @Param String consuming to set
   */
  public void setConsuming(String consuming) {
    this.consuming = consuming;
  }

  /**
   * @Return the long serialVersionUID
   */
  public static long getSerialversionuid() {
    return serialVersionUID;
  }

}
