package net.slans.sdk;

/**
 * 基于REST的TOP客户端。
 * 
 * @author JieMo
 * @since 1.7, 05 15, 2014
 */
public class DefaultSlansClient extends AbstractSlansClient {

	protected String privateKey;

	private Signer signer;

	public DefaultSlansClient(String url, String appid) {
		super(url, appid, null);
	}

	public DefaultSlansClient(String url, String appid, String privateKey) {
		super(url, appid, null);
		this.privateKey = privateKey;
		this.signer = new DefaultSigner(privateKey);
	}

	public DefaultSlansClient(String url, String appid, String privateKey, String signType) {
		super(url, appid, signType);
		this.privateKey = privateKey;
		this.signer = new DefaultSigner(privateKey);
	}
	
	public DefaultSlansClient(String url, String appid, String privateKey, String signType, int connectTimeout, int readTimeout) {
		super(url, appid, signType, connectTimeout, readTimeout);
		this.privateKey = privateKey;
		this.signer = new DefaultSigner(privateKey);
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
		if (this.signer == null) {
			this.signer = new DefaultSigner(privateKey);
		}
	}

	public Signer getSigner() {
		return signer;
	}

}
