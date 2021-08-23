package com.youu.winter.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author YouN
 * @desc 包扫描（注解方式）
 */
public class PackageScanUtil {
    /**
     * 获得包下面的所有的class
     *
     * @param pack package完整名称
     * @return List包含所有class的实例
     * @throws IOException
     */
    public static List<Class> findClassesInPackage(String pack) throws IOException {
        List<Class> classes = new ArrayList();
        Enumeration<URL> base = PackageScanUtil.class.getClassLoader().getResources(pack.replaceAll("\\.", "/"));
        while (base.hasMoreElements()) {
            URL url = base.nextElement();
            String protocol = url.getProtocol();
            if ("file".equals(protocol)) {
                String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
            } else if ("jar".equals(protocol)) {
                // 暂不考虑
            }
        }
        return classes;
    }

    public static void findClassesInPackage(String basePackage, List<Class<?>> classes) {
        URL resource = PackageScanUtil.class.getClassLoader().getResource(basePackage.replaceAll("\\.", "/"));
        File fileOfPackage = new File(resource.getPath());
        if (!fileOfPackage.exists() || !fileOfPackage.isDirectory()) {
            System.out.println(fileOfPackage + " NO EXISTS OR A DIRECTORY");
            return;
        }
        File[] files = fileOfPackage.listFiles(f -> {
            if (f.isDirectory()) {
                return true;
            } else {
                try {
                    String fileName = f.getName();
                    if (fileName.endsWith(".class"))
                        classes.add(PackageScanUtil.class.getClassLoader()
                                .loadClass(basePackage + "." + fileName.substring(0, fileName.indexOf(".class"))));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
        for (File sf : files) {
            findClassesInPackage(basePackage + "." + sf.getName(), classes);
        }
    }
}
