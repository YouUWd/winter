# Winter is coming

利用反射简单实现了spring ioc 和 mvc 的功能

```shell
mvn clean install -Dmaven.test.skip
cd sample
mvn jetty:run
```

## 浏览器验证

> http://127.0.0.1:8080/sample/h/hi?name=abc

## 业务开发

> 在./winter/sample/src/main/webapp/WEB-INF/web.xml 配置你的业务代码包位置  
> 参考代码 ./winter/sample/src/main/java/com/youu/winter/sample/bizz