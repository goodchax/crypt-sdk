package net.slans.sdk;

import java.util.Map;

/**
 * TOP请求接口。
 * 
 * @author JieMo
 * @since 1.0, 05 14, 2014
 */
public interface IovereyeRequest<T extends IovereyeResponse> {
	
	/**
	 * 获取TOP的API类型。	 * 
	 * @return API类型
	 */
	public String getApiMethod();

	/**
	 * 获取所有的Key-Value形式的文本请求参数集合。其中：
	 * <ul>
	 * <li>Key: 请求参数名</li>
	 * <li>Value: 请求参数值</li>
	 * </ul> 
	 * @return 文本请求参数集合
	 */
	public Map<String, String> getTextParams();


	/**
	 * 得到当前接口的版本
	 *
	 * @return API版本
	 */
	String getApiVersion();

	/**
	 * 设置当前API的版本信息
	 *
	 * @param apiVersion API版本
	 */
	void setApiVersion(String apiVersion);

	/**
	 * 获取返回资源的类型
	 * @return
	 */
	public Class<T> getResponseClass();

	/**
	 * 判断是否需要加密
	 *
	 * @return
	 */
	boolean isNeedEncrypt();

	/**
	 * 设置请求是否需要加密
	 *
	 * @param needEncrypt
	 */
	void setNeedEncrypt(boolean needEncrypt);
	
}