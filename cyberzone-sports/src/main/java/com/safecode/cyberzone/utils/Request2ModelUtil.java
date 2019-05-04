package com.safecode.cyberzone.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.safecode.cyberzone.base.utils.TypeParseUtil;


public final class Request2ModelUtil {
	private Request2ModelUtil() {
	}

	private static final Logger logger = LogManager.getLogger();

	/**
	 * 适用于 从request接收参数，并返回到指定对象中
	 * @param T
	 * @param request
	 * @return
	 */
	public static final <K> K covert(Class<K> T, HttpServletRequest request) {
		try {
			K obj = T.newInstance();
			// 获取类的方法集合
			Set<Method> methodSet = get_methods(T);
			Iterator<Method> methodIterator = methodSet.iterator();
			while (methodIterator.hasNext()) {
				Method method = methodIterator.next();
				String key = method.getName().substring(3, 4).toLowerCase() + method.getName().substring(4);
				String value = request.getParameter(key);
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

	/**
	 * 取自定义Set方法
	 *
	 * @param T
	 * @return
	 */
	public static final Set<Method> get_declared_methods(Class<?> T) {
		Method[] methods = T.getMethods();
		Set<Method> methodSet = new HashSet<Method>();
		for (Method method : methods) {
			if (method.getName().startsWith("set")) {
				methodSet.add(method);
			}
		}
		return methodSet;
	}

	/**
	 * 取自定义get方法
	 * 
	 * @param T
	 * @return
	 */
	public static final Set<Method> get_getDeclared_methods(Class<?> T) {
		Method[] methods = T.getDeclaredMethods();
		Set<Method> methodSet = new HashSet<Method>();
		for (Method method : methods) {
			if (method.getName().startsWith("get")) {
				methodSet.add(method);
			}
		}
		return methodSet;
	}

	/**
	 * 根据传递的参数修改数据
	 * 
	 * @param o
	 * @param parameterMap
	 */
	public static final void covertObj(Object o, Map<String, String[]> parameterMap) {
		Class<?> clazz = o.getClass();
		Iterator<Map.Entry<String, String[]>> iterator = parameterMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String[]> entry = iterator.next();
			String key = entry.getKey().trim();
			String value = entry.getValue()[0].trim();
			try {
				Method method = setMethod(key, clazz);
				if (method != null) {
					Class<?>[] parameterTypes = method.getParameterTypes();
					if (method != null) {
						Object[] param_value = new Object[] { TypeParseUtil.convert(value, parameterTypes[0], null) };
						method.invoke(o, param_value);
					}
				}
			} catch (Exception e) {
				logger.error("", e);
			}
		}
	}

	/**
	 * 根据传递的参数修改数据
	 * 
	 * @param o
	 * @param parameterMap map参数
	 */
	public static final void covertObjWithMap(Object o, Map<String, String> parameterMap) {
		Class<?> clazz = o.getClass();
		Iterator<Map.Entry<String, String>> iterator = parameterMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			String key = entry.getKey().trim();
			String value = entry.getValue().trim();
			try {
				Method method = setMethod(key, clazz);
				if (method != null) {
					Class<?>[] parameterTypes = method.getParameterTypes();
					if (method != null) {
						Object[] param_value = new Object[] { TypeParseUtil.convert(value, parameterTypes[0], null) };
						method.invoke(o, param_value);
					}
				}
			} catch (Exception e) {
				logger.error("", e);
			}
		}
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
	 * @param obj
	 * @param obiExtend
	 * @return
	 */
	public static final Object init(Object obj, Object obiExtend) {
		Class<?> clazz = obj.getClass();
		Set<Method> getMethods = Request2ModelUtil.get_getDeclared_methods(clazz);
		Iterator<Method> ite = getMethods.iterator();
		while (ite.hasNext()) {
			try {
				Method method = ite.next();
				String name = method.getName();
				String fileName = name.substring(3, 4).toLowerCase() + name.substring(4, name.length());
				Object o = method.invoke(obj);
				Method setMethod = Request2ModelUtil.setMethod(fileName, clazz);
				setMethod.invoke(obiExtend, o);
			} catch (Exception e) {
				logger.error("", e);
			}
		}
		return obiExtend;
	}

	public static final Method setMethod(String fieldName, Class<?> clazz) {
		try {
			Class<?>[] parameterTypes = new Class[1];
			Field field = clazz.getDeclaredField(fieldName);
			parameterTypes[0] = field.getType();
			StringBuffer sb = new StringBuffer();
			sb.append("set");
			sb.append(fieldName.substring(0, 1).toUpperCase());
			sb.append(fieldName.substring(1));
			Method method = clazz.getMethod(sb.toString(), parameterTypes);
			return method;
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
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
