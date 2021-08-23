package com.youu.winter.annotations;

import com.youu.winter.utils.BeanFactoryUtil;
import org.junit.Assert;
import org.junit.Test;

public class BeanTest {

    @Test
    public void test() throws InstantiationException, IllegalAccessException {
        BeanFactoryUtil.init("com.youu.winter.annotations");
        System.out.println(BeanFactoryUtil.beans);
        Assert.assertNotNull(BeanFactoryUtil.beans.get(User.class));
    }

}
