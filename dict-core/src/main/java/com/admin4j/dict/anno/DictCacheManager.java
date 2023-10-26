package com.admin4j.dict.anno;

import org.apache.commons.lang3.ObjectUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 字典缓存管理
 *
 * @author andanyang
 * @since 2022/7/24 13:23
 */
public interface DictCacheManager {


    /**
     * 开始缓存。对列表的dict 注解数据进行缓存
     *
     * @param data
     * @throws IllegalAccessException
     */
    default void startCache(List<?> data) throws IllegalAccessException {
        if (ObjectUtils.isEmpty(data)) {
            return;
        }
        Object o = data.get(0);
        startCache(data, o.getClass());
    }

    /**
     * 开始缓存。对列表的dict 注解数据进行缓存
     * 请保证 data 中的元素类型 和 TClass 一致
     */
    void startCache(List<?> data, Class<?> tClass) throws IllegalAccessException;

    /**
     * 根据字段开始翻译开始翻译数据
     */
    <T> void startCache(List<T> data, Field field) throws IllegalAccessException;

    /**
     * 清理缓存
     */
    void clearCache();

}
