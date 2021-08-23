package com.youu.winter.utils;

import com.youu.winter.annotations.Qualifier;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;

public class IocUtil {

    public static void init(Map<Class<?>, Object> beans) {
        for (Entry<Class<?>, Object> bean : beans.entrySet()) {
            initBean(bean.getKey(), bean.getValue(), beans);
        }
    }

    public static void initController(Map<Class<?>, Object> controllers, Map<Class<?>, Object> beans) {
        for (Entry<Class<?>, Object> controller : controllers.entrySet()) {
            initBean(controller.getKey(), controller.getValue(), beans);
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
