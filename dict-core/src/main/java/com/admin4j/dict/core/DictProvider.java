package com.admin4j.dict.core;

import com.admin4j.spring.plugin.provider.StringProvider;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 字典方法实现
 *
 * @author andanyang
 * @since 2022/7/20 14:07
 */
public interface DictProvider extends StringProvider {

    /**
     * 根据dictCode获取字典显示值
     *
     * @param strategy 字典策略
     * @param field
     * @param dictType 字典分类
     * @return 获取字典显示值
     */
    String dictLabel(Field field, String dictType, Object dictCode);

    /**
     * 批量获取
     *
     * @return key 为dictCode，value 为 dictLabel
     */
    default Map<Object, String> dictLabels(Field field, String dictType, Collection<Object> dictCodes) {

        Map<Object, String> dictLabels = new HashMap<>(dictCodes.size());
        for (Object dictCode : dictCodes) {
            dictLabels.computeIfAbsent(dictCode, key -> dictLabel(field, dictType, dictCode));
        }
        return dictLabels;
    }

    /**
     * 根据dictLabel获取字典Code
     *
     * @param field
     * @param dictType  字典策略
     * @param dictLabel 字典分类
     * @return 获取字典显示值
     */
    Object dictCode(Field field, String dictType, String dictLabel);


}
