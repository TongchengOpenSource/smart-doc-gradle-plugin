<h1 align="center"><a href="https://github.com/shalousun/smart-doc-gradle-plugin" target="_blank">Smart-Doc Gradle Plugin</a></h1>

![gradle](https://img.shields.io/gradle-plugin-portal/v/com.ly.smart-doc)
[![License](https://img.shields.io/badge/license-Apache%202-green.svg)](https://www.apache.org/licenses/LICENSE-2.0)
![closed pull requests](https://img.shields.io/github/issues-pr-closed-raw/shalousun/smart-doc-gradle-plugin)
![java version](https://img.shields.io/badge/JAVA-1.8+-green.svg)

## Introduce
`smart-doc-gradle-plugin`是`smart-doc`官方团队开发的`gradle`插件，该插件从`smart-doc 1.8.6`版本开始提供，
使用`smart-doc-gradle-plugin`更方便用户集成到自己的项目中，集成也更加轻量，你不再需要在项目中编写单元测试来
启动`smart-doc`扫描代码分析生成接口文档。可以直接运行`gradle`命令
或者是`IDE`中点击`smart-doc-gradle-plugin`预设好的`task`即可生成接口文档。
`smart-doc-gradle-plugin`底层完全依赖于官方开源的`smart-doc`解析库.
[关于smart-doc](https://github.com/TongchengOpenSource/smart-doc)
## Best Practice
`smart-doc` + [Torna](http://torna.cn) 组成行业领先的文档生成和管理解决方案，使用`smart-doc`无侵入完成`Java`源代码分析和提取注释生成`API`文档，自动将文档推送到`Torna`企业级接口文档管理平台。

![smart-doc+torna](https://gitee.com/smart-doc-team/smart-doc/raw/master/images/smart-doc-torna.png)

[smart-doc+Torna文档自动化](https://smart-doc-group.github.io/#/zh-cn/integrated/torna)
## Getting started
### Add plugin
Using the plugins DSL:
```
plugins {
  id "com.ly.smart-doc" version "[最新版本]"
}
```
Using legacy plugin application:
```
buildscript {
    repositories {
        maven { url 'https://maven.aliyun.com/repository/public' }
        maven { url = uri("https://plugins.gradle.org/m2/") }
        mavenCentral()
    }
    dependencies {
        classpath 'com.ly.smart-doc:smart-doc-gradle-plugin:[最新版本]'
    }
}
apply(plugin = "com.ly.smart-doc")
```
### Plugin options

| Option     | Default value                   | Description                                                                                  |
|------------|---------------------------------|----------------------------------------------------------------------------------------------|
| configFile | src/main/resources/default.json | 插件配置文件                                                                                       |
| exclude    | 无                               | 排除一些无法自定下载的java lib sources,例如:exclude 'org.springframework.boot:spring-boot-starter-tomcat' |
| include    | 无                               | 让插件自定下载指定的java lib sources,例如:include 'org.springframework.boot:spring-boot-starter-tomcat'  |

Example setting of options:
```
smartdoc {
    configFile = file("src/main/resources/smart-doc.json")
    
    // exclude example
    // exclude artifact
    exclude 'org.springframework.boot:spring-boot-starter-tomcat'
    // exclude artifact use pattern
    exclude 'org.springframework.boot.*'
    // 你可以使用include配置来让插件自动加载指定依赖的source.
    include 'org.springframework.boot:spring-boot-starter-tomcat'
}
```
对于多模块的`Gradle`，把`smart-doc`插件相关配置放到根目录`build.gradle`的`subprojects`中。

```
subprojects{
    apply plugin: 'com.ly.smart-doc'
    smartdoc {
        //
        configFile = file("src/main/resources/smart-doc.json")
        // exclude artifact
        exclude 'org.springframework.boot:xx'
        exclude 'org.springframework.boot:ddd'
        // 你可以使用include配置来让插件自动加载指定依赖的source.
        include 'org.springframework.boot:spring-boot-starter-tomcat'
    }
}
```
多模块`smart-doc`的实战demo参考
```
https://gitee.com/devin-alan/smart-doc-gradle-plugin-demo
```
> 多模块和单模块项目是有区别，多模块不从根目录使用命令构建可能会导致模块间源代码加载失败，生成文档出现各种问题。
### Create a json config 
在自己的项目中创建一个`json`配置文件，如果是多个模块则放到需要生成文档的模块中，`smart-doc-gradle-plugin`插件会根据这个配置生成项目的接口文档。
例如在项目中创建`/src/main/resources/smart-doc.json`。配置内容参考如下。

**最小配置单元:**
```
{
   "outPath": "D://md2" //指定文档的输出路径 相对路径时请写 ./ 不要写 / eg:./src/main/resources/static/doc
}
```
>如果你想把html文档也打包到应用中随着服务一起访问，则建议你配置路径为：src/main/resources/static/doc。
[服务访问配置参考](https://gitee.com/smart-doc-team/smart-doc/wikis/smart-doc常见问题解决方法?sort_id=2457284)

仅仅需要上面一行配置就能启动`smart-doc-gradle-plugin`插件。

`smart-doc`提供很多配置项，
详细配置请参考[官方文档](https://smart-doc-group.github.io/#/zh-cn/diy/config?id=allconfig)

### Generated document
#### Use Gradle command
```
//生成文档到html中
gradle smartDocRestHtml
//生成markdown
gradle smartDocRestMarkdown
//生成adoc
gradle smartDocRestAdoc
//生成postmanjson数据
gradle smartDocPostman
//生成Open Api 3.0 +规范的json文档,since smart-doc-gradle-plugin 1.1.4
gradle smartDocOpenApi
//生成rest接口文档并推送到Torna平台,@since 2.0.9
gradle tornaRest
//生成Jmeter性能压测脚本,since 3.0.0
gradle smartDocJmeter
//生成文档输出到Word,since 3.0.0
gradle word


// Apache Dubbo Rpc生成
// Generate html
gradle smartDocRpcHtml
// Generate markdown
gradle smartDocRpcMarkdown
// Generate adoc
gradle smartDocRpcAdoc
// 推送rpc接口到torna中
gradle tornaRpc
```
#### Use IDEA
当你使用`IDEA`时，可以通过`maven Helper`插件选择生成何种文档。

![idea中smart-doc-gradle插件使用](https://gitee.com/smart-doc-team/smart-doc-gradle-plugin/raw/master/images/idea.png "usage.png")

### Generated document example
[点击查看文档生成文档效果图](https://gitee.com/smart-doc-team/smart-doc/wikis/文档效果图?sort_id=1652819)

## 构建和发布
您可以使用以下命令进行构建。（构建主分支需要`Java 1.8`,`Gradle 7.6+`）

### 发布到Maven本地仓库
将`Gradle`插件安装到位于`~/.m2/repository`的本地`Maven`仓库。如果您的本地`Maven`仓库路径不是`~/.m2/repository`，
建议首先设置一个全局的`M2_HOME`（`Maven`安装路径）系统变量。`Gradle`将自动搜索它。

```groovy
gradle publishToMavenLocal
```

### 发布到Nexus
通过修改`build.gradle`中的仓库地址配置，将`Gradle`插件发布到您自己的`Nexus`仓库。

```groovy
gradle publish
```

### 发布到Gradle插件仓库
发布到https://plugins.gradle.org/

```groovy
gradlew publishPlugins
```

## Releases
[发布记录](https://gitee.com/smart-doc-team/smart-doc-maven-plugin/blob/master/CHANGELOG.md)
## Other reference
- [smart-doc功能使用介绍](https://my.oschina.net/u/1760791/blog/2250962)
- [smart-doc官方wiki](https://gitee.com/smart-doc-team/smart-doc/wikis/Home?sort_id=1652800)
## License
smart-doc-gradle-plugin is under the Apache 2.0 license.  See the [LICENSE](https://gitee.com/smart-doc-team/smart-doc/blob/master/license.txt) file for details.

**注意：** smart-doc源代码文件全部带有版权注释，使用关键代码二次开源请保留原始版权，否则后果自负！
## Who is using
> 排名不分先后，更多接入公司，欢迎在[用户登记](https://github.com/TongchengOpenSource/smart-doc/issues/12)登记（仅供开源用户参考）

![IFLYTEK](https://gitee.com/smart-doc-team/smart-doc/raw/master/images/known-users/iflytek.png)
&nbsp;&nbsp;<img src="https://gitee.com/smart-doc-team/smart-doc/raw/master/images/known-users/oneplus.png" title="一加" >
&nbsp;&nbsp;<img src="https://gitee.com/smart-doc-team/smart-doc/raw/master/images/known-users/xiaomi.png" title="小米" >
&nbsp;&nbsp;&nbsp;<img src="https://gitee.com/smart-doc-team/smart-doc/raw/master/images/known-users/shunfeng.png" title="顺丰">
&nbsp;&nbsp;&nbsp;<img src="https://gitee.com/smart-doc-team/smart-doc/raw/master/images/known-users/ly.jpeg" title="同程旅行" width="160px" height="70px"/>
&nbsp;&nbsp;&nbsp;<img src="https://gitee.com/smart-doc-team/smart-doc/raw/master/images/known-users/kuishou.png" title="快手">
&nbsp;&nbsp;&nbsp;<img src="https://gitee.com/smart-doc-team/smart-doc/raw/master/images/known-users/mafengwo.png" title="马蜂窝">
&nbsp;&nbsp;<img src="https://gitee.com/smart-doc-team/smart-doc/raw/master/images/known-users/yunda.png" title="韵达速递" width="192px" height="64px">
&nbsp;&nbsp;<img src="https://gitee.com/smart-doc-team/smart-doc/raw/master/images/known-users/zhongtongzhiyun.png" title="中通智运">
&nbsp;&nbsp;<img src="https://gitee.com/smart-doc-team/smart-doc/raw/master/images/known-users/tcsklogo.jpeg" title="同程数科" width="170px" height="64px"/>
&nbsp;&nbsp;<img src="https://gitee.com/smart-doc-team/smart-doc/raw/master/images/known-users/flipboard.png" title="红板报">
&nbsp;&nbsp;<img src="https://gitee.com/smart-doc-team/smart-doc/raw/master/images/known-users/dianxin.png" title="中国电信">
&nbsp;&nbsp;<img src="https://gitee.com/smart-doc-team/smart-doc/raw/master/images/known-users/yidong.png" title="中国移动">
&nbsp;&nbsp;<img src="https://gitee.com/smart-doc-team/smart-doc/raw/master/images/known-users/neusoft.png" title="东软集团">
&nbsp;&nbsp;<img src="https://gitee.com/smart-doc-team/smart-doc/raw/master/images/known-users/zhongkezhilian.png" title="中科智链" width="240px" height="64px"/>
&nbsp;&nbsp;<img src="https://www.hand-china.com/static/img/hand-logo.svg" title="上海汉得信息技术股份有限公司" width="240px" height="64px"/>
&nbsp;&nbsp;<img src="https://gitee.com/smart-doc-team/smart-doc/raw/master/images/known-users/yuanmengjiankang.png" title="远盟健康" width="230px" height="64px"/>



## Contact
愿意参与构建`smart-doc`或者是需要交流问题可以加入`qq`群：

<img src="https://gitee.com/smart-doc-team/smart-doc/raw/master/images/smart-doc-qq.png" title="qq群" width="200px" height="200px"/>
