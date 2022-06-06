package net.slans.qd.sdk;

import net.slans.qd.sdk.pay.SlansApiException;
import net.slans.qd.sdk.pay.SlansConstants;
import net.slans.qd.sdk.pay.SlansSignature;
import net.slans.qd.sdk.pay.util.AsymmetricManager;
import net.slans.qd.sdk.pay.util.RSA2Util;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
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

    @Test
    public void test01() throws SlansApiException, UnsupportedEncodingException {
        String access_token = "6dd68b8ba1924cba97d6a172814fc8ff";
        String jsonStr = "{\"amount\":1,\"bankCardNo\":\"6212263202023504700\",\"bankName\":\"招商银行\",\"name\":\"郑泰平\",\"serialNo\":\"202205201706001\",\"userid\":\"209165314385121280\"}";
        String timestamp = "1653039607628";

//        Map jsonMap = new HashMap();
//        jsonMap.put("serialNo", "KV6975000498311");
//        jsonMap.put("userid", "121212121212");
//        jsonMap.put("name", "测试");
//        jsonMap.put("bankName", "招商银行");
//        jsonMap.put("bankCardNo", "105521000053111111111");
//        jsonMap.put("amount", "1000");

        // 公私钥对
        Map<String, byte[]> keyMap = RSA2Util.generateKeyBytes();

//        String publicKey = Base64.encodeBase64String(keyMap.get(RSA2Util.PUBLIC_KEY));
        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgVLbD7uOX4CX6rLDFylpRe39Pi6YqUAVNxkER9ZbDTzsxursRTCJBCCI7fYg4koBFv861QQnKLnVwv1tXWjuAsFjAwCFpNoTdgw3M79GmPYyzb4mHiAQrre5GKtOwQUEYg2YxlgJBfV/luStt+IaVWPT/dfeUaMrq9g+/2VqK7H0IfRUAkOKr6F5qB7KMxQdIuUCKgG4Pf18nZ230HrxEGoulyKWoAwlXbAi3OXVgf0UBfulZv+X42CzNTbLDmaE8iDhe934mu77mufFi/QpPAhhqQl2lKZ2dQMlScTK2z+qQW2qULY7Iubn7MB36Mnd6i1ow0saW3EE9/YJc0ljdQIDAQAB";
        System.out.println("验签公钥==>" + publicKey);

//        String privateKey = Base64.encodeBase64String(keyMap.get(RSA2Util.PRIVATE_KEY));
        String privateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCBUtsPu45fgJfqssMXKWlF7f0+LpipQBU3GQRH1lsNPOzG6uxFMIkEIIjt9iDiSgEW/zrVBCcoudXC/W1daO4CwWMDAIWk2hN2DDczv0aY9jLNviYeIBCut7kYq07BBQRiDZjGWAkF9X+W5K234hpVY9P9195Royur2D7/ZWorsfQh9FQCQ4qvoXmoHsozFB0i5QIqAbg9/XydnbfQevEQai6XIpagDCVdsCLc5dWB/RQF+6Vm/5fjYLM1NssOZoTyIOF73fia7vua58WL9Ck8CGGpCXaUpnZ1AyVJxMrbP6pBbapQtjsi5ufswHfoyd3qLWjDSxpbcQT39glzSWN1AgMBAAECggEADbDR1BaM+ftJGeWW1aripC6PsreIHdLU7CJP27MLrJkYj+5q2OBMgQ1YsahuzsX6245lkguAgcU6pgviOhNTryL98mFpxIv5p3OfnHDRbnwR4MoSivnmCjkU9VGdYr2/OwopdEX5npHg0f6fjWn4U7XfyW3C8W2OwACCmtMHrFxhomCZ0n4YgQfDcm8SXZjbhXvf2oA63Z9CLlvJdRCOrQ5aAiYzU69bsVgzJCkjy5SXagDh2d3p396EljtojqUfBOIU7qXA2fZQGHSnAuVaEtZdS5BPNgvUb+/vxa2W0gZWFl4rboXDZzsUoF+7y3JmY3R8l0qqDQSUlmTnUexJOQKBgQDOMCwIBTYSvFb1KT7+yVU2NWAoKwW9loiFgHK8hAirhAhjJowbEtFwp6FOspX/SdbgGD8YVBn/jfbYNjxYMPdcxBeyJ2gNhRwxB7crSI25NPg0O+twFzqT9mGNh7WRFPIOumet+MVjECdHkIru6TBl8D0SnPaLEyBk4yd4E2C4kwKBgQCgkPPnN0fz0CdyVZpSlWNaaB4A8lytBpNzU9ouaH0ONxvxpdx2BWsjrNZbRfmk5Y6DrkK37APJkFzG0mfJNHO+ppQmhW+qAdr7B+iF6YCsYtmQKyhm7cZqSe0+sy0YSQcgtEBK1mJFn9ReVRyDDRjY80PMdCOoKeMzHyg9u5Qg1wKBgALwQNBkcp7VvE+GJPWwnNxG6nXoFw7apFtyty/kmdMH8K6pD/oazI1UdkIO3YBj5wIOlmecWzp/JXNzYmzHVd8mlEffs+XtbbdtKrkdMZfgH2FvMS3JxvXw3vcLZhUJPWHkWjHqh7Wft0+XycMZ46ywPc6UGhdlUGLfseJONGStAoGAM443cm9SoBue/twzn/6tlxLFR4KKdVgeMjKjZLFSLiVNyKp7/j1BKIRPk6EFRoga2XcznwCeOrMqxwA6KOW5oGa2PSQtNYZA/MeORqkyEqfuuZbR8zK3udsg3TgE675PFpYp6p2pkjd+pgdoD0A+UTLS+yvzYwSIGhsP/bpkFqMCgYA9M+eNs7Px80G1R2eEwto+r7JgRrp0P5JMMsvCPpbwDfnAuVsdn6aE4j3A/6FbbiOtSi53U327TytfdezQLlSkwxkriwW96LQHK1HVW+Qnv9w9UTQYUOq91R2hCal20dc7RvX6Ra6xOkH73LuLApUf5mr4LF75zd4hIAL7O/XkAw==";
        System.out.println("签名私钥==>" + privateKey);

        Map<String, String> signParams = new HashMap<String, String>();
        signParams.put("access_token", access_token);
        signParams.put("timestamp", timestamp);
        signParams.put("biz_content", jsonStr);

//        String signContent = SlansSignature.getSignContent(signParams);
        String sign = SlansSignature.sign(signParams, privateKey,"UTF-8", SlansConstants.SIGN_TYPE_RSA2);

        System.out.println("加密码后==>："+sign);



        String encode = URLEncoder.encode(sign, "UTF-8");
        System.out.println("encode = [" + encode + "]");
        Map<String, String> verifyParams = new HashMap<String, String>();
        verifyParams.put("sign", sign);
        verifyParams.put("access_token", access_token);
        verifyParams.put("timestamp", timestamp);
        verifyParams.put("biz_content", jsonStr);
        boolean planText = SlansSignature.verify(verifyParams, publicKey, "UTF-8", SlansConstants.SIGN_TYPE_RSA2);
        System.out.println(planText);
    }
}