package net.slans.sdk;


/**
 * API客户端。
 * 
 * @author Jie.mo
 * @since 1.0, Sep 12, 2014
 */
public interface SlansClient {

	void setHttpMethod(String httpMethod);

	<T extends SlansResponse> T certificateExecute(SlansRequest<T> request) throws SlansApiException;

	<T extends SlansResponse> T certificateExecute(SlansRequest<T> request, String accessToken) throws SlansApiException;

	/**
	 * @param <T>
	 * @param request
	 * @return
	 * @throws SlansApiException
	 */
	<T extends SlansResponse> T execute(SlansRequest<T> request) throws SlansApiException;

	/**
	 * @param <T>
	 * @param request
	 * @return
	 * @throws SlansApiException
	 */
	<T extends SlansResponse> T execute(SlansRequest<T> request,
										String accessToken) throws SlansApiException;

}