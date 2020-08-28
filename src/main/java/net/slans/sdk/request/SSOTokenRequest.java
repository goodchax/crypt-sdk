package net.slans.sdk.request;

import net.slans.sdk.IovereyeRequest;
import net.slans.sdk.internal.util.IovereyeHashMap;
import net.slans.sdk.response.SSOTokenResponse;

import java.util.Map;

public class SSOTokenRequest implements IovereyeRequest<SSOTokenResponse> {

    // add user-defined text parameters
    private IovereyeHashMap udfParams;
    private String apiVersion = null;
    private boolean needEncrypt=false;

    private String corpid;

    private String ssosecret;

    @Override
    public String getApiMethod() {
        return "oapi/sso/gettoken";
    }

    @Override
    public Map<String, String> getTextParams() {
        IovereyeHashMap txtParams = new IovereyeHashMap();
        txtParams.put("operator_id", this.corpid);
        txtParams.put("operator_type", this.ssosecret);
        if(udfParams != null) {
            txtParams.putAll(this.udfParams);
        }
        return txtParams;
    }

    @Override
    public String getApiVersion() {
        return apiVersion;
    }

    @Override
    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    @Override
    public Class<SSOTokenResponse> getResponseClass() {
        return SSOTokenResponse.class;
    }

    @Override
    public boolean isNeedEncrypt() {
        return needEncrypt;
    }

    @Override
    public void setNeedEncrypt(boolean needEncrypt) {

    }
}
