package net.slans.sdk.internal.parser.json;

import net.slans.sdk.ApiException;
import net.slans.sdk.IovereyeParser;
import net.slans.sdk.IovereyeResponse;
import net.slans.sdk.internal.mapping.Converter;

public class ObjectJsonParser<T extends IovereyeResponse> implements IovereyeParser<T> {

	private Class<T> clazz;
	
	public ObjectJsonParser(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public T parse(String rsp) throws ApiException {
		Converter converter;
		converter = new JsonConverter();
		
		return converter.toResponse(rsp, clazz);
	}

	@Override
	public Class<T> getResponseClass() {
		return clazz;
	}

	
	
}
