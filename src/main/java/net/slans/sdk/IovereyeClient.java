package net.slans.sdk;


/**
 * API客户端。
 * 
 * @author Jie.mo
 * @since 1.0, Sep 12, 2014
 */
public interface IovereyeClient {

	/**
	 * @param <T>
	 * @param request
	 * @return
	 * @throws ApiException
	 */
	<T extends IovereyeResponse> T execute(IovereyeRequest<T> request) throws ApiException;

	/**
	 * @param <T>
	 * @param request
	 * @return
	 * @throws ApiException
	 */
	<T extends IovereyeResponse> T execute(IovereyeRequest<T> request,
										 String authToken) throws ApiException;

	/**
	 * @param request
	 * @param accessToken
	 * @param appAuthToken
	 * @return
	 * @throws ApiException
	 */
	<T extends IovereyeResponse> T execute(IovereyeRequest<T> request, String accessToken,
										 String appAuthToken) throws ApiException;

	<T extends IovereyeResponse> T execute(IovereyeRequest<T> request, String accessToken,
										 String appAuthToken, String targetAppId) throws ApiException;

	/**
	 * 证书类型调用
	 *
	 * @param <T>
	 * @param request
	 * @return
	 * @throws ApiException
	 */
	<T extends IovereyeResponse> T certificateExecute(IovereyeRequest<T> request) throws ApiException;

	/**
	 * @param request
	 * @param <T>
	 * @return
	 * @throws ApiException
	 */
	<T extends IovereyeResponse> T certificateExecute(IovereyeRequest<T> request,
													String authToken) throws ApiException;

	/**
	 * @param request
	 * @param accessToken
	 * @param appAuthToken
	 * @param <T>
	 * @return
	 * @throws ApiException
	 */
	<T extends IovereyeResponse> T certificateExecute(IovereyeRequest<T> request, String accessToken,
													String appAuthToken) throws ApiException;

	<T extends IovereyeResponse> T certificateExecute(IovereyeRequest<T> request, String accessToken,
													String appAuthToken, String targetAppId) throws ApiException;
}