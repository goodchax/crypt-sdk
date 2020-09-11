package net.slans.qd.sdk.pay;

import net.slans.qd.sdk.pay.util.AsymmetricManager;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author junying.wjy
 */
public class SlansSignature {
    /**
     * 通用签名方法
     *
     * @param content    待签名内容
     * @param privateKey 私钥
     * @param charset    编码格式
     * @param signType   签名类型：RSA、RSA2Util、SM2
     * @return
     * @throws SlansApiException
     */
    public static String sign(String content, String privateKey, String charset,
                              String signType) throws SlansApiException {

        return AsymmetricManager.getByName(signType).sign(content, charset, privateKey);
    }

    /**
     * 通用签名方法
     *
     * @param params     待签名内容
     * @param privateKey 私钥
     * @param charset    编码格式
     * @param signType   签名类型：RSA、RSA2Util、SM2
     * @return
     * @throws SlansApiException
     */
    public static String sign(Map<String, String> params, String privateKey, String charset,
                              String signType) throws SlansApiException {
        String signContent = getSignContent(params);
        return AsymmetricManager.getByName(signType).sign(signContent, charset, privateKey);
    }

    /**
     * 密钥模式RSA、RSA2Util、SM2通用验签方法，主要是用于生活号相关的事件消息和口碑服务市场订购信息等发送到应用网关地址的异步信息的验签
     *
     * @param params    待验签的从支付宝接收到的参数Map
     * @param publicKey 支付宝公钥
     * @param charset   参数内容编码集
     * @param signType  指定采用的签名方式，RSA、RSA2Util、SM2
     * @return true：验签通过；false：验签不通过
     * @throws SlansApiException
     */
    public static boolean verify(Map<String, String> params, String publicKey,
                                   String charset, String signType) throws SlansApiException {
        String sign = params.get("sign");
        String content = getSignCheckContent(params);

        return AsymmetricManager.getByName(signType).verify(content, charset, publicKey, sign);
    }

    /**
     * 公钥加密
     *
     * @param content   待加密内容
     * @param publicKey 公钥
     * @param charset   字符集，如UTF-8, GBK, GB2312
     * @param signType  指定采用的签名方式，RSA、RSA2Util、SM2
     * @return 密文内容
     * @throws SlansApiException
     */
    public static String encrypt(String content, String publicKey,
                                 String charset, String signType) throws SlansApiException {
        return AsymmetricManager.getByName(signType).encrypt(content, charset, publicKey);
    }

    /**
     * 私钥解密
     *
     * @param content    待解密内容
     * @param privateKey 私钥
     * @param charset    字符集，如UTF-8, GBK, GB2312
     * @param signType   指定采用的签名方式，RSA、RSA2Util、SM2
     * @return 明文内容
     * @throws SlansApiException
     */
    public static String decrypt(String content, String privateKey,
                                 String charset, String signType) throws SlansApiException {

        return AsymmetricManager.getByName(signType).decrypt(content, charset, privateKey);
    }

    /**
     * @param sortedParams
     * @return
     */
    public static String getSignContent(Map<String, String> sortedParams) {
        StringBuilder content = new StringBuilder();
        List<String> keys = new ArrayList<String>(sortedParams.keySet());
        Collections.sort(keys);
        int index = 0;
        for (String key : keys) {
            String value = sortedParams.get(key);
            if (!StringUtils.isAnyBlank(key) && !StringUtils.isAnyBlank(value)) {
                content.append(index == 0 ? "" : "&").append(key).append("=").append(value);
                index++;
            }
        }
        return content.toString();
    }

    public static String getSignCheckContent(Map<String, String> params) {
        if (params == null) {
            return null;
        }

        params.remove("sign");

        StringBuilder content = new StringBuilder();
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            content.append(i == 0 ? "" : "&").append(key).append("=").append(value);
        }

        return content.toString();
    }

}
