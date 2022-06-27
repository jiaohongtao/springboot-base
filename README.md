# springboot_html
测试spring和html结合，个人研究

# 已有功能请访问页面
[已有功能](http://localhost:9090/hi/func)

# 参考文献
- [SpringBoot + validation 接口参数校验](https://blog.csdn.net/u014553029/article/details/109192520)


# 开启/关闭 SSL
- 项目ssl
    server:
      ssl:
        enabled: false
- actuator的ssl
    management:
      server:
        ssl:
          enabled: false
- TomcatHttpsConfig的配置（可选），用于端口重定向