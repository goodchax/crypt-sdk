package net.slans.sdk;

/**
 * TOP响应解释器接口。响应格式可以是JSON, XML等等。
 * 
 * @author carver.gu
 * @since 1.0, Apr 11, 2010
 */
public interface SlansParser<T extends SlansResponse> {

	/**
	 * 把响应字符串解释成相应的领域对象。
	 * 
	 * @param rsp 响应字符串
	 * @return 领域对象
	 */
	public T parse(String rsp) throws SlansApiException;

	/**
	 * 获取响应类类型。
	 */
	public Class<T> getResponseClass() throws SlansApiException;

}