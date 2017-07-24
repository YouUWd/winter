package com.youu.winter.utils;

import java.net.URL;

public class LoadClass {

	public static void main(String[] args) throws ClassNotFoundException {
		Class c = LoadClass.class.getClassLoader().loadClass("com.youu.winter.ServletDispatcher");
		System.out.println(c);
		URL resource = PackageScanUtil.class.getClassLoader().getResource("com.youu.winter".replaceAll("\\.", "/"));
		System.out.println(resource);
		System.out.println(resource.getPath());
	}
}
