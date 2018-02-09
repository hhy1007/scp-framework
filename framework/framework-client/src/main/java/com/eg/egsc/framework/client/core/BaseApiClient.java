/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.client.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient.EurekaServiceInstance;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.eg.egsc.common.component.auth.web.SecurityContext;
import com.eg.egsc.common.component.sequence.SequenceService;
import com.eg.egsc.common.component.utils.JsonUtil;
import com.eg.egsc.common.constant.CommonConstant;
import com.eg.egsc.common.exception.CommonException;
import com.eg.egsc.framework.client.dto.BaseBusinessDto;
import com.eg.egsc.framework.client.dto.Header;
import com.eg.egsc.framework.client.dto.RequestDto;
import com.eg.egsc.framework.client.dto.ResponseDto;
import com.netflix.appinfo.InstanceInfo;

/**
 * Api Client基础类
 * 
 * @author gaoyanlong
 * @since 2018年1月9日
 */
public abstract class BaseApiClient {

	protected final Logger logger = LoggerFactory
			.getLogger(BaseApiClient.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private DiscoveryClient discoveryClient;

	@Autowired
	private SequenceService sequenceServiceImpl;

	@Value("${gateway.service-url:}")
	private String gatewayUrl;

	@Value("${gateway.service-url:}")
	private String customGatewayUrl;

	private String defaultHost = "http://localhost:8082";

	@Value("${zuul.service.name:gateway-server}")
	private String zuulServiceName;

	@Value("${spring.application.name}")
	private String appName;

	@Value("${egsc.config.appshortname:UNKOWN}")
	private String appShortName;

	@Value("${egsc.config.ssl.enabled:false}")
	private String sslEnabled;

	@Value("${egsc.config.ssl.pfxpath:UNKOWN}")
	private String pfxpath;

	@Value("${egsc.config.ssl.pfxpwd:UNKOWN}")
	private String pfxpwd;

	public BaseApiClient() {
	}

	public BaseApiClient(String gatewayUrl) {
		this.gatewayUrl = gatewayUrl;
	}

	protected abstract String getContextPath();

	public void setServiceUrl(String url) {
		logger.info(String.format("Gateway adrress has been changed to : %s",
				url));
		gatewayUrl = url;
	}

	/**
	 * 获得服务地址
	 * 
	 * @return String
	 */
	public String getServiceUrl() {
		if (StringUtils.isBlank(gatewayUrl)) {

			try {
				List<ServiceInstance> instances = discoveryClient
						.getInstances(zuulServiceName);

				// If instances is more than 1, it should choose the idle
				// one.
				if (instances != null && !instances.isEmpty()) {
					ServiceInstance serviceInstance = instances.get(0);
					InstanceInfo instanceInfo = ((EurekaServiceInstance) serviceInstance)
							.getInstanceInfo();
					gatewayUrl = String.format("http://%s:%s",
							instanceInfo.getIPAddr(), instanceInfo.getPort());

					logger.info(String.format("链接网关: %s", gatewayUrl));
				} else {
					gatewayUrl = StringUtils.isEmpty(customGatewayUrl) ? defaultHost
							: customGatewayUrl;
					logger.warn(String.format("读取网关服务 %s地址错误， 设置连接地址为: %s",
							zuulServiceName, gatewayUrl));
				}
			} catch (Exception e) {
				gatewayUrl = StringUtils.isEmpty(customGatewayUrl) ? defaultHost
						: customGatewayUrl;
				logger.warn(String.format("读取网关服务 %s地址错误， 设置连接地址为: %s",
						zuulServiceName, gatewayUrl), e);
			}
		}
		return gatewayUrl;
	}

	/**
	 * 用于访问服务的客户端
	 * 
	 * @return RestTemplate
	 */
	protected RestTemplate getRestTemplate() {
		try {
			if ("true".equals(sslEnabled)) {
				CloseableHttpClient httpClient = acceptsUntrustedCertsHttpClient();
				HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
						httpClient);
				restTemplate = new RestTemplate(clientHttpRequestFactory);
			} else {
				restTemplate = new RestTemplate();
			}
		} catch (KeyManagementException | KeyStoreException
				| NoSuchAlgorithmException | UnrecoverableKeyException
				| CertificateException | IOException e) {
			logger.error("实例化RestTemplate异常", e);
			restTemplate = new RestTemplate();
		}

		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType
				.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());

