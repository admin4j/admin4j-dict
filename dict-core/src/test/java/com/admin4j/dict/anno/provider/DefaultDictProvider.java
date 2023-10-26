package com.admin4j.dict.anno.provider;

import com.admin4j.dict.anno.DictProvider;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @author andanyang
 * @since 2022/10/25 16:32
 */
@Component
public class DefaultDictProvider implements DictProvider {

    /**
     * 根据dictCode获取字典显示值
     *
     * @param field
     * @param dictType 字典分类
     * @param dictCode
     * @return 获取字典显示值
     */
    @Override
    public String dictLabel(Field field, String dictType, Object dictCode) {
        return null;
    }

    /**
     * 根据dictLabel获取字典Code
     *
     * @param field
     * @param dictType  字典策略
     * @param dictLabel 字典分类
     * @return 获取字典显示值
     */
    @Override
    public Object dictCode(Field field, String dictType, String dictLabel) {
        return null;
    }

    @Override
    public String support() {
        return null;
    }
}
