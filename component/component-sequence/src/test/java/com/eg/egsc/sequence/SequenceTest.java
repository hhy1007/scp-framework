/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.sequence;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eg.egsc.common.component.sequence.impl.SequenceServiceImpl;

/**
 * TestSequence
 * 
 * @author gaoyanlong
 * @since 2017年12月20日
 */
public class SequenceTest {
  private static final Logger logger = LoggerFactory.getLogger(SequenceTest.class);

  @Test
  public void testSequence() {
    try {
      logger.info(new SequenceServiceImpl().getSequence("DE"));
      logger.info(new SequenceServiceImpl().getSequence("DEM"));
    } catch (Exception e) {
      logger.info(e.getMessage());
    }
  }
}
