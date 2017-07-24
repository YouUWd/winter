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
 * @desc 包扫描（注解方式）
 * @author YouN
 *
 */
public class PackageScanUtil {
	/**
	 * 获得包下面的所有的class
	 * 
	 * @param pack
	 *            package完整名称
	 * @return List包含所有class的实例
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public static List<Class> getClasssFromPackage(String pack) throws IOException {
		List<Class> clazzs = new ArrayList<Class>();
		Enumeration<URL> base = PackageScanUtil.class.getClassLoader().getResources(pack.replaceAll("\\.", "/"));
		System.out.println(base);

		while (base.hasMoreElements()) {
			URL url = base.nextElement();
			System.out.println(url);
			String protocol = url.getProtocol();
			if ("file".equals(protocol)) {
				String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
				System.out.println(filePath);
			} else if ("jar".equals(protocol)) {
				// 暂不考虑
			}
		}
		return clazzs;
	}

	public static void findClassesInPackage(String pckg, List<Class<?>> classes) {
		URL resource = PackageScanUtil.class.getClassLoader().getResource(pckg.replaceAll("\\.", "/"));
		File fileOfPackage = new File(resource.getPath());
		if (!fileOfPackage.exists() || !fileOfPackage.isDirectory()) {
			System.out.println(fileOfPackage + " NO EXISTS OR A DIRECTORY");
			return;
		}
		File[] files = fileOfPackage.listFiles(new FileFilter() {

			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				} else {
					try {
						String fileName = f.getName();
						if (fileName.endsWith(".class"))
							classes.add(PackageScanUtil.class.getClassLoader()
									.loadClass(pckg + "." + fileName.substring(0, fileName.indexOf(".class"))));
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					return false;
				}
			}
		});
		for (File sf : files) {
			findClassesInPackage(pckg + "." + sf.getName(), classes);
		}
	}

	/**
	 * 从jar文件中读取指定目录下面的所有的class文件
	 * 
	 * @param jarPaht
	 *            jar文件存放的位置
	 * @param filePaht
	 *            指定的文件目录
	 * @return 所有的的class的对象
	 */
	@SuppressWarnings("rawtypes")
	public List<Class> getClasssFromJarFile(String jarPaht, String filePaht) {
		List<Class> clazzs = new ArrayList<Class>();

		JarFile jarFile = null;
		try {
			jarFile = new JarFile(jarPaht);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		List<JarEntry> jarEntryList = new ArrayList<JarEntry>();

		Enumeration<JarEntry> ee = jarFile.entries();
		while (ee.hasMoreElements()) {
			JarEntry entry = (JarEntry) ee.nextElement();
			// 过滤我们出满足我们需求的东西
			if (entry.getName().startsWith(filePaht) && entry.getName().endsWith(".class")) {
				jarEntryList.add(entry);
			}
		}
		for (JarEntry entry : jarEntryList) {
			String className = entry.getName().replace('/', '.');
			className = className.substring(0, className.length() - 6);

			try {
				clazzs.add(Thread.currentThread().getContextClassLoader().loadClass(className));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		return clazzs;
	}

	@SuppressWarnings("rawtypes")
	public void add(String pack, URL url, List<Class> classes) {
		// 如果是jar包文件
		// 定义一个JarFile
		JarFile jar;
		try {
			// 获取jar
			jar = ((JarURLConnection) url.openConnection()).getJarFile();
			// 从此jar包 得到一个枚举类
			Enumeration<JarEntry> entries = jar.entries();
			// 同样的进行循环迭代
			while (entries.hasMoreElements()) {
				// 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
				JarEntry entry = entries.nextElement();
				String name = entry.getName();
				// 如果是以/开头的
				if (name.charAt(0) == '/') {
					// 获取后面的字符串
					name = name.substring(1);
				}
				String packageDirName = null;
				// 如果前半部分和定义的包名相同
				if (name.startsWith(packageDirName)) {
					int idx = name.lastIndexOf('/');
					String packageName = null;
					// 如果以"/"结尾 是一个包
					if (idx != -1) {
						// 获取包名 把"/"替换成"."
						packageName = name.substring(0, idx).replace('/', '.');
					}
					// 如果可以迭代下去 并且是一个包
					if ((idx != -1)) {
						// 如果是一个.class文件 而且不是目录
						if (name.endsWith(".class") && !entry.isDirectory()) {
							// 去掉后面的".class" 获取真正的类名
							String className = name.substring(packageName.length() + 1, name.length() - 6);
							try {
								// 添加到classes
								classes.add(Class.forName(packageName + '.' + className));
							} catch (ClassNotFoundException e) {
								// log
								// .error("添加用户自定义视图类错误 找不到此类的.class文件");
								e.printStackTrace();
							}
						}
					}
				}
			}
		} catch (IOException e) {
			// log.error("在扫描用户定义视图时从jar包获取文件出错");
			e.printStackTrace();
		}
	}

}
