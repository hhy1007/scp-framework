/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.service.base;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.WebApplicationContext;

import com.eg.egsc.common.exception.BaseException;
import com.eg.egsc.framework.service.util.MessageUtils;

/**
 * 处理通用异常
 * @author wanghongben
 * @since 2018年1月24日
 */
public class BaseController {

  private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

  private static final String CONTEXT_ATTRIBUTE =
      WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE;

  /**
   * 用于处理通用异常
   */
  @ExceptionHandler({Exception.class})
  @ResponseBody
  @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
  protected Object exception(Exception exception, HttpServletRequest request,
      HttpServletResponse response) {
    logger.warn("got a Exception", exception);
    String message = "";
    ServletContext servletContext = request.getServletContext();
    WebApplicationContext context =
        (WebApplicationContext) servletContext.getAttribute(CONTEXT_ATTRIBUTE);

    String exceptionCode = "";
    if (exception instanceof BaseException) {
      BaseException baseException = (BaseException) exception;
      exceptionCode = baseException.getCode();
      Object[] args = baseException.getValues();
      message = message
          + context.getMessage(exceptionCode, args, exception.getMessage(), request.getLocale());
      baseException.setMessage(message);

    } else if (exception instanceof MethodArgumentNotValidException) {
      List<FieldError> errors =
          ((MethodArgumentNotValidException) exception).getBindingResult().getFieldErrors();
      if (null != errors && !errors.isEmpty()) {
        exceptionCode = errors.get(0).getDefaultMessage();
      }
      message = message
          + context.getMessage(exceptionCode, null, exception.getMessage(), request.getLocale());
    } else {
      logger.error(exception.getMessage(), exception);
      message = message + "系统内部错误！";
    }

    String uri = request.getRequestURI();
    Object result = message;
    if (StringUtils.contains(uri, "/api/")) {
      result = MessageUtils.getResponseByMessage(message);
    }
    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    return result;
  }
}
