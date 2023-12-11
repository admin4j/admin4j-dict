package com.admin4j.dict.core.impl;


import com.admin4j.dict.core.DictProvider;
import com.admin4j.dict.core.DictProviderManager;
import com.admin4j.spring.plugin.provider.manager.ProviderManager;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * @author andanyang
 * @since 2022/7/20 14:15
 */
public class DefaultDictProviderManager implements DictProviderManager {

    /**
     * 根据dictCode获取字典显示值
     *
     * @param field    字段
     * @param strategy 字典策略
     * @param dictType 字典分类
     * @param dictCode 字典code
     * @return 获取字典显示值
     */
    @Override
    public String dictLabel(Field field, String strategy, String dictType, Object dictCode) {
        DictProvider load = ProviderManager.load(DictProvider.class, strategy);
        return Objects.requireNonNull(load).dictLabel(field, dictType, dictCode);
    }

    /**
     * 批量获取
     */
    @Override
    public Map<Object, String> dictLabels(Field field, String strategy, String dictType, Collection<Object> dictCodes) {
        if (dictCodes == null || dictCodes.isEmpty()) {
            return Collections.emptyMap();
        }
        return Objects.requireNonNull(ProviderManager.load(DictProvider.class, strategy)).dictLabels(field, dictType, dictCodes);
    }

    /**
     * 根据dictLabel获取字典Code
     *
     * @param field
     * @param strategy  字典策略
     * @param dictType  字典分类
     * @param dictLabel
     * @return 获取字典显示值
     */
    @Override
    public Object dictCode(Field field, String strategy, String dictType, String dictLabel) {
        return Objects.requireNonNull(ProviderManager.load(DictProvider.class, strategy)).dictCode(field, dictType, dictLabel);
    }
}
