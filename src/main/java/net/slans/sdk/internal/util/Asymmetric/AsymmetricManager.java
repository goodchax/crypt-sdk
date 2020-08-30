/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package net.slans.sdk.internal.util.Asymmetric;

import net.slans.sdk.SlansApiException;
import net.slans.sdk.SlansConstants;

/**
 * 非对称加密算法管理类
 */
public class AsymmetricManager {

    public static IAsymmetricEncryptor getByName(String type) throws SlansApiException {
        if (SlansConstants.SIGN_TYPE_RSA.equals(type)) {
            return new RSAEncryptor();
        }
        if (SlansConstants.SIGN_TYPE_RSA2.equals(type)) {
            return new RSA2Encryptor();
        }
        if (SlansConstants.SIGN_TYPE_SM2.equals(type)) {
            return new SM2Encryptor();
        }
        throw new SlansApiException("无效的非对称加密类型:[\" + type + \"]，可选值为：RSA、RSA2和SM2。");
    }

}