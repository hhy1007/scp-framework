/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.sequence;

/**
 * Get sequence number
 * 
 * @Class Name SequenceService
 * @author gaoyanlong
 * @since 2018年1月3日
 */
public interface SequenceService {

  String getUUID();

  String getSequence(String sysCode) throws SequenceException;
}
