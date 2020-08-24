package demo;

import com.alibaba.fastjson.JSON;
import net.slans.qd.sdk.security.aes.CallBackContent;
import net.slans.qd.sdk.security.aes.SlansEncryptor;
import okhttp3.*;

import java.io.IOException;

public class PushTest {

	public static void main(String[] args) throws Exception {
		// 需要加密的明文
		String encodingAesKey = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFG";
		String token = "qidian";
		String timestamp = "1409304348000";
		String nonce = "123456";
		String corpid = "slansb0058cd49ba84db386e5e5197809xxxx";
		String replyMsg = "{\"EventType\":\"user_add_org\",\"TimeStamp\":43535463645,\"UserId\":[\"efefef\",\"111111\"],\"CorpId\":\"corpid\"}";

		// 加密
		SlansEncryptor pc = new SlansEncryptor(token, encodingAesKey, corpid);
		CallBackContent content = pc.getEncryptObj(replyMsg, timestamp, nonce);
		String json = JSON.toJSONString(content);
		System.out.println("加密后: " + json);

		// 测试http接口
		json = doPost(json, nonce, timestamp, content.getMsgSignature());
		if (json == null || "".equals(json)) {
			System.out.println("注册回调接口返回空字符");
			return ;
		}

		// 解密
		CallBackContent eventCallbackMsg = JSON.parseObject(json, CallBackContent.class);
		String encrypt = eventCallbackMsg.getEncrypt();
		String msgSignature = eventCallbackMsg.getMsgSignature();

		String result2 = pc.getDecryptMsg(msgSignature, timestamp, nonce, encrypt);
		System.out.println("解密后明文: " + result2);

		//pc.verifyUrl(null, null, null, null);
	}

	public static String doPost(String json, String nonce, String timestamp, String signature) {
		try {
			// url query param
			HttpUrl.Builder urlBuilder = HttpUrl.parse("http://localhost:8280/crmapi/qdCallBack")
					.newBuilder();
			urlBuilder.addQueryParameter("signature", signature);
			urlBuilder.addQueryParameter("timestamp", timestamp);
			urlBuilder.addQueryParameter("nonce", nonce);
			// 资源类型
			MediaType mediaType = MediaType.parse("; charset=utf-8");
			// 请求
			Request request = new Request.Builder()
					.url(urlBuilder.build())
					.addHeader("Content-Type", "application/json")
					.post(RequestBody.create(mediaType, json))
					.build();
			OkHttpClient okHttpClient = new OkHttpClient();
			Response response = okHttpClient.newCall(request).execute();
			if (response.isSuccessful()) {
				return response.body().string();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}



}
