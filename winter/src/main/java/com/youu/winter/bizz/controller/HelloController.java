package com.youu.winter.bizz.controller;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.youu.winter.annotations.Controller;
import com.youu.winter.annotations.Qualifier;
import com.youu.winter.annotations.RequestMapping;
import com.youu.winter.bizz.service.HelloService;

@Controller
@RequestMapping("/h")
public class HelloController {

	@Qualifier
	HelloService helloService;

	@RequestMapping("/hi")
	public int hi(ServletRequest req,ServletResponse resp) {
		int age = helloService.hi(req.getParameter("name"));
		System.out.println(age);
		return age;
	}
}
