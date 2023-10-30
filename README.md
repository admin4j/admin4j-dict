# admin4j-dict 字典转化

在实际开发中，列表查询比较烦的是需要查询他的关联字段的名称，比如客户列表查询，需要显示可的所属用户的用户名，而我们再表中已绑定了用户ID。

- 原始json

```json
[
  {
    "consumerId": 1,
    "consumerName": "刘备",
    "userId": "100"
  }
]
```

- 处理之后的json

```json
[
  {
    "consumerId": 1,
    "consumerName": "刘备",
    "userId": "100",
    "userName": "刘邦"
  }
]
```

解决方法如下：

1. 添加冗余用户名称字段
2. 使用join 查询
3. 先查列表后单独查名称，再进行赋值

第一种方案是最简单的，但他缺点是

1. 冗余字段要求不可变， 如果冗余字段原始值发生变化，那他需要一定的机制，去更新。如用户名变化，同时也要更新其他表的冗余字段
2. 多余需要显示字段较多的情况下，需要冗余多个字段。比如上方的 `userId`,`createBy`,`updateBy`的等

第二种方案缺点是

1. 性能不高。当表比较大，或者连表比较多是，表现出性能不佳。
2. 需要显示多个名称时。比如上方的 `userId`,`createBy`,`updateBy`的等，需要连多张表

第三种方案缺点是

1. 操作复杂

# admin4j-dict

通过使用注解的方式，实现第三种方式。避免效率冗余和join效率的各大问题

# Usage

导入 pom

```xml

<dependency>
    <groupId>com.admin4j.dict</groupId>
    <artifactId>dict-spring-boot-starter</artifactId>
    <version>0.8.1</version>
</dependency>
```

```
@Data
public class Consumer {

    private Integer consumerId;

    private String consumerName;
    /**
     * 归属于哪个用户
     */
    @Dict(dictStrategy = SqlDictProvider.DICT_STRATEGY, dictType = "user")
    @DictSql(labelField = "user_name", codeField = "user_id", whereSql = "del_flag = 0")
    @DictJson
    @ExcelProperty(converter = EnhanceConverter.class)
    private Integer userId;
    /**
     * 由哪个用户创建
     */
    @Dict(dictStrategy = SqlDictProvider.DICT_STRATEGY, dictType = "user")
    @DictSql(labelField = "user_name", codeField = "user_id", whereSql = "del_flag = 0")
    @DictJson(fieldName = "createByName")
    @ExcelProperty(converter = EnhanceConverter.class)
    private Integer createBy;
    /**
     * 由哪个用户更新
     */
    @Dict(dictStrategy = SqlDictProvider.DICT_STRATEGY, dictType = "user")
    @DictSql(labelField = "user_name", codeField = "user_id", whereSql = "del_flag = 0")
    @DictJson
    @ExcelProperty(converter = EnhanceConverter.class)
    private Integer updateBy;
}
```

具体示例 查看 [`dict-spring-boot-example`](./admin4j-dict/tree/master/dict-spring-boot-example)

## 配置方式一： 纯注解

```java
@Dict(dictStrategy = SqlDictProvider.DICT_STRATEGY, dictType = "user")
@DictSql(labelField = "user_name", codeField = "user_id", whereSql = "del_flag = 0")
@DictJson
private Integer userId;
```

### @Dict 定义字典策略

- dictStrategy 字典策略 当前为 sql
- dictType 字典类型 指 表名 或者表code

### @DictSql 定义sql 如何读取

- labelField 显示的字段名
- codeField 字典值字段名
- whereSql 查询条件

### @DictJson 定义Jackson 的 json 序列化

## 配置方式二： yaml 配置

`@DictSql` 配置有点繁琐和重复，使用 yaml 可以省略`@DictSql` 配置

```yaml
admin4j:
  dict:
    sql-dict-map:
      user:
        codeFiled: user_id
        labelFiled: user_name
        whereSql: del_flag = 0
```

## 配置方式三： 自定义配置

实现 `SqlDictService` 接口实现自定义的读取sql,参考 `PropertiesSqlDictService`

## 开始批量字典查询

