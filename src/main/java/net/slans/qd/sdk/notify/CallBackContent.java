package net.slans.qd.sdk.notify;

import java.io.Serializable;

/**
 * 消息对象
 * @author c
 * @date 2018/06/20
 */
public class CallBackContent implements Serializable {

    /**
     * 消息体签名
     */
    private String msgSignature;

    /**
     * 时间戳
     */
    private String timeStamp;

    /**
     * 随机字符串
     */
    private String nonce;

    /**
     * 加密字符串
     */
    private String encrypt;

    public CallBackContent() {
    }

    public CallBackContent(String msgSignature, String timeStamp, String nonce, String encrypt) {
        this.msgSignature = msgSignature;
        this.timeStamp = timeStamp;
        this.nonce = nonce;
        this.encrypt = encrypt;
    }

    public String getMsgSignature() {
        return msgSignature;
    }

    public void setMsgSignature(String msgSignature) {
        this.msgSignature = msgSignature;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }

    @Override
    public String toString() {
        return "CallBackContent{" +
                "msgSignature='" + msgSignature + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", nonce='" + nonce + '\'' +
                ", encrypt='" + encrypt + '\'' +
                '}';
    }
}
