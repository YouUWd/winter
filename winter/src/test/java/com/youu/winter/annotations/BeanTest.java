package com.youu.winter.annotations;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.youu.winter.bizz.service.HelloService;
import com.youu.winter.utils.BeanFactoryUtil;
import com.youu.winter.utils.PackageScanUtil;

public class BeanTest {

	@Test
	public void test() throws InstantiationException, IllegalAccessException {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		PackageScanUtil.findClassesInPackage("", classes);
		System.out.println(classes);
		boolean annotationPresent = HelloService.class.isAnnotationPresent(Component.class);
		boolean annotationPresent1 = HelloService.class.isAnnotationPresent(Controller.class);
		System.out.println(annotationPresent);
		System.out.println(annotationPresent1);
		System.out.println(HelloService.class.getName());
		System.out.println(BeanFactoryUtil.init(classes));
		((BeanTest)null).t();
	}

	static void t(){
		System.out.println("null static");
	}
}
