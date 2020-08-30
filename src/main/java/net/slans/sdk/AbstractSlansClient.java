/**
 * Alipay.com Inc. Copyright (c) 2004-2012 All Rights Reserved.
 */
package net.slans.sdk;

import net.slans.sdk.internal.parser.json.ObjectJsonParser;
import net.slans.sdk.internal.util.*;

import java.io.IOException;
import java.security.Security;
import java.util.*;

/**
 * @author liuqun.lq
 * @version $Id: AbstractAlipayClient.java, v 0.1 2018-07-03 10:45:21 liuqun.lq Exp $
 */
public abstract class AbstractSlansClient implements SlansClient {

    protected static final String ACCESS_TOKEN = "access_token";

    protected String httpMethod;

    protected String serverUrl;
    protected String signType;

    protected String corpid;

    // 连接超时
    protected int connectTimeout = 5000;
    // 读取超时
    protected int readTimeout = 15000;
    // 是否在客户端校验请求
    protected boolean needCheckRequest = true;
    // 是否对响应结果进行解释
    protected boolean needEnableParser = true;

    static {
        //清除安全设置
        Security.setProperty("jdk.certpath.disabledAlgorithms", "");
    }

    public AbstractSlansClient(String url, String appid) {
        this.serverUrl = url;
        this.corpid = appid;
    }

    public AbstractSlansClient(String url, String appid, String signType) {
        this.serverUrl = url;
        this.corpid = appid;
        this.signType = signType;
    }

    public AbstractSlansClient(String url, String appid, String signType, int connectTimeout, int readTimeout) {
        this(url, appid, signType);
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }

    @Override
    public <T extends SlansResponse> T certificateExecute(SlansRequest<T> request) throws SlansApiException {
        return null;
    }

    @Override
    public <T extends SlansResponse> T certificateExecute(SlansRequest<T> request, String accessToken) throws SlansApiException {
        return null;
    }

    @Override
    public <T extends SlansResponse> T execute(SlansRequest<T> request) throws SlansApiException {
        return execute(request, null);
    }

    @Override
    public <T extends SlansResponse> T execute(SlansRequest<T> request, String accessToken) throws SlansApiException {
        SlansParser<T> parser = null;
        if(this.needEnableParser) {
            parser = new ObjectJsonParser<T>(request.getResponseClass());
        }
        return _execute(request, parser, accessToken);
    }

