package com.youu.winter.utils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;

import com.youu.winter.annotations.Qualifier;

public class IocUtil {

	public static void init(Map<Class<?>, Object> beans) {
		for (Entry<Class<?>, Object> bean : beans.entrySet()) {
			initBean(bean.getKey(), bean.getValue(), beans);
		}

	}

	static void initBean(Class<?> clazz, Object bean, Map<Class<?>, Object> beans) {
		Field[] declaredFields = clazz.getDeclaredFields();
		for (Field field : declaredFields) {
			if (field.isAnnotationPresent(Qualifier.class)) {
				try {
					field.setAccessible(true);
					field.set(bean, beans.get(field.getType()));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