		return restTemplate;
	}

	/**
	 * 调用接口，并封装公共框架
	 * 
	 * @param url
	 * @param baseBusinessDto
	 * @return ResponseDto
	 * @throws CommonException
	 */
	protected ResponseDto post(String url, BaseBusinessDto baseBusinessDto)
			throws CommonException {

		ResponseDto response = null;
		try {
			response = call(getContextPath() + url, baseBusinessDto);
		} catch (Exception ex) {
			String errMsg = ex.getMessage();
			logger.error(errMsg, ex);
			if (errMsg != null) {
				throw new CommonException(errMsg);
			} else {
				throw new CommonException(
						CommonConstant.DEFAULT_SYS_ERROR_CODE, "系统调用错误!", null,
						ex);
			}
		}

		if (response != null
				&& !CommonConstant.SUCCESS_CODE.equals(response.getCode())) {
			throw new CommonException(response.getCode(), response.getMessage());
		}
		return response;
	}

	/**
	 * 封装公共框架的BusinessId、TargetSysId、SourceSysId
	 * 
	 * @param url
	 * @param baseBusinessDto
	 * @return ResponseDto
	 */
	private ResponseDto call(String url, BaseBusinessDto baseBusinessDto) {
		RequestDto<BaseBusinessDto> requestDto = new RequestDto<BaseBusinessDto>();
		Header requestHeader = new Header();
		if (sequenceServiceImpl != null) {
			requestHeader.setBusinessId(sequenceServiceImpl
					.getSequence(appShortName));
		}
		requestHeader.setSourceSysId(baseBusinessDto.getSourceSysId());
		if (StringUtils.isNotEmpty(appName)) {
			requestHeader.setSourceSysId(appName);
		}
		requestHeader.setTargetSysId(baseBusinessDto.getTargetSysId());
		String contextPath = getContextPath(url);
		if (StringUtils.isNotEmpty(contextPath)) {
			requestHeader.setTargetSysId(contextPath);
		}
		requestHeader.setCreateTimestamp(System.currentTimeMillis());
		requestHeader.setCharset("utf-8");
		requestHeader.setContentType("json");
		requestHeader.setExtAttributes(baseBusinessDto.getExtAttributes());

		requestDto.setHeader(requestHeader);

		baseBusinessDto.clearMetaData();
		requestDto.setData(baseBusinessDto);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.add("Authorization", SecurityContext.getToken());
		HttpEntity<RequestDto<?>> formEntity = new HttpEntity<RequestDto<?>>(
				requestDto, headers);
		URI uri = URI.create(getServiceUrl() + url);

		logger.info(String.format("post -> %s", uri.toString()));

		if (logger.isDebugEnabled()) {
			logger.debug("Request: " + JsonUtil.toJsonString(requestDto));
		}

		ResponseDto response = null;
		try {
			response = getRestTemplate().postForObject(uri, formEntity,
					ResponseDto.class);
		} catch (ResourceAccessException | HttpClientErrorException e) {
			logger.error(e.getMessage(), e);
			this.gatewayUrl = null;
			uri = URI.create(getServiceUrl() + url);
			response = getRestTemplate().postForObject(uri, formEntity,
					ResponseDto.class);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Response: " + JsonUtil.toJsonString(response));
		}

		return response;
	}

	/**
	 * 获得系统上下文
	 * 
	 * @param url
	 * @return String
	 */
	private String getContextPath(String url) {
		String contextPath = "";
		if (StringUtils.isNotEmpty(url) && url.startsWith("/")
				&& !url.startsWith("//")) {
			contextPath = url.split("/")[1];
		} else {
			throw new CommonException("00099",
					String.format("URL %s 格式不对", url));
		}
		return contextPath;
	}

	/**
	 * 
	 * @return CloseableHttpClient
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws CertificateException
	 * @throws IOException
	 * @throws UnrecoverableKeyException
	 */
	private CloseableHttpClient acceptsUntrustedCertsHttpClient()
			throws KeyStoreException, NoSuchAlgorithmException,
			KeyManagementException, CertificateException, IOException,
			UnrecoverableKeyException {
		HttpClientBuilder b = HttpClientBuilder.create();

		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		InputStream instream = new FileInputStream(new File(pfxpath));
		try {
			keyStore.load(instream, pfxpwd.toCharArray());
		} finally {
			instream.close();
		}

		SSLContext sslcontext = SSLContexts.custom()
				.loadKeyMaterial(keyStore, pfxpwd.toCharArray()).build();

		SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null,
				new TrustStrategy() {
					@Override
					public boolean isTrusted(X509Certificate[] arg0, String arg1)
							throws CertificateException {
						return true;
					}
				}).build();
		b.setSSLContext(sslContext);

		SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(
				sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.getDefaultHostnameVerifier());

		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
				.<ConnectionSocketFactory> create()
				.register("http",
						PlainConnectionSocketFactory.getSocketFactory())
				.register("https", sslSocketFactory).build();

		PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(
				socketFactoryRegistry);
		connMgr.setMaxTotal(200);
		connMgr.setDefaultMaxPerRoute(100);
		b.setConnectionManager(connMgr);

		return b.build();
	}
}
