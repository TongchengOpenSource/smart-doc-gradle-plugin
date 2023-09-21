<h1 align="center"><a href="https://github.com/shalousun/smart-doc-gradle-plugin" target="_blank">Smart-Doc Gradle Plugin</a></h1>

![gradle](https://img.shields.io/gradle-plugin-portal/v/com.github.shalousun.smart-doc)
[![License](https://img.shields.io/badge/license-Apache%202-green.svg)](https://www.apache.org/licenses/LICENSE-2.0)
![closed pull requests](https://img.shields.io/github/issues-pr-closed-raw/shalousun/smart-doc-gradle-plugin)
![java version](https://img.shields.io/badge/JAVA-1.8+-green.svg)

## Introduce
smart-doc-gradle-plugin是smart-doc官方团队开发的`gradle`插件，该插件从smart-doc 1.8.6版本开始提供，
使用smart-doc-gradle-plugin更方便用户集成到自己的项目中，集成也更加轻量，你不再需要在项目中编写单元测试来
启动smart-doc扫描代码分析生成接口文档。可以直接运行`gradle`命令
或者是IDE中点击smart-doc-gradle-plugin预设好的`task`即可生成接口文档。
smart-doc-gradle-plugin底层完全依赖于官方开源的smart-doc解析库，
因此整个使用过程中遇到问题或者是想查看完整解决方案请前往码云smart-doc的仓库查看wiki文档。

[关于smart-doc](https://gitee.com/smart-doc-team/smart-doc)
## Best Practice
smart-doc + [Torna](http://torna.cn) 组成行业领先的文档生成和管理解决方案，使用smart-doc无侵入完成Java源代码分析和提取注释生成API文档，自动将文档推送到Torna企业级接口文档管理平台。

![smart-doc+torna](https://gitee.com/smart-doc-team/smart-doc/raw/master/images/smart-doc-torna.png)

[smart-doc+Torna文档自动化](https://gitee.com/smart-doc-team/smart-doc/wikis/smart-doc与torna对接?sort_id=3695028)
## Getting started
### Add plugin
Using the plugins DSL:
```
plugins {
  id "com.github.shalousun.smart-doc" version "[最新版本]"
}
```
Using legacy plugin application:
```
buildscript {
    repositories {
        maven { url 'http://maven.aliyun.com/nexus/content/groups/public/' }
        maven { url = uri("https://plugins.gradle.org/m2/") }
        mavenCentral()
    }
    dependencies {
        classpath 'com.github.shalousun:smart-doc-gradle-plugin:[最新版本]'
    }
}
apply(plugin = "com.github.shalousun.smart-doc")
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
对于多模块的gradle，把smart-doc插件相关配置放到根目录build.gradle的subprojects中。

```
subprojects{
    apply plugin: 'com.github.shalousun.smart-doc'
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
多模块smart-doc的实战demo参考
```
https://gitee.com/devin-alan/smart-doc-gradle-plugin-demo
```
> 多模块和单模块项目是有区别，多模块不从根目录使用命令构建可能会导致模块间源代码加载失败，生成文档出现各种问题。
### Create a json config 
在自己的项目中创建一个json配置文件，如果是多个模块则放到需要生成文档的模块中，smart-doc-gradle-plugin插件会根据这个配置生成项目的接口文档。
例如在项目中创建`/src/main/resources/smart-doc.json`。配置内容参考如下。

**最小配置单元:**
```
{
   "outPath": "D://md2" //指定文档的输出路径 相对路径时请写 ./ 不要写 / eg:./src/main/resources/static/doc
}
```
>如果你想把html文档也打包到应用中随着服务一起访问，则建议你配置路径为：src/main/resources/static/doc。
[服务访问配置参考](https://gitee.com/smart-doc-team/smart-doc/wikis/smart-doc常见问题解决方法?sort_id=2457284)

仅仅需要上面一行配置就能启动smart-doc-gradle-plugin插件。

smart-doc提供很多配置项，
详细配置请参考[官方文档](https://smart-doc-group.github.io/#/zh-cn/diy/config?id=allconfig)

### Generated document
#### Use Gradle command
```
//生成html
gradle smartDocRestHtml
//生成markdown
gradle smartDocRestMarkdown
//生成adoc
gradle smartDocRestAdoc
//生成postmanjson数据
gradle smartDocPostman
//生成Open Api 3.0 +规范的json文档
gradle smartDocOpenApi
//生成rest接口文档并推送到Torna平台
gradle tornaRest

// Apache Dubbo Rpc生成
// Generate html
gradle smartDocRpcHtml
// Generate markdown
gradle smartDocRpcMarkdown
// Generate adoc
gradle smartDocRpcAdoc
```
#### Use IDEA
当你使用Idea时，可以通过maven Helper插件选择生成何种文档。

![idea中smart-doc-gradle插件使用](https://gitee.com/smart-doc-team/smart-doc-gradle-plugin/raw/master/images/idea.png "usage.png")

### Generated document example
[点击查看文档生成文档效果图](https://gitee.com/smart-doc-team/smart-doc/wikis/文档效果图?sort_id=1652819)

## Building
如果你需要自己构建，那可以使用下面命令，构建需要依赖Java 1.8。
```
// 将gradle插件暗转到本地
gradle publishToMavenLocal
// 将gradle插件发布到自己nexus仓库，自己修改build.gradle中的仓库地址配置
gradle publish
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
> 排名不分先后，更多接入公司，欢迎在[https://gitee.com/smart-doc-team/smart-doc/issues/I1594T](https://gitee.com/smart-doc-team/smart-doc/issues/I1594T)登记（仅供开源用户参考）

![iFLYTEK](https://gitee.com/smart-doc-team/smart-doc/raw/master/images/known-users/iflytek.png)
&nbsp;&nbsp;<img src="https://gitee.com/smart-doc-team/smart-doc/raw/master/images/known-users/oneplus.png" title="一加" width="83px" height="83px"/>
&nbsp;&nbsp;<img src="https://gitee.com/smart-doc-team/smart-doc/raw/master/images/known-users/xiaomi.png" title="小米" width="170px" height="83px"/>
<img src="https://gitee.com/smart-doc-team/smart-doc/raw/master/images/known-users/yuanmengjiankang.png" title="远盟健康" width="260px" height="83px"/>
<img src="https://gitee.com/smart-doc-team/smart-doc/raw/master/images/known-users/zhongkezhilian.png" title="中科智链" width="272px" height="83px"/>
<img src="https://gitee.com/smart-doc-team/smart-doc/raw/master/images/known-users/puqie_gaitubao_100x100.jpg" title="普切信息科技" width="83px" height="83px"/>&nbsp;&nbsp;
<img src="https://gitee.com/smart-doc-team/smart-doc/raw/master/images/known-users/tianbo-tech.png" title="杭州天铂云科" width="127px" height="70px"/>


## Contact
愿意参与构建smart-doc或者是需要交流问题可以加入qq群：

<img src="https://gitee.com/smart-doc-team/smart-doc/raw/master/images/smart-doc-qq.png" title="qq群" width="200px" height="200px"/>