默认情况下 字典查询都是按照查询出来的数组一个个查询，如下

```
c.a.d.e.mapper.ConsumerMapper.list       : ==>  Preparing: select * from consumer;
c.a.d.e.mapper.ConsumerMapper.list       : ==> Parameters: 
c.a.d.e.mapper.ConsumerMapper.list       : <==      Total: 3
c.a.d.p.s.i.m.SqlDictMapper.dictLabel    : ==>  Preparing: SELECT user_name as `label` from user where user_id = ? AND del_flag = 0 LIMIT 1
c.a.d.p.s.i.m.SqlDictMapper.dictLabel    : ==> Parameters: 100(Integer)
c.a.d.p.s.i.m.SqlDictMapper.dictLabel    : <==      Total: 1
c.a.d.p.s.i.m.SqlDictMapper.dictLabel    : ==>  Preparing: SELECT user_name as `label` from user where user_id = ? AND del_flag = 0 LIMIT 1
c.a.d.p.s.i.m.SqlDictMapper.dictLabel    : ==> Parameters: 101(Integer)
c.a.d.p.s.i.m.SqlDictMapper.dictLabel    : <==      Total: 1
c.a.d.p.s.i.m.SqlDictMapper.dictLabel    : ==>  Preparing: SELECT user_name as `label` from user where user_id = ? AND del_flag = 0 LIMIT 1
c.a.d.p.s.i.m.SqlDictMapper.dictLabel    : ==> Parameters: 102(Integer)
c.a.d.p.s.i.m.SqlDictMapper.dictLabel    : <==      Total: 1
c.a.d.p.s.i.m.SqlDictMapper.dictLabel    : ==>  Preparing: SELECT user_name as `label` from user where user_id = ? AND del_flag = 0 LIMIT 1
c.a.d.p.s.i.m.SqlDictMapper.dictLabel    : ==> Parameters: 101(Integer)
c.a.d.p.s.i.m.SqlDictMapper.dictLabel    : <==      Total: 1
c.a.d.p.s.i.m.SqlDictMapper.dictLabel    : ==>  Preparing: SELECT user_name as `label` from user where user_id = ? AND del_flag = 0 LIMIT 1
c.a.d.p.s.i.m.SqlDictMapper.dictLabel    : ==> Parameters: 100(Integer)
c.a.d.p.s.i.m.SqlDictMapper.dictLabel    : <==      Total: 1
c.a.d.p.s.i.m.SqlDictMapper.dictLabel    : ==>  Preparing: SELECT user_name as `label` from user where user_id = ? AND del_flag = 0 LIMIT 1
c.a.d.p.s.i.m.SqlDictMapper.dictLabel    : ==> Parameters: 102(Integer)
c.a.d.p.s.i.m.SqlDictMapper.dictLabel    : <==      Total: 1
c.a.d.p.s.i.m.SqlDictMapper.dictLabel    : ==>  Preparing: SELECT user_name as `label` from user where user_id = ? AND del_flag = 0 LIMIT 1
c.a.d.p.s.i.m.SqlDictMapper.dictLabel    : ==> Parameters: 100(Integer)
c.a.d.p.s.i.m.SqlDictMapper.dictLabel    : <==      Total: 1
```

### 参考 DictResponseBodyAdvice 开始缓存

开始缓存之后，会变得使用批量查询，提供查询效率

```
c.a.d.e.mapper.ConsumerMapper.list       : ==>  Preparing: select * from consumer;
c.a.d.e.mapper.ConsumerMapper.list       : ==> Parameters: 
c.a.d.e.mapper.ConsumerMapper.list       : <==      Total: 3
c.a.d.p.s.i.m.SqlDictMapper.dictLabels   : ==>  Preparing: select user_name as `label`, user_id as `code` from user where user_id in ( ? , ? , ? ) AND del_flag = 0
c.a.d.p.s.i.m.SqlDictMapper.dictLabels   : ==> Parameters: 100(Integer), 101(Integer), 102(Integer)
c.a.d.p.s.i.m.SqlDictMapper.dictLabels   : <==      Total: 3
```
