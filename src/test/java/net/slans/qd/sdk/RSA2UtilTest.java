package net.slans.qd.sdk;

import net.slans.qd.sdk.pay.SlansApiException;
import net.slans.qd.sdk.pay.SlansConstants;
import net.slans.qd.sdk.pay.util.AsymmetricManager;
import net.slans.qd.sdk.pay.util.RSA2Util;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import java.util.Map;

public class RSA2UtilTest {

    @Test
    public void gen() throws SlansApiException {
        RSA2Util rsa2Util = new RSA2Util();

        // 公私钥对
        Map<String, byte[]> keyMap = RSA2Util.generateKeyBytes();
        String publicKey = Base64.encodeBase64String(keyMap.get(RSA2Util.PUBLIC_KEY));

        System.out.println("验签公钥==>" + publicKey);

        String privateKey = Base64.encodeBase64String(keyMap.get(RSA2Util.PRIVATE_KEY));

        System.out.println("签名私钥==>" + privateKey);

        String sign = AsymmetricManager.getByName(SlansConstants.SIGN_TYPE_RSA2).encrypt("123456", "UTF-8", publicKey);

        System.out.println(sign);


        String planText = AsymmetricManager.getByName(SlansConstants.SIGN_TYPE_RSA2).decrypt(sign, "UTF-8", privateKey);
        System.out.println(planText);
    }
}