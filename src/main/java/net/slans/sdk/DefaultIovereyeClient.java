package net.slans.sdk;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.slans.sdk.internal.parser.json.ObjectJsonParser;
import net.slans.sdk.internal.util.IovereyeHashMap;
import net.slans.sdk.internal.util.IovereyeUtils;
import net.slans.sdk.internal.util.RequestParametersHolder;
import net.slans.sdk.internal.util.WebUtils;
/**
 * 基于REST的TOP客户端。
 * 
 * @author JieMo
 * @since 1.7, 05 15, 2014
 */
public class DefaultIovereyeClient implements IovereyeClient {

//	protected static final String OAUTH_CLIENT_ID = "client_id";
	protected static final String OAUTH_ACCESS_TOKEN = "access_token";
//	protected static final String OAUTH_TIMESTAMP = "timestamp";
//	protected static final String OAUTH_SIGN = "sign";

	protected String serverUrl;
	protected String oauth_client_id;
	protected String oauth_client_secret;
	protected String format = Constants.FORMAT_JSON;
//	protected String signMethod = Constants.SIGN_METHOD_HMAC;

	// 连接超时
	private int connectTimeout = 30000;
	// 读取超时
	private int readTimeout = 5000;
	// 是否在客户端校验请求
	private boolean needCheckRequest = true;
	// 是否对响应结果进行解释
	private boolean needEnableParser = true;
	
	public DefaultIovereyeClient(String url, String client_id, String client_secret) {
		this.serverUrl = url;
		this.oauth_client_id = client_id;
		this.oauth_client_secret = client_secret;
	}
	
	public DefaultIovereyeClient(String url, String appkey, String secret, String format) {
		this(url, appkey, secret);
		this.format = format;
	}
	
	public DefaultIovereyeClient(String url, String appkey, String secret, String format, int connectTimeout, int readTimeout) {
		this(url, appkey, secret, format);
		this.connectTimeout = connectTimeout;
		this.readTimeout = readTimeout;
	}

	@Override
	public <T extends IovereyeResponse> T execute(IovereyeRequest<T> request) throws ApiException {
		return execute(request, null);
	}

	@Override
	public <T extends IovereyeResponse> T execute(IovereyeRequest<T> request, String session) throws ApiException {
		IovereyeParser<T> parser = null; 
		if(this.needEnableParser) {
			//if (Constants.FORMAT_XML.equals(this.format)) {
			parser = new ObjectJsonParser<T>(request.getResponseClass());				
		}
		return _execute(request, parser, session);
	}
	
	private <T extends IovereyeResponse> T _execute(IovereyeRequest<T> request, IovereyeParser<T> parser, String session) throws ApiException {
		Map<String, Object> rt = doAction(request, session);
		if(rt == null) {
			return null;
		}
		T tRsp = null;
		if(this.needEnableParser) {			
			try {
				tRsp = parser.parse((String) rt.get("rsp"));
				if (tRsp == null)
					try {
						tRsp = request.getResponseClass().newInstance();
					} catch (InstantiationException | IllegalAccessException e) {
						e.printStackTrace();
					}
				tRsp.setBody((String) rt.get("rsp"));
			} catch (RuntimeException e) {
				throw e;
			}			
		} else {			
			try {
				tRsp = request.getResponseClass().newInstance();
				tRsp.setBody((String) rt.get("rsp"));	
			} catch (Exception e) {
				throw new ApiException(e.getMessage());
			}			
		}
		
			
		tRsp.setParams((IovereyeHashMap) rt.get("textParams"));
		
		if (!tRsp.isSuccess()) {
			
		}
		
		return tRsp;
	}
	
	private <T extends IovereyeResponse> Map<String, Object> doAction(IovereyeRequest<T> request, String session) throws ApiException {
		// 最终结果，包含api返回数据、协议、应用、请求的字段
		Map<String, Object> result = new HashMap<String, Object>();
		// 请求字段管理器
		RequestParametersHolder requestHolder = new RequestParametersHolder();	
		
		this.serverUrl = IovereyeUtils.urlTopRequest(this.serverUrl, request.getApiMethod(), null);
		
		{// 添加协议级请求参数,[属于必需]
			IovereyeHashMap protocalMustParams = new IovereyeHashMap();			
			if (session != null) {
				protocalMustParams.put(OAUTH_ACCESS_TOKEN, session);
			}
			requestHolder.setProtocalMustParams(protocalMustParams);
		}
		
		{// api参数
			IovereyeHashMap appParams = new IovereyeHashMap(request.getTextParams());
			requestHolder.setTextParams(appParams);
		}
		
		{// 可选择性参数
			IovereyeHashMap protocalOptParams = new IovereyeHashMap();
			protocalOptParams.put("access_token", session);
			requestHolder.setProtocalOptParams(protocalOptParams);
		}		
		
		
		// 添加签名
		try {
			String sign = IovereyeUtils.signTopRequest(requestHolder, this.oauth_client_secret, this.signMethod);
			requestHolder.getProtocalMustParams().put(OAUTH_SIGN, sign);
		} catch (IOException e) {
			throw new ApiException(e);
		}
		
		StringBuffer urlSb = new StringBuffer(serverUrl);
		try {
			String sysMustQuery = WebUtils.buildQuery(requestHolder.getProtocalMustParams(),Constants.CHARSET_UTF8);
			String sysOptQuery = WebUtils.buildQuery(requestHolder.getProtocalOptParams(), Constants.CHARSET_UTF8);
			
			urlSb.append("?");
			urlSb.append(sysMustQuery);
			if(sysOptQuery != null && sysOptQuery.length() >0) {
				urlSb.append("&");
				urlSb.append(sysOptQuery);
			}
		} catch (IOException e) {
			throw new ApiException(e);
		}
		
		String rsp = null;
		try {
			if (request.getApiMethod() == Constants.METHOD_POST) {
				if (request instanceof IovereyeUploadRequest) {
					/*IovereyeUploadRequest<T> uRequest = (IovereyeUploadRequest<T>) request;
					Map<String, FileItem> fileParams = IovereyeUtils.cleanupMap(uRequest.getFileParams());
					rsp = WebUtils.doPost(urlSb.toString(), appParams, fileParams,Constants.CHARSET_UTF8, connectTimeout, readTimeout,request.getHeaderMap());*/
				} else {			
					rsp = WebUtils.doPost(urlSb.toString(), requestHolder.getTextParams(), Constants.CHARSET_UTF8, connectTimeout, readTimeout, request.getHeaderMap());
				}
			} else if (request.getApiMethod() == Constants.METHOD_GET) {
				rsp = WebUtils.doGet(urlSb.toString(), requestHolder.getTextParams());
			}
		} catch(IOException e) {
			throw new ApiException(e.getMessage());
		}
		
		result.put("rsp", rsp);
		result.put("textParams", requestHolder.getTextParams());
		result.put("protocalMustParams", requestHolder.getProtocalMustParams());
		result.put("protocalOptParams", requestHolder.getProtocalOptParams());
		result.put("url", urlSb.toString());
		return result;
	}
	
	public void setNeedCheckRequest(boolean needCheckRequest) {
		this.needCheckRequest = needCheckRequest;
	}

	public void setNeedEnableParser(boolean needEnableParser) {
		this.needEnableParser = needEnableParser;
	}

}
