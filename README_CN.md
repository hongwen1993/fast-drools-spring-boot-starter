# Fast Drools [English](./README.md) 


![FastDroolsLogo](https://github.com/hongwen1993/fast-drools-spring-boot-starter/blob/master/logo.png)

[![Build Status](https://travis-ci.org/apache/dubbo.svg?branch=master)](https://github.com/hongwen1993/fast-drools-spring-boot-starter)
![LICENSE](https://img.shields.io/badge/license-Apache--2.0-brightgreen)
![Language](https://img.shields.io/badge/language-JAVA-blue)
[![Average time to resolve an issue](http://isitmaintained.com/badge/resolution/hongwen1993/fast-drools-spring-boot-starter.svg)](http://isitmaintained.com/project/hongwen1993/fast-drools-spring-boot-starter "Average time to resolve an issue")
[![Percentage of issues still open](http://isitmaintained.com/badge/open/hongwen1993/fast-drools-spring-boot-starter.svg)](http://isitmaintained.com/project/hongwen1993/fast-drools-spring-boot-starter "Percentage of issues still open")

## Introduction
`fast-drools-spring-boot-starter` 将 SpringBoot 与规则引擎 Drools 完美融合，提供了更高效，更便捷地访问方式，同时无感知地实现规则文件的动态更新，适用于生产环境的热部署。


## Contact
有问必答，上到宇宙洪荒，下到八字良缘。更期待您的意见和建议，不定期更新各种技术栈讲解视频。

- QQ群：476067618
- Drools视频教程：https://space.bilibili.com/564757


## FAQ
- 1）Demo地址
    - https://github.com/hongwen1993/all/tree/master/java/drools/boot-drools

- 2）为什么在开发时，无法动态更新规则？
    - 开发时，不推荐使用 Intellij IDEA 等开发工具打开规则文件进行修改，因为可能并不会自动保存规则文件，如果一定要使用，则修改规则文件后，按 Ctrl + F9，或手动编译当前规则文件。

- 3）...


## Usage
- 1）在`pom.xml`中引入依赖（**update 2022/01/20**）：

  ```xml
  <dependency>
      <groupId>com.github.hongwen1993</groupId>
      <artifactId>fast-drools-spring-boot-starter</artifactId>
      <version>8.0.9</version>
  </dependency>
  ```
  
- 2）在配置文件中指定规则文件的路径

  ```xml
  ################## 必填属性 ##################
  # 指定规则文件目录，会自动扫描该目录下所有规则文件，决策表，以及CSV文件
  # 支持classpath资源目录，如：classpath:drools/**/*.drl
  # win 系统注意使用反斜杠，如：C:\\DRL\\
  # linux 系统注意使用斜杠，如：/usr/local/drl/
  spring.drools.path = C:\\DRL\\
  ################## 可选属性 ##################
  # 也可以指定全局的mode，选择stream或cloud（默认stream模式）
  spring.drools.mode = stream
  # 自动更新，on 或 off（默认开启）
  spring.drools.auto-update = on
  # 指定规则文件自动更新的周期，单位秒（默认30秒扫描偶一次）
  spring.drools.update = 10
  # 规则监听日志，on 或 off（默认开启）
  spring.drools.listener = on
  # 开启 drl 语法检查，on 或 off（默认关闭）
  spring.drools.verify = off
  # 指定规则文件的字符集（默认 UTF-8）
  spring.drools.charset = GBK
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
  
- 5）结果展示

  ```java
  2020-09-10 16:51:08.344 DEBUG ===>>开始更新规则文件
  2020-09-10 16:51:09.730 DEBUG ===>>插入对象：[fact 0:1:1571707504:1072693248:1:DEFAULT:NON_TRAIT:java.lang.Double:1.0]；操作规则：null
  2020-09-10 16:51:09.748 DEBUG ===>>匹配的规则：[Rule name=规则1-1, agendaGroup=MAIN, salience=0, no-loop=false]
  2020-09-10 16:51:09.761 DEBUG ===>>开始执行Java代码块，匹配规则：[Rule name=规则1-1, agendaGroup=MAIN, salience=0, no-loop=false]，评估对象：[[fact 0:1:1571707504:1072693248:1:DEFAULT:NON_TRAIT:java.lang.Double:1.0]]
  .... 执行过程忽略 ....
  2020-09-10 16:51:09.765 DEBUG ===>>结束执行Java代码块，匹配规则：[Rule name=规则1-1, agendaGroup=MAIN, salience=0, no-loop=false]，评估对象：[[fact 0:1:1571707504:1072693248:1:DEFAULT:NON_TRAIT:java.lang.Double:1.0]]
  ```

（KieTemplate 下封装了许多 Drools 的功能，许多便捷的 API 等你来发现！）


## Available Features

- [x] SpringBoot 与 Drools 快速整合，再也不需要配置繁琐的 kmodule.xml 啦。
- [x] 指定文件名执行评估规则，更加直观的流程分析。
- [x] 规则文件动态加载
- [x] 规则文件分组控制
- [x] 使用NIO的文件映射，更快速的文件的读写
- [x] 基于缓存的规则文件控制，更高效的规则评估
- [x] 支持各种路径格式
- [x] 支持xls和xlsx格式的规则表文件
- [x] 日志监控流程中规则与评估对象的动态
- [ ] 执行速度与性能的极致，且可定制


## Features in Progress

- 规则执行监控
- 基于分布式的评估与决策
- 规则执行成功失败率统计
- 规则文件的GUI控制
- 决策表的GUI控制
- 分布式规则文件的存储


## License
[Apache 2.0](/LICENSE)


## User List

如果你在使用 fast-drools，并且觉得他还不错，请告诉我（仅仅用于推广 Thanks♪(･ω･)ﾉ ）。


<div>
<table>
  <tbody>
  <tr></tr>
    <tr>
      <td align="center"  valign="middle">
        <a href="" target="_blank">
          <img width="222px"  src="https://s3.ax1x.com/2020/11/19/DKBxde.png">
        </a>
      </td>
      <td align="center"  valign="middle">
        <a href="" target="_blank">
          <img width="222px"  src="https://s3.ax1x.com/2020/11/19/DKyom6.png">
        </a>
      </td>
      <td align="center"  valign="middle">
        <a href="" target="_blank">
          <img width="222px"  src="https://s3.ax1x.com/2020/11/19/DKy5Ox.png">
        </a>
      </td>
      <td align="center"  valign="middle">
        <a href="" target="_blank">
          <img width="222px"  src="https://s3.ax1x.com/2020/11/19/DKy7TO.png">
        </a>
      </td>
      <td align="center"  valign="middle">
        <a href="" target="_blank">
          <img width="222px"  src="https://s3.ax1x.com/2020/11/19/DKyqte.png">
        </a>
      </td>
    </tr>
    <tr></tr>
  </tbody>
</table>
</div>



## Stargazers over time

[![Stargazers over time](https://starchart.cc/hongwen1993/fast-drools-spring-boot-starter)](https://starchart.cc/hongwen1993/fast-drools-spring-boot-starter)

