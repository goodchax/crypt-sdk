package net.slans.sdk.internal.parser.json;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import net.slans.sdk.ApiException;
import net.slans.sdk.Constants;
import net.slans.sdk.IovereyeResponse;
import net.slans.sdk.internal.mapping.Converter;
import net.slans.sdk.internal.mapping.Converters;
import net.slans.sdk.internal.mapping.Reader;
import net.slans.sdk.internal.util.json.ExceptionErrorListener;
import net.slans.sdk.internal.util.json.JSONReader;
import net.slans.sdk.internal.util.json.JSONValidatingReader;

public class JsonConverter implements Converter {

	@Override
	public <T extends IovereyeResponse> T toResponse(String rsp, Class<T> clazz)
			throws ApiException {
		JSONReader reader = new JSONValidatingReader(new ExceptionErrorListener());
		Object rootObj = reader.read(rsp);
		if (rootObj instanceof Map<?, ?>) {
			Map<?, ?> rootJson = (Map<?, ?>) rootObj;
			Collection<?> values = rootJson.values();
			for (Object rspObj : values) {
				if (rspObj instanceof Map<?, ?>) {
					Map<?, ?> rspJson = (Map<?, ?>) rspObj;
					return fromJson(rspJson, clazz);
				}
			}
		}
		return null;
	}

	/**
	 * 把JSON格式的数据转换为对象。
	 * 
	 * @param <T> 泛型领域对象
	 * @param json JSON格式的数据
	 * @param clazz 泛型领域类型
	 * @return 领域对象
	 */
	public <T> T fromJson(final Map<?, ?> json, Class<T> clazz)
			throws ApiException {
		return Converters.convert(clazz, new Reader() {

			@Override
			public boolean hasReturnField(Object name) {
				return json.containsKey(name);
			}

			@Override
			public Object getPrimitiveObject(Object name) {
				return json.get(name);
			}

			@Override
			public Object getObject(Object name, Class<?> type)
					throws ApiException {
				Object tmp = json.get(name);
				if (tmp instanceof Map<?, ?>) {
					Map<?, ?> map = (Map<?, ?>) tmp;
					return fromJson(map, type);
				} else {
					return null;
				}
			}

			@Override
			public List<?> getListObjects(Object listName, Object itemName,
					Class<?> subType) throws ApiException {
				List<Object> listObjs = null;

				Object listTmp = json.get(listName);
				if (listTmp instanceof Map<?, ?>) {
					Map<?, ?> jsonMap = (Map<?, ?>) listTmp;
					Object itemTmp = jsonMap.get(itemName);
					if (itemTmp == null && listName != null) {
						String listNameStr = listName.toString();
						itemTmp = jsonMap.get(listNameStr.substring(0,
								listNameStr.length() - 1));
					}
					if (itemTmp instanceof List<?>) {
						listObjs = getListObjectsInner(subType, itemTmp);
					}
				} else if (listTmp instanceof List<?>) {
					listObjs = getListObjectsInner(subType, listTmp);
				}
				return listObjs;
			}

			private List<Object> getListObjectsInner(Class<?> subType, Object itemTmp)
					throws ApiException {
				List<Object> listObjs;
				listObjs = new ArrayList<Object>();
				List<?> tmpList = (List<?>) itemTmp;
				for (Object subTmp : tmpList) {
					Object obj = null;
					if (String.class.isAssignableFrom(subType)) {
						obj = subTmp;
					} else if (Long.class.isAssignableFrom(subType)) {
						obj = subTmp;
					} else if (Integer.class.isAssignableFrom(subType)) {
						obj = subTmp;
					} else if (Boolean.class.isAssignableFrom(subType)) {
						obj = subTmp;
					} else if (Date.class.isAssignableFrom(subType)) {
						DateFormat format = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
						try {
							obj = format.parse(String.valueOf(subTmp));
						} catch (ParseException e) {
							throw new ApiException(e);
						}
					} else if (subTmp instanceof Map<?, ?>) {
						Map<?, ?> subMap = (Map<?, ?>) subTmp;
						obj = fromJson(subMap, subType);
					}

					if (obj != null) {
						listObjs.add(obj);
					}
				}
				return listObjs;
			}

		});
	}

}
