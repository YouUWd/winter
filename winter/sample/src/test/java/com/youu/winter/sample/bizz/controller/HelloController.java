package com.youu.winter.sample.bizz.controller;

import com.youu.winter.annotations.Controller;
import com.youu.winter.annotations.Param;
import com.youu.winter.annotations.Qualifier;
import com.youu.winter.annotations.RequestMapping;
import com.youu.winter.sample.bizz.service.HelloService;

@Controller
@RequestMapping("/h")
public class HelloController {

    @Qualifier
    HelloService helloService;

    @RequestMapping("/hi")
    public int hi(@Param("name") String name) {
        return helloService.hi(name);
    }
}
