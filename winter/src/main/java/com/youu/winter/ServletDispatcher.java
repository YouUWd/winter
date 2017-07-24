package com.youu.winter;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.youu.winter.utils.BeanFactoryUtil;
import com.youu.winter.utils.IocUtil;
import com.youu.winter.utils.PackageScanUtil;
import com.youu.winter.utils.Pair;

public class ServletDispatcher extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7079056072986765801L;
	private static final String basePackage = "com.youu.winter.bizz";

	// IOC
	private static Pair<Map<Class<?>, Object>, Map<String, Pair<Object, Method>>> beanAndControllers;

	@Override
	public void init(ServletConfig config) throws ServletException {
		// 扫描基包
		List<Class<?>> classes = new ArrayList<Class<?>>();
		PackageScanUtil.findClassesInPackage(basePackage, classes);
		// 初始化bean + URL mapping
		try {
			beanAndControllers = BeanFactoryUtil.init(classes);
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// IOC注入
		IocUtil.init(beanAndControllers.getF());
		System.out.println(beanAndControllers);
	}

	@Override
	public void init() throws ServletException {
		super.init();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println(req.getContextPath());
		System.out.println(req.getRequestURI());
		System.out.println(req.getRequestURL());

		String url = req.getRequestURI().replaceFirst(req.getContextPath(), "");
		Pair<Object, Method> pair = beanAndControllers.getS().get(url);
		PrintWriter w = resp.getWriter();
		if (pair == null) {
			w.println("Hello " + req.getParameter("name") + " !");
		} else {
			try {
				Object result = pair.getS().invoke(pair.getF(), req, resp);

				w.println("Result is " + result + " !");
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		w.flush();
		w.close();

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
