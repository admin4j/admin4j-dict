package com.admin4j.dict.provider.sql;

import java.util.Collection;
import java.util.Map;

/**
 * 基于sql的字典
 *
 * @author andanyang
 * @since 2022/7/24 13:35
 */
public interface SqlDictManager {

    /**
     * 获取字典显示值
     *
     * @param dictType
     * @param code
     * @param codeFiled
     * @param labelFiled
     * @param whereSql
     * @return
     */
    String dictLabel(String dictType, Object code, String codeFiled, String labelFiled, String whereSql);

    /**
     * 批量获取使用 in 查询
     */
    Map<Object, String> dictLabels(String dictType, Collection<Object> dictCodes, String codeFiled, String labelFiled, String whereSql);

    /**
     * 根据label 获取字典code
     *
     * @param dictType
     * @param dictLabel
     * @return
     */

    Object dictCode(String dictType, String dictLabel, String codeFiled, String labelFiled, String whereSql);

}
