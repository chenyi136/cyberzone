package com.safecode.cyberzone.web.utils;

import java.lang.reflect.Field;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class ObjectUtil {
	public static MultiValueMap<String, Object> objectToMap(Object obj) throws IllegalAccessException {
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		Class<?> clazz = obj.getClass();
		for (Field field : clazz.getDeclaredFields()) {
			field.setAccessible(true);
			String fieldName = field.getName();
			Object value = field.get(obj);
			map.add(fieldName, value);
		}
		return map;
	}
}