/**
 * Alipay.com Inc. Copyright (c) 2004-2012 All Rights Reserved.
 */
package net.slans.sdk;

import com.alipay.api.internal.parser.json.ObjectJsonParser;
import com.alipay.api.internal.parser.xml.ObjectXmlParser;
import com.alipay.api.internal.util.*;
import com.alipay.api.internal.util.codec.Base64;
import com.alipay.api.internal.util.file.FileUtils;
import com.alipay.api.internal.util.json.JSONWriter;
import net.slans.sdk.internal.parser.json.ObjectJsonParser;
import net.slans.sdk.internal.util.*;

import javax.net.ssl.SSLException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liuqun.lq
 * @version $Id: AbstractAlipayClient.java, v 0.1 2018-07-03 10:45:21 liuqun.lq Exp $
 */
public abstract class AbstractIovereyeClient implements IovereyeClient {
    /**
     * 批量API默认分隔符
     **/
    private static final String BATCH_API_DEFAULT_SPLIT = "#S#";

    static {
        //清除安全设置
        Security.setProperty("jdk.certpath.disabledAlgorithms", "");

    }

    protected boolean                                    loadTest       = false;
    private   String                                     serverUrl;
    private   String                                     appId;
    private   String                                     prodCode;
    private   String                                     format         = Constants.FORMAT_JSON;
    private   String                                     signType       = Constants.SIGN_TYPE_RSA;
    private   String                                     encryptType    = Constants.ENCRYPT_TYPE_AES;
    private   String                                     charset;
    private   int                                        connectTimeout = 3000;
    private   int                                        readTimeout    = 15000;
    private   String                                     proxyHost;
    private   int                                        proxyPort;
//    private   SignChecker                                signChecker;
    private   String                                     appCertSN;
    private   String                                     alipayCertSN;
    private   String                                     alipayRootCertSN;
    private   String                                     alipayRootSm2CertSN;
    private   String                                     rootCertContent;
    private   X509Certificate                            cert;
    private   ConcurrentHashMap<String, X509Certificate> alipayPublicCertMap;
    private   ConcurrentHashMap<String, String>          alipayPublicKeyMap;

    @Override
    public <T extends IovereyeResponse> T execute(IovereyeRequest<T> request) throws ApiException {
        return execute(request, null);
    }

    @Override
    public <T extends IovereyeResponse> T execute(IovereyeRequest<T> request,
                                                String accessToken) throws ApiException {

        return execute(request, accessToken, null);
    }

    @Override
    public <T extends IovereyeResponse> T execute(IovereyeRequest<T> request, String accessToken,
                                                String appAuthToken) throws ApiException {
        return execute(request, accessToken, appAuthToken, null);
    }

    @Override
    public <T extends IovereyeResponse> T execute(IovereyeRequest<T> request, String accessToken,
                                                String appAuthToken, String targetAppId) throws ApiException {

        //如果根证书序列号非空，抛异常提示开发者使用certificateExecute
        if (!StringUtils.isEmpty(this.alipayRootCertSN)) {
            throw new ApiException("检测到证书相关参数已初始化，证书模式下请改为调用certificateExecute");
        }

        IovereyeParser<T> parser = new ObjectJsonParser<T>(request.getResponseClass());


        return _execute(request, parser, accessToken, appAuthToken, targetAppId);
    }

