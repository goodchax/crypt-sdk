package net.slans.sdk.request;

import net.slans.sdk.SlansRequest;
import net.slans.sdk.SlansObject;
import net.slans.sdk.internal.util.SlansHashMap;
import net.slans.sdk.response.SSOTokenResponse;

import java.util.Map;

public class SSOTokenRequest implements SlansRequest<SSOTokenResponse> {

    // add user-defined text parameters
    private SlansHashMap udfParams;
    private String apiVersion = null;
    private boolean needEncrypt=false;

    private String corpid;

    private String ssosecret;

    @Override
    public String getApiMethod() {
        return "/oapi/sso/gettoken";
    }

    @Override
    public Map<String, String> getTextParams() {
        SlansHashMap txtParams = new SlansHashMap();
        txtParams.put("corpid", this.corpid);
        txtParams.put("ssosecret", this.ssosecret);
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

    @Override
    public SlansObject getBizModel() {
        return null;
    }

    @Override
    public void setBizModel(SlansObject bizModel) {

    }

    public String getCorpid() {
        return corpid;
    }

    public void setCorpid(String corpid) {
        this.corpid = corpid;
    }

    public String getSsosecret() {
        return ssosecret;
    }

    public void setSsosecret(String ssosecret) {
        this.ssosecret = ssosecret;
    }
}
