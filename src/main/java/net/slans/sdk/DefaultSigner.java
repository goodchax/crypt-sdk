/**
 * Alipay.com Inc. Copyright (c) 2004-2018 All Rights Reserved.
 */
package net.slans.sdk;

import net.slans.sdk.internal.util.SlansSignature;

/**
 * @author liuqun.lq
 * @version $Id: DefaultSigner.java, v 0.1  12:02 liuqun.lq Exp $
 */
public class DefaultSigner implements Signer {

    private String privateKey;

    public DefaultSigner(String privateKey) {
        this.privateKey = privateKey;
    }

    public String sign(String sourceContent, String signType, String charset) {
        String sign = null;
        try {
            sign = SlansSignature.rsaSign(sourceContent, this.privateKey, charset, signType);
        } catch (SlansApiException e) {
            throw new RuntimeException(e);
        }
        return sign;
    }

    /**
     * Getter method for property <tt>privateKey</tt>.
     *
     * @return property value of privateKey
     */
    public String getPrivateKey() {
        return privateKey;
    }

    /**
     * Setter method for property <tt>privateKey</tt>.
     *
     * @param privateKey value to be assigned to property privateKey
     */
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}