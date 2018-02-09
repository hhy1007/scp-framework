/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.job.model;

/**
 * job状态类
 * 
 * @author songjie
 * @since 2018年1月17日
 */
public enum JobStatus {

	ENABLE("1"), DISABLE("0");

	private String value;

	/**
	 * JobStatus
	 * @param value
	 */
	private JobStatus(String value) {
		this.value = value;
	}

	/**
	 * 
	 * @Methods Name getValue
	 * @Create In 2018年1月16日 By songjie
	 * @return String
	 */
	public String getValue() {
		return value;
	}
}
