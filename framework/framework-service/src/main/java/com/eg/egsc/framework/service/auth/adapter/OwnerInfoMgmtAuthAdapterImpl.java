/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.service.auth.adapter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.eg.egsc.common.component.auth.adapter.AuthAdapter;
import com.eg.egsc.common.component.auth.exception.AuthException;
import com.eg.egsc.common.component.auth.model.User;
import com.eg.egsc.common.component.sequence.SequenceService;
import com.eg.egsc.common.constant.CommonConstant;
import com.eg.egsc.framework.client.core.BaseApiClient;
import com.eg.egsc.framework.client.dto.ResponseDto;
import com.eg.egsc.framework.service.auth.adapter.dto.UserLoginDto;

/**
 * OwnerInfoMgmt Authentication & Authorization Adapter Implementation
 * 
 * @author gaoyanlong
 * @since 2018年1月18日
 */
@Service
public class OwnerInfoMgmtAuthAdapterImpl extends BaseApiClient implements AuthAdapter {
  protected final Logger logger = LoggerFactory.getLogger(OwnerInfoMgmtAuthAdapterImpl.class);

  @Autowired
  private SequenceService sequenceServiceImpl;

  @Value("${auth.ownermgnt.service-uri:/egc-ownerinfomgmtcomponent/api/account/loginByPasswordOrVerifycode}")
  private String authUri;

  @Value("${egsc.config.auth.ownermgnt.url:}")
  private String ownermgntUrl;

  /*
   * (non-Javadoc)
   * 
   * @see com.eg.egsc.common.component.auth.adapter.AuthAdapter#findUser(java.lang.String,
   * java.lang.String)
   */
  @Override
  public User findUser(String username, String password, String verifyCode) throws AuthException {
    UserLoginDto userDto = new UserLoginDto(username, password, verifyCode);

    String sysCode = CommonConstant.FRAMEWORK_SYS_CODE;
    String businessId = sequenceServiceImpl.getSequence(sysCode);
    userDto.setBusinessId(businessId);
    if (!StringUtils.isEmpty(ownermgntUrl)) {
      super.setServiceUrl(ownermgntUrl);
    }
    ResponseDto res = post(authUri, userDto);

    return res.getData(User.class);
  }

  @Override
  protected String getContextPath() {
    return "";
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.eg.egsc.common.component.auth.adapter.AuthAdapter#findUser(java.lang.String,
   * java.lang.String, java.lang.String)
   */
  @Override
  public User findUser(String userName, String password) throws AuthException {
    logger.warn(
        "UserMgmt doesn't support this operation, it uses findUser(userName, password， verifyCode) instead!");
    return findUser(userName, password);
  }
}
