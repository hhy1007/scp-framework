/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.job.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * job配置类
 * 
 * @author songjie
 * @since 2018年1月17日
 */
public class BusinessJobConfig {

	private String serviceName; // 服务名称
	private String methodName; // 方法名称
	private String cronExpression; // 表达式
	private String category; // 类型
	private String ips; // 运行Ip
	private String status; // 状态
	private String creator; // 创建人
	private String creatorId; // 创建人id
	private Date createDt; // 创建时间
	private String lastUpdator; // 最后修改人
	private String lastUpdatorId; // 最后修改人id
	private Date lastUpdateDt; // 最后修改时间
	private boolean runAsAdmin = false;//

	public BusinessJobConfig() {
		super();
	}
	
	public boolean isRunAsAdmin() {
    return runAsAdmin;
  }


  public void setRunAsAdmin(boolean runAsAdmin) {
    this.runAsAdmin = runAsAdmin;
  }


  public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public String getLastUpdator() {
		return lastUpdator;
	}

	public void setLastUpdator(String lastUpdator) {
		this.lastUpdator = lastUpdator;
	}

	public String getLastUpdatorId() {
		return lastUpdatorId;
	}

	public void setLastUpdatorId(String lastUpdatorId) {
		this.lastUpdatorId = lastUpdatorId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLastUpdateDt() {
		return lastUpdateDt;
	}

	public void setLastUpdateDt(Date lastUpdateDt) {
		this.lastUpdateDt = lastUpdateDt;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getIps() {
		return ips;
	}

	public void setIps(String ips) {
		this.ips = ips;
	}

}
