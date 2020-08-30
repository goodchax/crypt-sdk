package net.slans.sdk.internal.util;


public class RequestParametersHolder {

	private SlansHashMap protocalMustParams;
	private SlansHashMap protocalOptParams;
	private SlansHashMap applicationParams;

	public SlansHashMap getProtocalMustParams() {
		return protocalMustParams;
	}

	public void setProtocalMustParams(SlansHashMap protocalMustParams) {
		this.protocalMustParams = protocalMustParams;
	}

	public SlansHashMap getProtocalOptParams() {
		return protocalOptParams;
	}

	public void setProtocalOptParams(SlansHashMap protocalOptParams) {
		this.protocalOptParams = protocalOptParams;
	}

	public SlansHashMap getApplicationParams() {
		return applicationParams;
	}

	public void setApplicationParams(SlansHashMap applicationParams) {
		this.applicationParams = applicationParams;
	}

}