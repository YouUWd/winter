package com.youu.winter.sample.bizz;

import com.youu.winter.sample.bizz.controller.HelloController;
import com.youu.winter.utils.BeanFactoryUtil;
import com.youu.winter.utils.IocUtil;
import org.junit.Assert;
import org.junit.Test;

public class HelloControllerTest {

    private static final String basePackage = "com.youu.winter.sample.bizz";


    @Test
    public void hi() {
        // 初始化bean
        try {
            BeanFactoryUtil.init(basePackage);
        } catch (InstantiationException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // IOC注入
        IocUtil.init(BeanFactoryUtil.beans);
        IocUtil.initController(BeanFactoryUtil.controllers, BeanFactoryUtil.beans);
        HelloController h = (HelloController) BeanFactoryUtil.controllers.get(HelloController.class);
        Assert.assertEquals(4, h.hi("Jack"));
    }
}