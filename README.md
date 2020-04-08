<h1 align="center">Smart-Doc Gradle Plugin</a></h1>

[中文文档](https://github.com/shalousun/smart-doc-maven-plugin/blob/master/README_CN.md)

## Introduce
smart-doc-gradle-plugin is a `gradle` plugin developed by the smart-doc official team. 
This plugin is available from smart-doc 1.8.1.
Using smart-doc-gradle-plugin makes it easier to integrate smart-doc into your project, and integration is more lightweight. 
You no longer need to write unit tests in your project to
Start smart-doc to scan source code analysis and generate API documents. 
You can run the `gradle` command directly or click on the preset` goal` of the smart-doc-maven-plugin in the IDE to generate API documentation. 
smart-doc-gradle-plugin will also make smart-doc's ability to generate API documentation more powerful.
[About smart-doc](https://github.com/shalousun/smart-doc)

## Getting started
### Add plugin
```
buildscript {
    dependencies {
        classpath 'com.github.shalousun:smart-doc-gradle-plugin:lastest'
    }
}
apply plugin: 'smart-doc'
```
### Plugin options

| Option | Default value | Description |
| ------ | ------------- | ----------- |
|configFile|src/main/resources/default.json||

Example setting of options:
```
smartdoc {
    configFile = file("src/main/resources/default.json")
}
```
### Create a json config 
Create a json configuration file in your project. The smart-doc-gradle-plugin plugin will use this configuration information.
For example, create `/src/main/resources/smart-doc.json` in the project. 
The configuration contents are as follows.

**Minimize configuration:**
```
{
   "allInOne": true, // whether to merge documents into one file, generally recommended as true
   "isStrict": false,//If the strict mode is set to true, Smart-doc forces that the public method in each interface in the code has a comment.
   "outPath": "/src/main/resources" //Set the api document output path.
}
```
Only three configuration items are required to use the smart-doc-gradle-plugin to generate API documentation. In fact, only outPath must be configured.

**Detailed configuration content:**

When you need to use smart-doc to generate more API document information, you can add detailed configuration content.
```
{
  "serverUrl": "http://127.0.0.1", // Set the server address, not required
  "isStrict": false, // whether to enable strict mode
  "allInOne": true, // whether to merge documents into one file, generally recommended as true
  "outPath": "D: // md2", // Specify the output path of the document
  "coverOld": true, // Whether to overwrite old files, mainly used for mardown file overwrite
  "packageFilters": "", // controller package filtering, multiple package names separated by commas
  "md5EncryptedHtmlName": false, // only used if each controller generates an html file
  "projectName": "smart-doc", // Configure your own project name
  "skipTransientField": true, // Not currently implemented
  "dataDictionaries": [// Configure the data dictionary, no need to set
    {
      "title": "Order Status", // The name of the data dictionary
      "enumClassName": "com.power.doc.enums.OrderEnum", // Data dictionary enumeration class name
      "codeField": "code", // The field name corresponding to the data dictionary dictionary code
      "descField": "desc" // Data dictionary object description information dictionary
    }
  ],

  "errorCodeDictionaries": [{// error code list, no need to set
    "title": "title",
    "enumClassName": "com.power.doc.enums.ErrorCodeEnum", // Error code enumeration class
    "codeField": "code", // Code field name of the error code
    "descField": "desc" // Field name corresponding to the error code description
  }],

  "revisionLogs": [// Set document change records, no need to set
    {
      "version": "1.0", // Document version number
      "status": "update", // Change operation status, generally: create, update, etc.
      "author": "author", // Document change author
      "remarks": "desc" // Change description
    }
  ],
  "customResponseFields": [// Customly add fields and comments. If api-doc encounters a field with the same name later, directly add a comment to the corresponding field. It is not necessary.
    {
      "name": "code", // Override the response code field
      "desc": "Response code", // Override field comment of response code
      "value": "00000" // Set the value of the response code
    }
  ],
  "requestHeaders": [// Set global request headers, no need to set
    {
      "name": "token",
      "type": "string",
      "desc": "desc",
      "required": false,
      "since": "-"
    }
  ]
}
```
**Note:** The above json configuration is completely converted into json using the smart-doc's ApiConfig. 
So the project configuration can also refer to the introduction of smart-doc.
### Generated document
#### Use Gradle command
```
// Generate html
gradle smartDocRestHtml
// Generate markdown
gradle smartDocRestMarkdown
// Generate adoc
gradle smartDocRestAdoc
// Generate postman collection
gradle smartDocPostman
```
#### Use IntelliJ IDEA
On Use IntelliJ IDE, if you have added smart-doc-gradle-plugin to the project, 
you can directly find the plugin smart-doc plugin and click to generate API documentation.

![smart-doc-gradle-plugin](https://raw.githubusercontent.com/shalousun/smart-doc-maven-plugin/master/images/idea.png)

### Generated document example
#### Interface header rendering
![header](https://images.gitee.com/uploads/images/2019/1231/223538_be45f8a9_144669.png "header.png")
#### Request parameter example rendering
![request-params](https://images.gitee.com/uploads/images/2019/1231/223710_88933f55_144669.png "request.png")
#### Response parameter example renderings
![response-fields](https://images.gitee.com/uploads/images/2019/1231/223817_32bea6dc_144669.png "response.png")
## Building
you can build with the following commands. (Java 1.8 is required to build the master branch)
```
mvn clean install -Dmaven.test.skip=true
```
## Other reference
- [Smart-doc manual](https://github.com/shalousun/smart-doc/wiki)

## Who is using
These are only part of the companies using smart-doc, for reference only. If you are using smart-doc, please [add your company here](https://github.com/shalousun/smart-doc/issues/12) to tell us your scenario to make smart-doc better.

![iFLYTEK](https://raw.githubusercontent.com/shalousun/smart-doc/dev/images/known-users/iflytek.png)
&nbsp;&nbsp;<img src="https://raw.githubusercontent.com/shalousun/smart-doc/dev/images/known-users/oneplus.png" title="OnePlus" width="83px" height="83px"/>
&nbsp;&nbsp;<img src="https://raw.githubusercontent.com/shalousun/smart-doc/dev/images/known-users/xiaomi.png" title="Xiaomi" width="170px" height="83px"/>
<img src="https://raw.githubusercontent.com/shalousun/smart-doc/dev/images/known-users/yuanmengjiankang.png" title="yuanmengjiankang" width="260px" height="83px"/>
<img src="https://raw.githubusercontent.com/shalousun/smart-doc/dev/images/known-users/zhongkezhilian.png" title="zhongkezhilian" width="272px" height="83px"/>
<img src="https://raw.githubusercontent.com/shalousun/smart-doc/dev/images/known-users/puqie_gaitubao_100x100.jpg" title="puqie" width="83px" height="83px"/>
## License
smart-doc-gradle-plugin is under the Apache 2.0 license.  See the [LICENSE](https://raw.githubusercontent.com/shalousun/smart-doc-maven-plugin/master/LICENSE) file for details.
## Contact
Email： 836575280@qq.com
