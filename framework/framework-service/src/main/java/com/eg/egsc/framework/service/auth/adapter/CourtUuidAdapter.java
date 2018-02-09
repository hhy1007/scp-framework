/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.service.auth.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.eg.egsc.common.component.sequence.SequenceService;
import com.eg.egsc.common.constant.CommonConstant;
import com.eg.egsc.framework.client.core.BaseApiClient;
import com.eg.egsc.framework.client.dto.BaseBusinessDto;
import com.eg.egsc.framework.client.dto.ResponseDto;

/**
 * 读取小区UUID适配器
 * 
 * @author gaoyanlong
 * @since 2018年1月22日
 */
@Service
public class CourtUuidAdapter extends BaseApiClient {
  protected final Logger logger = LoggerFactory.getLogger(CourtUuidAdapter.class);

  @Autowired
  private SequenceService sequenceServiceImpl;

  @Value("${egsc.config.courtuuid.service-uri:/scp-mdmapp/api/court/getCourtUuid}")
  private String courtUuidUri;

  public String getCurrentCourtUuid() {
    String sysCode = CommonConstant.FRAMEWORK_SYS_CODE;
    String businessId = sequenceServiceImpl.getSequence(sysCode);
    BaseBusinessDto bizDto = new BaseBusinessDto();
    bizDto.setBusinessId(businessId);

    ResponseDto res = post(courtUuidUri, bizDto);

    return res.getData(String.class);
  }


  /*
   * (non-Javadoc)
   * 
   * @see com.eg.egsc.framework.client.core.BaseApiClient#getContextPath()
   */
  @Override
  protected String getContextPath() {
    return "";
  }

}