    protected <T extends SlansResponse> T _execute(SlansRequest<T> request, SlansParser<T> parser, String accessToken) throws SlansApiException {
        Map<String, Object> rt = doAction(request, accessToken);
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
                throw new SlansApiException(e.getMessage());
            }
        }


        tRsp.setParams((SlansHashMap) rt.get("textParams"));

        if (!tRsp.isSuccess()) {

        }

        return tRsp;
    }

    protected <T extends SlansResponse> Map<String, Object> doAction(SlansRequest<T> request, String accessToken) throws SlansApiException {
        // 最终结果，包含api返回数据、协议、应用、请求的字段
        Map<String, Object> result = new HashMap<String, Object>();
        // 请求字段管理器
        RequestParametersHolder requestHolder = getRequestHolderWithSign(request, accessToken);

        String url = getRequestUrl(request, requestHolder);

        // 打印完整请求报文
//        if (SlansLogger.isBizDebugEnabled()) {
//            SlansLogger.logBizDebug(getRedirectUrl(requestHolder));
//        }
        result.put("prepareTime", System.currentTimeMillis());


        String rsp = null;
        try {
            if (this.httpMethod == SlansConstants.METHOD_POST) {
                rsp = WebUtils.doPost(url, requestHolder.getApplicationParams(), SlansConstants.CHARSET_UTF8, connectTimeout, readTimeout, request.getTextParams());
            } else if (this.httpMethod == SlansConstants.METHOD_GET) {
                rsp = WebUtils.doGet(url, requestHolder.getApplicationParams());
            } else {
                throw new SlansApiException("不支持的http method");
            }
        } catch(IOException e) {
            throw new SlansApiException(e.getMessage());
        }

        result.put("requestTime", System.currentTimeMillis());
        result.put("rsp", rsp);
        result.put("textParams", requestHolder.getApplicationParams());
        result.put("protocalMustParams", requestHolder.getProtocalMustParams());
        result.put("protocalOptParams", requestHolder.getProtocalOptParams());
        result.put("url", url);
        return result;
    }

    @Override
    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    private <T extends SlansResponse> RequestParametersHolder getRequestHolderWithSign(SlansRequest<?> request,
                                                                                       String accessToken)
            throws SlansApiException {
        RequestParametersHolder requestHolder = new RequestParametersHolder();
        SlansHashMap appParams = new SlansHashMap(request.getTextParams());

//        // 只有新接口和设置密钥才能支持加密
//        if (request.isNeedEncrypt()) {
//
//            if (StringUtils.isEmpty(appParams.get(SlansConstants.BIZ_CONTENT_KEY))) {
//
//                throw new SlansApiException("当前API不支持加密请求");
//            }
//
//            // 需要加密必须设置密钥和加密算法
//            if (StringUtils.isEmpty(this.encryptType) || getEncryptor() == null) {
//
//                throw new SlansApiException("API请求要求加密，则必须设置密钥类型和加密器");
//            }
//
//            String encryptContent = getEncryptor().encrypt(
//                    appParams.get(SlansConstants.BIZ_CONTENT_KEY), this.encryptType, this.charset);
//
//            appParams.put(SlansConstants.BIZ_CONTENT_KEY, encryptContent);
//        }

        requestHolder.setApplicationParams(appParams);

        SlansHashMap protocalMustParams = new SlansHashMap();
        protocalMustParams.put(SlansConstants.SIGN_TYPE, this.signType);
        protocalMustParams.put(SlansConstants.CORPID, corpid);

        SlansHashMap protocalOptParams = new SlansHashMap();
        protocalOptParams.put(SlansConstants.ACCESS_TOKEN, accessToken);
        requestHolder.setProtocalOptParams(protocalOptParams);

        if (!StringUtils.isEmpty(this.signType)) {
            String signContent = SlansSignature.getSignatureContent(requestHolder);
            protocalMustParams.put(SlansConstants.SIGN,
                    getSigner().sign(signContent, this.signType, SlansConstants.CHARSET_UTF8));
        }
        return requestHolder;
    }

    /**
     * 获取POST请求的base url
     *
     * @param requestHolder
     * @return
     * @throws SlansApiException
     */
    private String getRequestUrl(SlansRequest<?> request, RequestParametersHolder requestHolder) throws SlansApiException {
        StringBuilder urlSb = new StringBuilder(serverUrl);
        urlSb.append(request.getApiMethod());
        try {
            String sysMustQuery = WebUtils.buildQuery(requestHolder.getProtocalMustParams(), SlansConstants.CHARSET_UTF8);
            String sysOptQuery = WebUtils.buildQuery(requestHolder.getProtocalOptParams(), SlansConstants.CHARSET_UTF8);

            urlSb.append("?");
            if (org.apache.commons.lang3.StringUtils.isNotBlank(sysMustQuery)) {
                urlSb.append(sysMustQuery);
            }
            if (org.apache.commons.lang3.StringUtils.isNotBlank(sysMustQuery)
                    && org.apache.commons.lang3.StringUtils.isNotBlank(sysOptQuery)) {
                urlSb.append("&");
            }
            if (sysOptQuery != null && sysOptQuery.length() > 0) {
                urlSb.append(sysOptQuery);
            }
        } catch (IOException e) {
            throw new SlansApiException(e);
        }

        return urlSb.toString();
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public void setNeedCheckRequest(boolean needCheckRequest) {
        this.needCheckRequest = needCheckRequest;
    }

    public void setNeedEnableParser(boolean needEnableParser) {
        this.needEnableParser = needEnableParser;
    }

    public abstract Signer getSigner();

}
