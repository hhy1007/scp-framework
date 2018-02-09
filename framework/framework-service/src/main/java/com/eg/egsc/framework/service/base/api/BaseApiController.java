/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.service.base.api;

import com.eg.egsc.common.constant.CommonConstant;
import com.eg.egsc.framework.client.dto.ResponseDto;
import com.eg.egsc.framework.service.base.BaseController;

/**
 * BaseApiController
 * @author wanghongben
 * @since 2018年1月24日
 */
public class BaseApiController extends BaseController {

	public ResponseDto getDefaultResponseDto() {
		return new ResponseDto(CommonConstant.SUCCESS_CODE, null, "");
	}

}
