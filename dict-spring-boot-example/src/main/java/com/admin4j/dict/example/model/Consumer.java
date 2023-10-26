package com.admin4j.dict.example.model;

import com.admin4j.dict.anno.Dict;
import com.admin4j.dict.anno.TableDict;
import com.admin4j.dict.excel.EnhanceConverter;
import com.admin4j.dict.json.anno.DictJson;
import com.admin4j.dict.provider.SqlDictProvider;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author andanyang
 * @since 2022/10/26 9:36
 */
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
    // @JsonProperty("updateName")
    @DictJson
    @ExcelProperty(converter = EnhanceConverter.class)
    private Integer updateBy;
}