    private <T extends IovereyeResponse> T _execute(IovereyeRequest<T> request, IovereyeParser<T> parser,
                                                  String authToken, String appAuthToken,
                                                  String targetAppId) throws ApiException {

        long beginTime = System.currentTimeMillis();
        //适配新增证书序列号的请求
        Map<String, Object> rt = doPost(request, authToken, appAuthToken, null, targetAppId);
        if (rt == null) {
            return null;
        }
        Map<String, Long> costTimeMap = new HashMap<String, Long>();
        if (rt.containsKey("prepareTime")) {
            costTimeMap.put("prepareCostTime", (Long) (rt.get("prepareTime")) - beginTime);
            if (rt.containsKey("requestTime")) {
                costTimeMap.put("requestCostTime", (Long) (rt.get("requestTime")) - (Long) (rt.get("prepareTime")));
            }
        }

        T tRsp = null;

        try {

            // 若需要解密则先解密
            ResponseEncryptItem responseItem = decryptResponse(request, rt, parser);

            // 解析实际串
            tRsp = parser.parse(responseItem.getRealContent());
            tRsp.setBody(responseItem.getRealContent());

            // 验签是对请求返回原始串
            checkResponseSign(request, parser, responseItem.getRespContent(), tRsp.isSuccess());

            if (costTimeMap.containsKey("requestCostTime")) {
                costTimeMap.put("postCostTime", System.currentTimeMillis() - (Long) (rt.get("requestTime")));
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (ApiException e) {
            throw new ApiException(e);
        }

        tRsp.setParams((IovereyeHashMap) rt.get("textParams"));
        if (!tRsp.isSuccess()) {
            SlansLogger.logErrorScene(rt, tRsp, "", costTimeMap);
        } else {
            SlansLogger.logBizSummary(rt, tRsp, costTimeMap);
        }
        return tRsp;
    }
    
    /**
     * @param request
     * @param accessToken
     * @param appAuthToken
     * @param appCertSN    应用证书序列号
     * @return
             * @throws ApiException
     */
    private <T extends IovereyeResponse> Map<String, Object> doPost(IovereyeRequest<T> request,
                                                                  String accessToken, String appAuthToken,
                                                                  String appCertSN, String targetAppId) throws ApiException {
        Map<String, Object> result = new HashMap<String, Object>();
        RequestParametersHolder requestHolder = getRequestHolderWithSign(request, accessToken,
                appAuthToken, appCertSN, targetAppId);

        String url = getRequestUrl(requestHolder);

        // 打印完整请求报文
//        if (SlansLogger.isBizDebugEnabled()) {
//            SlansLogger.logBizDebug(getRedirectUrl(requestHolder));
//        }
        result.put("prepareTime", System.currentTimeMillis());

        String rsp = null;
        try {
                rsp = WebUtils.doPost(url, requestHolder.getApplicationParams(), charset,
                        connectTimeout, readTimeout, proxyHost, proxyPort);
        } catch (SSLException e) {
            if (e.getMessage().contains("the trustAnchors parameter must be non-empty")
                    || e.getMessage().contains("PKIX path building failed")) {
                throw new ApiException("SDK已默认开启SSL服务端证书校验，"
                        + "请确认本地JRE默认自带的CA证书库是否正确（JRE主目录下的lib/security/cacerts是否存在。" + e.getMessage(), e);
            }
            throw new ApiException(e);
        } catch (IOException e) {
            throw new ApiException(e);
        }
        result.put("requestTime", System.currentTimeMillis());
        result.put("rsp", rsp);
        result.put("textParams", requestHolder.getApplicationParams());
        result.put("protocalMustParams", requestHolder.getProtocalMustParams());
        result.put("protocalOptParams", requestHolder.getProtocalOptParams());
        result.put("url", url);
        return result;
    }

    /**
     * 组装接口参数，处理加密、签名逻辑
     *
     * @param request
     * @param accessToken
     * @param appAuthToken
     * @param appCertSN    应用证书序列号
     * @return
     * @throws ApiException
     */
    private <T extends IovereyeResponse> RequestParametersHolder getRequestHolderWithSign(IovereyeRequest<?> request,
                                                                                        String accessToken, String appAuthToken,
                                                                                        String appCertSN, String targetAppId)
            throws ApiException {
        RequestParametersHolder requestHolder = new RequestParametersHolder();
        IovereyeHashMap appParams = new IovereyeHashMap(request.getTextParams());


        if (StringUtils.isEmpty(charset)) {
            charset = Constants.CHARSET_UTF8;
        }

        IovereyeHashMap protocalOptParams = new IovereyeHashMap();
        protocalOptParams.put(Constants.ACCESS_TOKEN, accessToken);
        return requestHolder;
    }

    /**
     * 获取POST请求的base url
     *
     * @param requestHolder
     * @return
     * @throws ApiException
     */
    private String getRequestUrl(RequestParametersHolder requestHolder) throws ApiException {
        StringBuilder urlSb = new StringBuilder(serverUrl);
        try {
            String sysMustQuery = WebUtils.buildQuery(loadTest ?
                    LoadTestUtil.getParamsWithLoadTestFlag(requestHolder.getProtocalMustParams())
                    : requestHolder.getProtocalMustParams(), charset);
            String sysOptQuery = WebUtils.buildQuery(requestHolder.getProtocalOptParams(), charset);

            urlSb.append("?");
            urlSb.append(sysMustQuery);
            if (sysOptQuery != null && sysOptQuery.length() > 0) {
                urlSb.append("&");
                urlSb.append(sysOptQuery);
            }
        } catch (IOException e) {
            throw new AlipayApiException(e);
        }

        return urlSb.toString();
    }


}
