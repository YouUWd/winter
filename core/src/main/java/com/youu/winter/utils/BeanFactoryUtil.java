package com.youu.winter.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.youu.winter.annotations.Bean;
import com.youu.winter.annotations.Component;
import com.youu.winter.annotations.Controller;
import com.youu.winter.annotations.RequestMapping;

public class BeanFactoryUtil {

    public static Map<Class<?>, Object> beans = new HashMap<>();
    public static Map<Class<?>, Object> controllers = new HashMap<>();
    public static Map<String, Pair<Object, Method>> urlMapping = new HashMap<>();


    public static void init(String basePackage)
            throws InstantiationException, IllegalAccessException {
        List<Class<?>> classes = new ArrayList<>();
        PackageScanUtil.findClassesInPackage(basePackage, classes);

        for (Class<?> clazz : classes) {
            // 组件
            if (clazz.isAnnotationPresent(Component.class) || clazz.isAnnotationPresent(Bean.class)) {//bean or component
                beans.put(clazz, clazz.newInstance());
            } else if (clazz.isAnnotationPresent(Controller.class)) {// controller
                Object instance = clazz.newInstance();
                doMapping(clazz, instance);
                controllers.put(clazz, instance);
            }
        }
    }

    private static void doMapping(Class<?> clazz, Object controller) {
        String majorPath = clazz.getAnnotation(RequestMapping.class).value();
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method : declaredMethods) {

            if (method.isAnnotationPresent(RequestMapping.class)) {
                String minorPath = method.getAnnotation(RequestMapping.class).value();
                if ("".equals(majorPath) && "".equals(minorPath)) {
                    // 空白url不予配置
                } else {
                    urlMapping.put(majorPath + minorPath, new Pair<>(controller, method));
                }
            }
        }
    }


}
