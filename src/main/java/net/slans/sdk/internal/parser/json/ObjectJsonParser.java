package net.slans.sdk.internal.parser.json;

import net.slans.sdk.SlansApiException;
import net.slans.sdk.SlansParser;
import net.slans.sdk.SlansResponse;
import net.slans.sdk.internal.mapping.Converter;

public class ObjectJsonParser<T extends SlansResponse> implements SlansParser<T> {

	private Class<T> clazz;
	
	public ObjectJsonParser(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public T parse(String rsp) throws SlansApiException {
		Converter converter = new JsonConverter();
		
		return converter.toResponse(rsp, clazz);
	}

	@Override
	public Class<T> getResponseClass() {
		return clazz;
	}

	
	
}
