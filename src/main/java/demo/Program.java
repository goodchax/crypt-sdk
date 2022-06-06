package demo;

import com.alibaba.fastjson.JSON;
import net.slans.qd.sdk.notify.CallBackContent;
import net.slans.qd.sdk.notify.SlansEncryptor;

public class Program {

	public static void main(String[] args) throws Exception {
		// 需要加密的明文
		String encodingAesKey = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFG";
		String token = "qidian";
		String timestamp = "1409304348000";
		String nonce = "123456";
		String corpid = "slansb0058cd49ba84db386e5e5197809xxxx";
		String replyMsg = "{\"EventType\":\"check_url\",\"TimeStamp\":43535463645,\"UserId\":[\"efefef\",\"111111\"],\"CorpId\":\"corpid\"}";

		// 加密
		SlansEncryptor pc = new SlansEncryptor(token, encodingAesKey, corpid);
		String json = pc.getEncryptMsg(replyMsg, timestamp, nonce);
		System.out.println("加密后: " + json);

		// 解密
		CallBackContent eventCallbackMsg = JSON.parseObject(json, CallBackContent.class);
		String encrypt = eventCallbackMsg.getEncrypt();
		String msgSignature = eventCallbackMsg.getMsgSignature();

		String result2 = pc.getDecryptMsg(msgSignature, timestamp, nonce, encrypt);
		System.out.println("解密后明文: " + result2);
		
	}



}
