package com.youu.winter.sample.bizz.service;

import com.youu.winter.annotations.Component;

@Component
public class HelloService {
	public int hi(String name) {
		return name.length();
	}
}
