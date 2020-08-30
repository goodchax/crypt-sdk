/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */

import net.slans.sdk.SlansApiException;
import net.slans.sdk.internal.util.SlansEncrypt;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author junying
 * @version : EncryptTest.java, v 0.1 2019年12月20日 16:30 junying Exp $
 */
public class EncryptTest {

    @Test
    public void should_get_correct_chipertext() throws SlansApiException {
        //given
        String chipertext = SlansEncrypt.encryptContent("test1234567", "AES", "aa4BtZ4tspm2wnXLb1ThQA==", "utf-8");

        assertThat(chipertext, containsString("ILpoMowjIQjfYMR847rnFQ=="));
    }

    @Test
    public void should_get_correct_plaintext() throws SlansApiException {
        //given
        String plaintext = SlansEncrypt.decryptContent("ILpoMowjIQjfYMR847rnFQ==", "AES", "aa4BtZ4tspm2wnXLb1ThQA==", "utf-8");

        assertThat(plaintext, containsString("test1234567"));
    }

}