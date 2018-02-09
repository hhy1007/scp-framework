/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.sequence.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eg.egsc.common.component.redis.RedisUtils;
import com.eg.egsc.common.component.sequence.SequenceException;
import com.eg.egsc.common.component.sequence.SequenceService;

/**
 * Get sequence number
 * 
 * @author gaoyanlong
 * @since 2018年1月3日
 */
@Service
public class SequenceServiceImpl implements SequenceService {

  private static final String SEQUENCE_KEY = "SEQUENCE_KEY:BUSINESSID";

  @Autowired
  private RedisUtils redisUtils;

  /**
   * 根据系统编码生成序列号
   * 
   * @Methods Name getUUID
   * @return
   * @throws Exception String
   */
  public final synchronized String getUUID() {
    return UUID.randomUUID().toString().replace("-", "");
  }

  /**
   * 根据系统编码生成序列号
   * 
   * @Methods Name getSequence
   * @param sysCode 3位系统编码
   * @return
   * @throws Exception String
   */
  @Override
  public String getSequence(String sysCode) throws SequenceException {

    if (sysCode == null || sysCode.length() > 6 || sysCode.length() < 2) {
      throw new SequenceException(String.format("系统编码无效: %s", sysCode));
    }

    return sysCode + getSubSequence();
  }

  private String getSubSequence() {
	  return String.format("%010d", redisUtils.generate(SEQUENCE_KEY));
  }
}
