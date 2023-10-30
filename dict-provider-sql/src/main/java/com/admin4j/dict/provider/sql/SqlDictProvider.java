package com.admin4j.dict.provider.sql;


import com.admin4j.dict.anno.DictSql;
import com.admin4j.dict.core.DictProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

/**
 * 数据库表字典
 *
 * @author andanyang
 * @since 2022/7/20 14:18
 */


public class SqlDictProvider implements DictProvider {

    public static final String DICT_STRATEGY = "sql";
    private final SqlDictManager sqlDictManager;
    private final SqlDictService sqlDictService;

    public SqlDictProvider(SqlDictManager sqlDictManager, @Autowired(required = false) SqlDictService sqlDictService) {
        this.sqlDictManager = sqlDictManager;
        this.sqlDictService = sqlDictService;
    }

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

        DictSql dictSql = field.getAnnotation(DictSql.class);
        if (dictSql == null && sqlDictService != null) {
            return sqlDictService.dictLabel(dictType, dictCode);
        } else {
            Assert.notNull(dictSql, "table cannot be null");
            return sqlDictManager.dictLabel(dictType, dictCode, dictSql.codeField(), dictSql.labelField(), dictSql.whereSql());
        }
    }

    /**
     * 批量获取
     */
    @Override
    public Map<Object, String> dictLabels(Field field, String dictType, Collection<Object> dictCodes) {

        DictSql dictSql = field.getAnnotation(DictSql.class);
        if (dictSql == null && sqlDictService != null) {
            return sqlDictService.dictLabels(dictType, dictCodes);
        }
        Assert.notNull(dictSql, "table cannot be null");
        return sqlDictManager.dictLabels(dictType, dictCodes, dictSql.codeField(), dictSql.labelField(), dictSql.whereSql());
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


        DictSql dictSql = field.getAnnotation(DictSql.class);
        if (dictSql == null && sqlDictService != null) {
            return sqlDictService.dictCode(dictType, dictLabel);
        }
        Assert.notNull(dictSql, "table cannot be null");
        return sqlDictManager.dictCode(dictType, dictLabel, dictSql.codeField(), dictSql.labelField(), dictSql.whereSql());
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
