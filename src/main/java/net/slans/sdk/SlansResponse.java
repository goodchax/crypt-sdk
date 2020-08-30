package net.slans.sdk;

import java.io.Serializable;
import java.util.Map;

import net.slans.sdk.internal.mapping.ApiField;
import org.apache.commons.lang3.BooleanUtils;

public abstract class SlansResponse implements Serializable {

	private static final long serialVersionUID = 7067148970531690343L;

	@ApiField(value="errCode")
	private int errCode;
	
	@ApiField(value="errMsg")
	private String errMsg;
	
	@ApiField(value="subCode")
	private String subCode;
	
	private String body;
	private Map<String, String> params;

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getSubCode() {
		return subCode;
	}

	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public boolean isSuccess() {
		return !BooleanUtils.toBoolean(errCode);
	}
}
