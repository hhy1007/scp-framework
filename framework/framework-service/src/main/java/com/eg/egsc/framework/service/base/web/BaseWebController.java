/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.service.base.web;

import java.util.Date;

import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.eg.egsc.framework.service.base.BaseController;

/**
 * BaseWebController
 * @author wanghongben
 * @since 2018年1月24日
 */
public class BaseWebController extends BaseController {

	@InitBinder
	protected void initBaseBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateEditor());
		binder.registerCustomEditor(Boolean.class, new CustomBooleanEditor(
				"true", "false", true));
	}
}
