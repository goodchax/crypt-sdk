package controller;
/*

import com.alibaba.fastjson.JSONObject;
import net.slans.qd.sdk.security.aes.AesException;
import net.slans.qd.sdk.security.aes.SlansEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/")
public class CallBackDemoController {

    public static final String AES_KEY = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFG";
    public static final String TOKEN = "qidian";
    public static final String CORPID = "slansb0058cd49ba84db386e5e5197809xxxx";

    private static Logger logger = LoggerFactory.getLogger(CallBackDemoController.class);

    @RequestMapping(value = "qdCallBack", method = RequestMethod.POST)
    public Object dingCallback(
            @RequestParam(value = "signature") String signature,
            @RequestParam(value = "timestamp") String timestamp,
            @RequestParam(value = "nonce") String nonce,
            @RequestBody(required = false) JSONObject json
    ) {
        String params = " signature:" + signature + " timestamp:" + timestamp + " nonce:" + nonce + " json:" + json;
        logger.info("begin callback:" + params);

        try {
            String encrypt = json.getString("encrypt");

            SlansEncryptor se = new SlansEncryptor(TOKEN, AES_KEY, CORPID);

            String plainText = se.getDecryptMsg(signature, timestamp, nonce, encrypt);
            logger.info("plainText", plainText);

            return se.getEncryptMsg("success", timestamp, nonce);
        } catch (AesException e) {
            e.printStackTrace();
        }
        return null;
    }

}
*/
