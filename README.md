# admin4j-dict 字典转化

在实际开发中，列表查询比较烦的是需要查询他的关联字段的名称，比如客户列表查询，需要显示可的所属用户的用户名，而我们再表中已绑定了用户ID。

- 原始json

```json
[
  {
    "consumerId": 1,
    "consumerName": "刘备",
    "userId": "100",
    "createBy": 101,
    "createByName": 102,
    "updateBy": 103
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
    "userName": "刘邦",
    "createBy": 101,
    "updateBy": 103
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

```
@Data
public class Consumer {

    private Integer consumerId;

    private String consumerName;
    /**
     * 归属于哪个用户
     */
    @Dict(dictStrategy = SqlDictProvider.DICT_STRATEGY, dictType = "user")
    @TableDict(labelField = "user_name", codeField = "user_id", whereSql = "del_flag = 0")
    @DictJson
    @ExcelProperty(converter = EnhanceConverter.class)
    private Integer userId;
    /**
     * 由哪个用户创建
     */
    @Dict(dictStrategy = SqlDictProvider.DICT_STRATEGY, dictType = "user")
    @TableDict(labelField = "user_name", codeField = "user_id", whereSql = "del_flag = 0")
    @DictJson(fieldName = "createByName")
    @ExcelProperty(converter = EnhanceConverter.class)
    private Integer createBy;
    /**
     * 由哪个用户更新
     */
    @Dict(dictStrategy = SqlDictProvider.DICT_STRATEGY, dictType = "user")
    @TableDict(labelField = "user_name", codeField = "user_id", whereSql = "del_flag = 0")
    @DictJson
    @ExcelProperty(converter = EnhanceConverter.class)
    private Integer updateBy;
}
```

