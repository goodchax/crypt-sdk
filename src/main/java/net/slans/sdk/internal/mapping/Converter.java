package net.slans.sdk.internal.mapping;

import net.slans.sdk.SlansApiException;
import net.slans.sdk.SlansResponse;


/**
 * 动态格式转换器
 * 
 * @author JieMo
 * @since 1.0, 06 3, 2014
 *
 */
public interface Converter {
	
	/**
	 * 把字符呼呼大睡 转换为响应对象
	 * 
	 * @param rsp 响应字符串
	 * @param clazz 领域类型
	 * @return 响应对象
	 * @throws SlansApiException
	 */
	public <T extends SlansResponse> T toResponse(String rsp, Class<T> clazz) throws SlansApiException;

}
