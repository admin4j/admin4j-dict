package com.admin4j.dict.provider.sql.impl;

import com.admin4j.dict.provider.sql.SqlDictManager;
import com.admin4j.dict.provider.sql.SqlDictProperties;
import com.admin4j.dict.provider.sql.SqlDictService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Map;

/**
 * 使用 yml/Properties 的方式来配置sql 注解
 *
 * @author andanyang
 * @since 2023/10/30 15:38
 */
@RequiredArgsConstructor
public class PropertiesSqlDictService implements SqlDictService {


    private final SqlDictProperties sqlDictProperties;

    private final SqlDictManager sqlDictManager;

    /**
     * 通过 字典code 获取字典显示值
     *
     * @param tableType 表类型，或者表的 代码，由实现类转化
     * @param dictCode
     * @return
     */
    @Override
    public String dictLabel(String tableType, Object dictCode) {
        SqlDictProperties.SqlDict sqlDict = sqlDictProperties.getSqlDictMap().get(tableType);
        Assert.notNull(sqlDict, "sqlDictProperties cannot null");
        return sqlDictManager.dictLabel(tableType, dictCode, sqlDict.getCodeFiled(), sqlDict.getLabelFiled(), sqlDict.getWhereSql());
    }

    /**
     * 通过 字典code数组 批量的获取字典显示值
     *
     * @param dictType  表类型，或者表的 代码，由实现类转化
     * @param dictCodes
     * @return
     */
    @Override
    public Map<Object, String> dictLabels(String dictType, Collection<Object> dictCodes) {
        SqlDictProperties.SqlDict sqlDict = sqlDictProperties.getSqlDictMap().get(dictType);
        Assert.notNull(sqlDict, "sqlDictProperties cannot null");
        return sqlDictManager.dictLabels(dictType, dictCodes, sqlDict.getCodeFiled(), sqlDict.getLabelFiled(), sqlDict.getWhereSql());
    }

    /**
     * 通过字典显示label 获取字典值值
     *
     * @param dictType
     * @param dictLabel
     * @return
     */
    @Override
    public Object dictCode(String dictType, String dictLabel) {
        SqlDictProperties.SqlDict sqlDict = sqlDictProperties.getSqlDictMap().get(dictType);
        Assert.notNull(sqlDict, "sqlDictProperties cannot null");
        return sqlDictManager.dictCode(dictType, dictLabel, sqlDict.getCodeFiled(), sqlDict.getLabelFiled(), sqlDict.getWhereSql());
    }
}
