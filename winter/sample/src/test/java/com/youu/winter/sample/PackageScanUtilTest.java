package com.youu.winter.sample;

import com.youu.winter.utils.PackageScanUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PackageScanUtilTest {
    private static final String basePackage = "com.youu.winter.sample.bizz";

    @Test
    public void test() {
        List list = new ArrayList();
        PackageScanUtil.findClassesInPackage(basePackage, list);
        System.out.println(list);
    }
}
