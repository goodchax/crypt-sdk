package net.slans.qd.sdk.pay;

/**
 * @author runzhi
 */
public class SlansApiException extends Exception {

    private static final long serialVersionUID = -238091758285157331L;

    private String errCode;
    private String errMsg;

    public SlansApiException() {
        super();
    }

    public SlansApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public SlansApiException(String message) {
        super(message);
    }

    public SlansApiException(Throwable cause) {
        super(cause);
    }

    public SlansApiException(String errCode, String errMsg) {
        super(errCode + ":" + errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return this.errCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }
}