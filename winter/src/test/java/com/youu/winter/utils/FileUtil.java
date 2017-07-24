package com.youu.winter.utils;

public class FileUtil {
	public static void main(String[] args) {
		System.out.println(FileUtil.class.getResource("/"));
		System.out.println(FileUtil.class.getResource("/").getProtocol());
		System.out.println(FileUtil.class.getResource("/").getFile());
		System.out.println(FileUtil.class.getClassLoader().getResource(""));
	}
}
