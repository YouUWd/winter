package com.youu.winter.utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.youu.winter.annotations.Component;
import com.youu.winter.annotations.Controller;
import com.youu.winter.annotations.RequestMapping;

public class BeanFactoryUtil {

	private static Map<Class<?>, Object> beans = new HashMap<Class<?>, Object>();
	private static Map<String, Pair<Object, Method>> ctrls = new HashMap<String, Pair<Object, Method>>();

	public static Pair<Map<Class<?>, Object>, Map<String, Pair<Object, Method>>> init(List<Class<?>> classes)
			throws InstantiationException, IllegalAccessException {
		for (Class<?> clazz : classes) {
			// 组件
			if (clazz.isAnnotationPresent(Component.class)) {
				beans.put(clazz, clazz.newInstance());
			} else if (clazz.isAnnotationPresent(Controller.class)) {// controller
				Object instance = clazz.newInstance();
				beans.put(clazz, instance);
				initController(clazz, instance);
			}
		}
		return new Pair<Map<Class<?>, Object>, Map<String, Pair<Object, Method>>>(beans, ctrls);
	}

	private static void initController(Class<?> clazz, Object bean) {
		String majPath = ((RequestMapping) clazz.getAnnotation(RequestMapping.class)).value();
		Method[] declaredMethods = clazz.getDeclaredMethods();
		for (Method method : declaredMethods) {

			if (method.isAnnotationPresent(RequestMapping.class)) {
				String mijPath = method.getAnnotation(RequestMapping.class).value();
				if ("".equals(majPath) && "".equals(mijPath)) {
					// 空白url不予配置
				} else {
					ctrls.put(majPath + mijPath, new Pair<Object, Method>(bean, method));
				}
			}
		}

	}
}
