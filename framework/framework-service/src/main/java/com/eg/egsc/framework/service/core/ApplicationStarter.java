/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.service.core;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import com.eg.egsc.common.component.utils.LogbackUtils;
import com.eg.egsc.framework.service.core.config.CoreConfig;

/**
 * ApplicationStarter
 * @author wanghongben
 * @since 2018年1月25日
 */
@ComponentScan(value = "com.eg.egsc")
@EnableAutoConfiguration
@ServletComponentScan(basePackages = {"com.eg.egsc"})
public class ApplicationStarter extends SpringBootServletInitializer {

  private static final Logger logger = LoggerFactory.getLogger(ApplicationStarter.class);

  private static volatile ConfigurableApplicationContext instance;

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(CoreConfig.class);
  }

  public static void run(Class<?> assemblyClass, String[] args) {
    if (null != args && args.length > 0) {
      for (String arg : args) {
        setServerPortInSystemProperty(arg);
      }
    }
    LogbackUtils.updateSystemPropertyWithLogbackFileName();

    if (instance == null) {
      synchronized (ApplicationStarter.class) {
        if (instance == null) {
          logger.info("start run a new instance.");

          instance = new ApplicationStarter()
              .configure(new SpringApplicationBuilder(ApplicationStarter.class, assemblyClass))
              .profiles("localResource", "healthCheck").properties(defaultProperties()).run(args);
        } else {
          logger.warn("instance is not null, can't run a new one.");
        }
      }
    } else {
      logger.warn("instance is not null, can't run a new one.");
    }
  }

  /**
   * @param arg void
   */
  private static void setServerPortInSystemProperty(String arg) {
    if (arg.indexOf("server.port") > -1) {
      String[] portArg = arg.split("=");
      if (null != portArg && portArg.length > 1) {
        System.setProperty("server.port", portArg[1]);
      }
    }
  }

  private static Properties defaultProperties() {
	  return new Properties();
  }

  public static synchronized void close() {
    if (instance != null) {
      logger.info("start to stop the instance.");
      instance = null;
      instance.close();
      logger.info("stopped the instance.");
    } else {
      logger.warn("can't close application,instance == null");
    }
  }

  public static boolean isRunning() {
    return instance != null;
  }

  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource =
        new ReloadableResourceBundleMessageSource();
    messageSource.addBasenames("classpath:messages/exception-framework");
    messageSource.addBasenames("classpath:messages/exception");
    // if true, the key of the message will be displayed if the key is not
    // found, instead of throwing a NoSuchMessageException
    messageSource.setUseCodeAsDefaultMessage(true);
    messageSource.setDefaultEncoding("UTF-8");
    // # -1 : never reload, 0 always reload
    messageSource.setCacheSeconds(0);
    
    return messageSource;
  }
}
