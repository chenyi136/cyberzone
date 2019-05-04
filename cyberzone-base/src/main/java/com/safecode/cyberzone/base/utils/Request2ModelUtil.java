package com.safecode.cyberzone.base.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public final class Request2ModelUtil {
	private Request2ModelUtil() {
	}

	private static final Logger logger = LogManager.getLogger();

	
	/**
	 * 适用于 从map中获取参数，并返回到指定对象中
	 * @param T
	 * @param params
	 * @return
	 */
	public static final <K> K covertByMap(Class<K> T, Map<String, Object> params) {
		try {
			K obj = T.newInstance();
			// 获取类的方法集合
			Set<Method> methodSet = get_methods(T);
			Iterator<Method> methodIterator = methodSet.iterator();
			while (methodIterator.hasNext()) {
				Method method = methodIterator.next();
				String key = method.getName().substring(3, 4).toLowerCase() + method.getName().substring(4);
				//String value = request.getParameter(key);
				Object value = params.get(key);
				Class<?>[] type = method.getParameterTypes();
				Object[] param_value = new Object[] { TypeParseUtil.convert(value, type[0], null) };
				method.invoke(obj, param_value);
			}
			return obj;
		} catch (Exception ex) {
			logger.error("", ex);
		}
		return null;
	}
	
	
	/**
	 * 根据第二个参数，赋值第一个参数
	 * 
	 * @param o
	 * @param paramObj model参数
	 */
	public static final void covertObj(Object o, Object paramObj) {
		Field[] fields = paramObj.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			try {
				Field field = fields[i];
				Method getMethod = getMethod(field.getName(), paramObj.getClass());
				if (getMethod != null) {
					Object value = getMethod.invoke(paramObj);
					
					Method setMethod = null;
					try {
						Class<?>[] parameterTypes = new Class[1];
						Field field2 = o.getClass().getDeclaredField(field.getName());
						parameterTypes[0] = field2.getType();
						StringBuffer sb = new StringBuffer();
						sb.append("set");
						sb.append(field.getName().substring(0, 1).toUpperCase());
						sb.append(field.getName().substring(1));
						setMethod = o.getClass().getMethod(sb.toString(), parameterTypes);
					} catch (Exception e) {
					}
					
					if (setMethod != null) {
						if (value != null && !value.toString().equals("")) {
							setMethod.invoke(o, value);
						}
					}
				}
			} catch (Exception e) {
				logger.error("", e);
			}
		}
	}
	

	/**
	 * 取全部Set方法
	 *
	 * @param T
	 * @return
	 */
	public static final Set<Method> get_methods(Class<?> T) {
		Method[] methods = T.getMethods();
		Set<Method> methodSet = new HashSet<Method>();
		for (Method method : methods) {
			if (method.getName().startsWith("set")) {
				methodSet.add(method);
			}
		}
		return methodSet;
	}
	
	public static final Method getMethod(String fieldName, Class<?> clazz) {
		StringBuffer sb = new StringBuffer();
		sb.append("get");
		sb.append(fieldName.substring(0, 1).toUpperCase());
		sb.append(fieldName.substring(1));
		try {
			return clazz.getMethod(sb.toString());
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}
}