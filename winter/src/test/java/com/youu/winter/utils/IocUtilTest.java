package com.youu.winter.utils;

import org.junit.Test;

import com.youu.winter.bizz.controller.HelloController;

public class IocUtilTest {

	@Test
	public void test() {
		IocUtil.initBean(HelloController.class, new HelloController(),null);
	}

}
