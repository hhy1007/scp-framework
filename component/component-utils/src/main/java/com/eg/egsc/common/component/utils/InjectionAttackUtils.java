/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.utils;

/**
 * 
 * @author douguoqiang
 * @since 2018年1月15日
 */
public class InjectionAttackUtils {
	private static final String EVENTS = "((?i)onload|onunload|onchange|onsubmit|onreset"
			+ "|onselect|onblur|onfocus|onkeydown|onkeypress|onkeyup"
			+ "|onclick|ondblclick|onmousedown|onmousemove|onmouseout|onmouseover|onmouseup)";
	private static final String XSS_HTML_TAG = "(%3C)|(%3E)|[<>]+";
	private static final String XSS_INJECTION = "((%22%20)|(%22\\s)|('%22)|(%22\\+))\\w.*|(\\s|%20)"
			+ EVENTS + ".*|(%7C)";
	private static final String XSS_REGEX = XSS_HTML_TAG + "|" + XSS_INJECTION;
	private static final String SQL_REGEX = "('.+--)|(--)|(\\|)|(%7C)|(')";

	boolean filterXSS = true;
	boolean filterSQL = true;

	public InjectionAttackUtils(boolean filterXSS, boolean filterSQL) {
		this.filterXSS = filterXSS;
		this.filterSQL = filterSQL;
	}

	public InjectionAttackUtils() {
	}

	protected String[] filterStringArray(String[] rawValue) {
		String[] filteredArray = new String[rawValue.length];
		for (int i = 0; i < rawValue.length; i++) {
			filteredArray[i] = filterParamString(rawValue[i]);
		}
		return filteredArray;
	}

	public String filterParamString(String rawValue) {
		if (rawValue == null) {
			return null;
		}
		if (filterXSS()) {
			rawValue = rawValue.replaceAll(XSS_REGEX, "");
		}
		if (filterSQL()) {
			rawValue = rawValue.replaceAll(SQL_REGEX, "");
		}
		return rawValue;
	}

	protected boolean filterXSS() {
		return filterXSS;
	}

	protected boolean filterSQL() {
		return filterSQL;
	}

}
