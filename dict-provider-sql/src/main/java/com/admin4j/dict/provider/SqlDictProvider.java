package com.admin4j.dict.provider;


import com.admin4j.dict.anno.DictProvider;
import com.admin4j.dict.anno.TableDict;
import com.admin4j.dict.provider.sql.SqlDictManager;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

/**
 * 数据库表字典
 *
 * @author andanyang
 * @since 2022/7/20 14:18
 */

@RequiredArgsConstructor
public class SqlDictProvider implements DictProvider {

    public static final String DICT_STRATEGY = "sql";
    private final SqlDictManager sqlDictManager;

    /**
     * 根据dictCode获取字典显示值
     *
     * @param field
     * @param dictType 字典分类
     * @param dictCode 字典code
     * @return 获取字典显示值
     */
    @Override
    public String dictLabel(Field field, String dictType, Object dictCode) {

        TableDict tableDict = field.getAnnotation(TableDict.class);

        return sqlDictManager.dictLabel(dictType, dictCode, tableDict.codeField(), tableDict.labelField(), tableDict.whereSql());
    }

    /**
     * 批量获取
     */
    @Override
    public Map<Object, String> dictLabels(Field field, String dictType, Collection<Object> dictCodes) {

        TableDict tableDict = field.getAnnotation(TableDict.class);

        return sqlDictManager.dictLabels(dictType, dictCodes, tableDict.codeField(), tableDict.labelField(), tableDict.whereSql());
    }

    /**
     * 根据dictLabel获取字典Code
     *
     * @param field
     * @param dictType  字典分类
     * @param dictLabel 字典label
     * @return 获取字典显示值
     */
    @Override
    public Object dictCode(Field field, String dictType, String dictLabel) {


        TableDict tableDict = field.getAnnotation(TableDict.class);

        return sqlDictManager.dictCode(dictType, dictLabel, tableDict.codeField(), tableDict.labelField(), tableDict.whereSql());
    }

    /**
     * 支持什么类型
     *
     * @return
     */
    @Override
    public String support() {
        return DICT_STRATEGY;
    }
}
