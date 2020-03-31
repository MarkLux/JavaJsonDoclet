# Json Doclet

> 一个简洁的Java代码结构解析器

## 使用说明

基于[Doclet API](https://docs.oracle.com/javase/7/docs/technotes/guides/javadoc/doclet/overview.html)实现，可以扫描源码文件生成json格式的类及服务描述，可用于文档生成和代码提示等场景。

使用时将需要解析的类用`@Model`注解，将需要解析的接口用`@Service`注解，注意填写`namespace`以实现命名隔离。

解析时会自动递归所有已声明模型中引用的类及接口中引用的类。

## 通过javadoc命令调用

```
javadoc -doclet com.marklux.doclet.JsonDoclet -sourcepath xxx -subpackages com.xxx -encoding UTF-8 -outputfile schema.json
```

## 通过maven插件集成(推荐)

```xml

```

## JSON格式示范

```json
{
  "models": [
    {
      "containGenericType": false,
      "description": "用户模型",
      "hasParent": true,
      "packageName": "com.marklux.doclet.demo",
      "parentType": "java.lang.Object",
      "properties": [
        {
          "collection": false,
          "comment": "姓名",
          "containGenericType": false,
          "map": false,
          "name": "name",
          "primitive": true,
          "type": "java.lang.String"
        },
        {
          "collection": false,
          "comment": "年龄",
          "containGenericType": false,
          "map": false,
          "name": "age",
          "primitive": true,
          "type": "java.lang.Integer"
        },
        {
          "collection": false,
          "comment": "性别，male: 男；female: 女",
          "containGenericType": false,
          "map": false,
          "name": "sex",
          "primitive": true,
          "type": "java.lang.String"
        },
        {
          "collection": false,
          "comment": "手机号码",
          "containGenericType": false,
          "map": false,
          "name": "mobile",
          "primitive": true,
          "type": "java.lang.String"
        },
        {
          "collection": true,
          "comment": "图片列表",
          "containGenericType": true,
          "map": false,
          "name": "photos",
          "primitive": false,
          "type": "java.util.List",
          "valueType": "java.lang.String"
        }
      ],
      "type": "com.marklux.doclet.demo.User"
    },
    {
      "containGenericType": false,
      "description": "通用数据包装",
      "hasParent": true,
      "packageName": "com.marklux.doclet.demo",
      "parentType": "java.lang.Object",
      "properties": [
        {
          "collection": false,
          "comment": "数据",
          "containGenericType": false,
          "map": false,
          "name": "data",
          "primitive": true,
          "type": "java.lang.Object"
        },
        {
          "collection": false,
          "comment": "是否成功",
          "containGenericType": false,
          "map": false,
          "name": "success",
          "primitive": true,
          "type": "boolean"
        }
      ],
      "type": "com.marklux.doclet.demo.DTO"
    }
  ],
  "services": [
    {
      "alias": "helloService",
      "description": "测试服务",
      "methods": [
        {
          "comment": "说你好",
          "methodName": "sayHello",
          "params": [
            {
              "collection": false,
              "containGenericType": false,
              "map": false,
              "name": "user",
              "primitive": false,
              "type": "com.marklux.doclet.demo.User"
            }
          ],
          "paramsComment": {
            "user": "用户模型"
          },
          "resultComment": "你好文本",
          "resultType": {
            "collection": false,
            "containGenericType": false,
            "map": false,
            "primitive": true,
            "type": "java.lang.String"
          }
        },
        {
          "comment": "打招呼",
          "methodName": "greeting",
          "params": [
            {
              "collection": false,
              "containGenericType": false,
              "map": false,
              "name": "s",
              "primitive": true,
              "type": "java.lang.String"
            }
          ],
          "paramsComment": {
            "s": "名字"
          },
          "resultComment": "招呼文本",
          "resultType": {
            "collection": false,
            "containGenericType": false,
            "map": false,
            "primitive": false,
            "type": "com.marklux.doclet.demo.DTO"
          }
        }
      ],
      "serviceInterface": "com.marklux.doclet.demo.HelloService",
      "tags": []
    }
  ]
}
```