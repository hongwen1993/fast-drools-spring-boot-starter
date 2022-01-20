# Fast Drools [中文](./README_CN.md) 


![FastDroolsLogo](https://github.com/hongwen1993/fast-drools-spring-boot-starter/blob/master/logo.png)

[![Build Status](https://travis-ci.org/apache/dubbo.svg?branch=master)](https://github.com/hongwen1993/fast-drools-spring-boot-starter)
![LICENSE](https://img.shields.io/badge/license-Apache--2.0-brightgreen)
![Language](https://img.shields.io/badge/language-JAVA-blue)
[![Average time to resolve an issue](http://isitmaintained.com/badge/resolution/hongwen1993/fast-drools-spring-boot-starter.svg)](http://isitmaintained.com/project/hongwen1993/fast-drools-spring-boot-starter "Average time to resolve an issue")
[![Percentage of issues still open](http://isitmaintained.com/badge/open/hongwen1993/fast-drools-spring-boot-starter.svg)](http://isitmaintained.com/project/hongwen1993/fast-drools-spring-boot-starter "Percentage of issues still open")

## Introduction
`fast-drools-spring-boot-starter` integrates Drools with Springboot providing a more efficient, sophisticated model for rule configuration and management. It is capable of updating rules dynamically with regards to hot deployment.


## Contact
Feel free to ask me any questions with the contacts below.

- QQ group chat：476067618
- Drools video tutorials：https://space.bilibili.com/564757


## FAQ
- 1）Demo？
    - https://github.com/hongwen1993/all/tree/master/java/drools/boot-drools

- 2）Why I can't dynamically update rules in development environment?
    - In development stage, it is not recommended to use tools such as Intellij IDEA for rule modification. If insisted, Ctrl + F9 or compile manually to apply the changes you make.

- 3）...

## Usage
- 1）Inject the following dependency in `pom.xml`（**update 2021/01/20**）：

  ```xml
  <dependency>
      <groupId>com.github.hongwen1993</groupId>
      <artifactId>fast-drools-spring-boot-starter</artifactId>
      <version>8.0.9</version>
  </dependency>
  ```
  
- 2）Designate path to drools rule file in the configruation file

  ```xml
  # (necessary) specify a rule file folder that will automatically scan all rule files, include  decision tables, and CSV files, 
  # also support classpath resource path, example：classpath:drools/**/*.drl
  # win system use \\, example C:\\DRL\\
  # linux system use /, example /var/config/
  spring.drools.path = C:\\DRL\\
  # set up mode with options "stream" or "cloud" (default = stream)
  spring.drools.mode = stream
  # close drl file auto update, on or off (default = on)
  spring.drools.auto-update = on
  # regulates update cycle, unit is seconds (default = 30)
  spring.drools.update = 10
  # monitoring rules, on or off (default = on)
  spring.drools.listener = on
  # open drl syntax checker, on or off (default = off)
  spring.drools.verify = off
  # default drl file charset（default utf-8）
  spring.drools.charset = GBK
  ```
  
- 3）Introduce KieTemplate with annotation

  ```java
  @Autowired
  private KieTemplate kieTemplate;
  ```
  
- 4）Session can be acquired with getKieSession(). Supporting multiple rule files at the same time as well as decisions tables.

  ```java
  KieSession kieSession = kieTemplate.getKieSession("rule1.drl", "rule2.drl");
  ......
  ```
  
- 5）Results

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

- [x] Quick start with Drools-Springboot integration without having to go through the complex configuration process in kmodule.xml
- [x] Specify filenames to perform evaluation rules for more intuitive process analysis.
- [x] Dynamically loads the rules file
- [x] Group rule file
- [x] NIO file mapping for faster file reads and writes
- [x] Use caching to improve rule evaluation
- [x] Support for various path formats
- [x] Supports rule table files in XLS and XLSX formats
- [x] Logging monitors the dynamics of rules and evaluation objects in the process
- [ ] Extreme execution speed and performance, and customizable


## Features in Progress

- Rule execution monitoring
- Distributed based assessment and decision making
- Rule execution success failure rate statistics
- GUI control for rule files
- GUI control for decision tables
- Storage of distributed rule files


## License
[Apache 2.0](/LICENSE)


## User List

If you are using fast-drools and think that it helps you or want do some contributions to it, please call me to let us know just for promotional purposes only.


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
