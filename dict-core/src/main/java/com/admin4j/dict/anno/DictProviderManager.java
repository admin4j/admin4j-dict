package com.admin4j.dict.anno;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

/**
 * 字典方法实现管理
 *
 * @author andanyang
 * @since 2022/7/20 14:07
 */
public interface DictProviderManager {

    /**
     * 根据dictCode获取字典显示值
     *
     * @param field    翻译字段
     * @param strategy 字典策略
     * @param dictType 字典分类
     * @return 获取字典显示值
     */
    String dictLabel(Field field, String strategy, String dictType, Object dictCode);

    /**
     * 批量获取
     */
    Map<Object, String> dictLabels(Field field, String strategy, String dictType, Collection<Object> dictCodes);

    /**
     * 根据dictLabel获取字典Code
     *
     * @param field
     * @param strategy 字典策略
     * @param dictType 字典分类
     * @return 获取字典显示值
     */
    Object dictCode(Field field, String strategy, String dictType, String dictLabel);
}
