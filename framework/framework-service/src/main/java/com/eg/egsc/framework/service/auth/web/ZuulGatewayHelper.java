/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.service.auth.web;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient.EurekaServiceInstance;
import org.springframework.stereotype.Service;

import com.netflix.appinfo.InstanceInfo;

/**
 * @Class Name GatewayHelper
 * @author gaoyanlong
 * @since 2018年1月6日
 */
@Service
public class ZuulGatewayHelper {
  protected final Logger logger = LoggerFactory.getLogger(ZuulGatewayHelper.class);

  @Autowired
  private DiscoveryClient discoveryClient;

  @Value("${zuul.service.name:gateway-server}")
  private String zuulServiceName;

  public List<String> getIPs() {

    List<String> ips = new ArrayList<String>();

    try {
      List<ServiceInstance> instances = discoveryClient.getInstances(zuulServiceName);
      for (ServiceInstance instance : instances) {
        InstanceInfo instanceInfo = ((EurekaServiceInstance) instance).getInstanceInfo();
        ips.add(instanceInfo.getIPAddr());
      }
    } catch (Exception e) {
      logger.warn(String.format("读取网关服务 %s地址错误", zuulServiceName), e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug(String.format("Zuul Gateway IP : %s", ips));
    }
    return ips;
  }
}
