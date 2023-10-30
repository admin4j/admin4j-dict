package com.admin4j.dict.provider.sql;

import java.util.Collection;
import java.util.Map;

/**
 * 自定义的sql字典实现
 *
 * @author andanyang
 * @since 2023/10/30 10:15
 */
public interface SqlDictService {
    /**
     * 通过 字典code 获取字典显示值
     *
     * @param tableType 表类型，或者表的 代码，由实现类转化
     * @param dictCode
     * @return
     */
    String dictLabel(String tableType, Object dictCode);

    /**
     * 通过 字典code数组 批量的获取字典显示值
     *
     * @param dictType  表类型，或者表的 代码，由实现类转化
     * @param dictCodes
     * @return
     */
    Map<Object, String> dictLabels(String dictType, Collection<Object> dictCodes);

    /**
     * 通过字典显示label 获取字典值值
     *
     * @param dictType
     * @param dictLabel
     * @return
     */
    Object dictCode(String dictType, String dictLabel);
}
