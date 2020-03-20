![FastDroolsLogo](https://github.com/hongwen1993/fast-drools-spring-boot-starter/blob/master/logo.png)

![LICENSE](https://img.shields.io/badge/license-Apache--2.0-brightgreen)
![Language](https://img.shields.io/badge/language-JAVA-blue)


## 介绍
`fast-drools-spring-boot-starter` 将 SpringBoot 与规则引擎 Drools 完美融合，提供了更高效，更便捷地访问方式，同时无感知地实现规则文件的动态更新，适用于生产环境的热部署。


## 讨论
有问必答，上到宇宙洪荒，下到八字良缘。更期待您的意见和建议，不定期更新各种技术栈讲解视频。

- QQ群：476067618
- Drools视频教程：https://space.bilibili.com/564757

## 使用方法
- 1）在`pom.xml`中引入依赖（**update 2020/03/21**）：

  ```xml
  <dependency>
      <groupId>com.github.hongwen1993</groupId>
      <artifactId>fast-drools-spring-boot-starter</artifactId>
      <version>2.2.0</version>
  </dependency>
  ```
- 2）在配置文件中指定规则文件的路径

  ```xml
  # 指定规则文件文件夹，会自动扫描该目录下所有规则文件，决策表，以及CSV文件
  # 支持classpath资源目录，如：classpath:drools/**/*.drl
  spring.drools.path = C:\\DRL\\
  # 也可以指定全局的mode，选择stream或cloud
  spring.drools.mode = stream
  # 指定规则文件自动更新的周期，单位秒，默认30秒扫描一次
  spring.drools.update = 10
  ```
- 3）使用注解方式引入KieTemplate

  ```java
  @Autowired
  private KieTemplate kieTemplate;
  ```
- 4）使用 kieTemplate 的 getKieSession 方法，指定规则文件名，就可以获取对应的 Session，可以传入多个规则文件，包括决策表

  ```java
  KieSession kieSession = kieTemplate.getKieSession("rule1.drl", "rule2.drl");
  ......
  ```

（KieTemplate 下封装了许多 Drools 的功能，许多便捷的 API 等你来发现！）

## 当前功能

- SpringBoot 与 Drools 快速整合，再也不需要配置繁琐的 kmodule.xml 啦。
- 规则文件动态加载
- 规则文件分组控制
- 使用NIO的文件映射，更快速的文件的读写
- 基于缓存的规则文件控制，更高效的规则评估
- 支持各种路径格式
- 支持xls和xlsx格式的规则表文件


## 未来功能

- 选择性记录日志输出
- 规则执行监控
- 基于分布式的评估与决策
- 规则执行成功失败率统计
- 规则文件的GUI控制
- 决策表的GUI控制
- 分布式规则文件的存储


## 开源协议
[Apache 2.0](/LICENSE)

