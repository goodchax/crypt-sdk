package net.slans.qd.sdk.pay.util;

import net.slans.qd.sdk.pay.SlansApiException;
import net.slans.qd.sdk.pay.SlansConstants;

/**
 * 非对称加密算法管理类
 */
public class AsymmetricManager {

    public static IAsymmetricEncryptor getByName(String signType) throws SlansApiException {
        if (SlansConstants.SIGN_TYPE_RSA.equals(signType)) {
            return new RSAEncryptor();
        }
        if (SlansConstants.SIGN_TYPE_RSA2.equals(signType)) {
            return new RSA2Encryptor();
        }
        if (SlansConstants.SIGN_TYPE_SM2.equals(signType)) {
            return new SM2Encryptor();
        }
        throw new SlansApiException("无效的非对称加密类型:[\" + type + \"]，可选值为：RSA、RSA2和SM2。");
    }

}