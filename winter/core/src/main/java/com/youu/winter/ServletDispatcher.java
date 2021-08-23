package com.youu.winter;

import com.youu.winter.annotations.Param;
import com.youu.winter.utils.BeanFactoryUtil;
import com.youu.winter.utils.IocUtil;
import com.youu.winter.utils.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ServletDispatcher extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ServletDispatcher.class);
    /**
     *
     */
    private static final long serialVersionUID = 7079056072986765801L;


    @Override
    public void init(ServletConfig config) {
        try {
            String basePackage = config.getInitParameter("basePackage");
            // 扫描basePackage
            // 初始化bean + URL mapping
            BeanFactoryUtil.init(basePackage);
        } catch (InstantiationException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // IOC注入
        IocUtil.init(BeanFactoryUtil.beans);
        IocUtil.initController(BeanFactoryUtil.controllers, BeanFactoryUtil.beans);
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.debug("doGet Path={}, URI={}, URL={}", req.getContextPath(), req.getRequestURI(), req.getRequestURL());
        String url = req.getRequestURI().replaceFirst(req.getContextPath(), "");
        Pair<Object, Method> objectMethod = BeanFactoryUtil.urlMapping.get(url);
        PrintWriter w = resp.getWriter();
        if (objectMethod == null) {
            w.println("404 NOT Found !");
        } else {
            try {
                Object object = objectMethod.getF();
                Method method = objectMethod.getS();
                Parameter[] parameters = method.getParameters();
                Object[] params = new Object[parameters.length];
                for (int i = 0; i < parameters.length; i++) {
                    Parameter parameter = parameters[i];
                    Param param = parameter.getAnnotation(Param.class);
                    if (param == null) {
                        throw new RuntimeException("url:" + url + ", " + object.getClass() + "." + method + " must have annotation of Param");
                    }
                    String paramKey = param.value();
                    if (paramKey == null || "".equals(paramKey)) {
                        throw new RuntimeException("url:" + url + ", " + object.getClass() + "." + method + " annotation of Param should have value");
                    }
                    params[i] = req.getParameter(paramKey);
                }
                Object result = method.invoke(object, params);
                w.println("Result is " + result + " !");
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        w.flush();
        w.close();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }

}
