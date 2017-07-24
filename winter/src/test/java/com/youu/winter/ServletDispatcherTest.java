package com.youu.winter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.youu.winter.bizz.controller.HelloController;
import com.youu.winter.bizz.service.HelloService;
import com.youu.winter.utils.BeanFactoryUtil;
import com.youu.winter.utils.IocUtil;
import com.youu.winter.utils.PackageScanUtil;
import com.youu.winter.utils.Pair;

public class ServletDispatcherTest {

	private static final String basePackage = "com.youu.winter.bizz";

	// IOC
	private static Pair<Map<Class<?>, Object>, Map<String, Pair<Object, Method>>> beanAndControllers;

	@Test
	public void test() {

		// 扫描基包
		List<Class<?>> classes = new ArrayList<Class<?>>();
		PackageScanUtil.findClassesInPackage(basePackage, classes);
		// 初始化bean
		try {
			beanAndControllers = BeanFactoryUtil.init(classes);
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// IOC注入
		IocUtil.init(beanAndControllers.getF());
		System.out.println(beanAndControllers);
		HelloController h = (HelloController) beanAndControllers.getF().get(HelloController.class);
		System.out.println();
		
		Object o =beanAndControllers.getF().get(HelloService.class);
		System.out.println(o);
		try {
			Method m = HelloService.class.getMethod("hi", String.class);
			System.out.println(m.invoke(o, "123"));
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// URL mapping
	}

}
