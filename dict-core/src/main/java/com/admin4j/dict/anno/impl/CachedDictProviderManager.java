package com.admin4j.dict.anno.impl;

import com.admin4j.dict.anno.Dict;
import com.admin4j.dict.anno.DictCacheManager;
import com.admin4j.dict.anno.DictList;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author andanyang
 * @since 2022/7/24 13:13
 */

public class CachedDictProviderManager extends DefaultDictProviderManager implements DictCacheManager {

    /**
     * 字典key/value缓存
     */
    private static final ThreadLocal<Map<Field, Map<Object, String>>> THREAD_LOCAL_CACHE = new ThreadLocal<>();
    /**
     * 读缓存
     */
    private static final ThreadLocal<Map<Field, Map<Object, Object>>> THREAD_LOCAL_READ_CACHE = new ThreadLocal<>();
    /**
     * 缓存 字典 字段
     * key model 对象
     * value 该model的所有 带有 @Dict 的字段
     */
    private static final Map<Class<?>, List<Field>> DICT_FIELDS = new ConcurrentHashMap<>(64);
    /**
     * 批量读取数
     */
    private static final int BATCH_NUM = 500;

    /**
     * 缓存结果值
     *
     * @return
     */
    @Override
    public Object dictCode(Field field, String strategy, String dictType, String dictLabel) {

        Map<Field, Map<Object, Object>> fieldMapMap = THREAD_LOCAL_READ_CACHE.get();
        if (fieldMapMap == null) {
            fieldMapMap = new HashMap<>(32);
            THREAD_LOCAL_READ_CACHE.set(fieldMapMap);
        }
        Map<Object, Object> codeMap = fieldMapMap.computeIfAbsent(field, k -> new HashMap<>(32));

        return codeMap.computeIfAbsent(dictLabel, key -> super.dictCode(field, strategy, dictType, dictLabel));
    }

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
        Map<Field, Map<Object, String>> fieldMapMap = THREAD_LOCAL_CACHE.get();
        if (fieldMapMap == null) {
            fieldMapMap = new HashMap<>(32);
            THREAD_LOCAL_CACHE.set(fieldMapMap);
        }

        Map<Object, String> labelMap = fieldMapMap.computeIfAbsent(field, k -> new HashMap<>(32));
        return labelMap.computeIfAbsent(dictCode, key -> super.dictLabel(field, strategy, dictType, dictCode));
    }

    /**
     * 开始翻译数据
     */
    @Override
    public void startCache(List<?> data, Class<?> tClass) throws IllegalAccessException {

        List<Field> fields = dictFields(tClass);
        for (Field field : fields) {

            startCache(data, field);
        }
    }

    /**
     * 缓存/获取该Class所有的带有 @Dict 注解的字段
     *
     * @param tClass
     * @param <T>
     * @return
     */
    private <T> List<Field> dictFields(Class<T> tClass) {

        return DICT_FIELDS.computeIfAbsent(tClass, key -> {

            Field[] fields = tClass.getDeclaredFields();
            List<Field> fs = new ArrayList<>();
            for (Field field : fields) {
                boolean annotationPresent = field.isAnnotationPresent(Dict.class);
                if (annotationPresent) {
                    fs.add(field);
                }
            }

            // 查找父类
            Class<? super T> superclass = tClass.getSuperclass();
            while (superclass != null && superclass != Object.class) {
                fields = superclass.getDeclaredFields();
                for (Field field : fields) {
                    boolean annotationPresent = field.isAnnotationPresent(Dict.class);
                    if (annotationPresent) {
                        fs.add(field);
                    }
                }

                superclass = superclass.getSuperclass();
            }
            return fs;
        });
    }

    /**
     * 开始翻译数据
     */
    @Override
    public <T> void startCache(List<T> data, Field field) throws IllegalAccessException {

        if (ObjectUtils.isEmpty(data)) {
            return;
        }
        Map<Field, Map<Object, String>> dictCache = THREAD_LOCAL_CACHE.get();
        if (dictCache == null) {
            dictCache = new HashMap<>();
        }

        Dict dict = field.getAnnotation(Dict.class);

        field.setAccessible(true);
        // 提取key数据
        Collection<Object> codes = new HashSet<>(data.size());
        // 所有的结果缓存
        Map<Object, String> dictFieldCacheAll = null;
        for (T row : data) {

            Object code = field.get(row);
            if (code instanceof Collection) {
                codes.addAll((Collection<?>) code);
            } else {
                codes.add(code);
            }
            if (codes.size() >= BATCH_NUM) {
                Map<Object, String> dictFieldCache = super.dictLabels(field, dict.dictStrategy(), dict.dictType(), codes);
                if (dictFieldCacheAll == null) {
                    dictFieldCacheAll = new HashMap<>(dictFieldCache.size());
                    dictFieldCacheAll.putAll(dictFieldCache);
                }
                codes.clear();
            }
        }

        if (dictFieldCacheAll == null) {
            dictFieldCacheAll = super.dictLabels(field, dict.dictStrategy(), dict.dictType(), codes);
        } else {
            Map<Object, String> dictFieldCache = super.dictLabels(field, dict.dictStrategy(), dict.dictType(), codes);
            dictFieldCacheAll.putAll(dictFieldCache);
        }
        dictCache.put(field, dictFieldCacheAll);
        THREAD_LOCAL_CACHE.set(dictCache);
    }

    public String dictLabel(Field field, Object code) {
        if (THREAD_LOCAL_CACHE.get().get(field) == null) {
            return null;
        }

        if (code instanceof Collection) {
            List<String> strings = dictLabels(field, (Collection) code);
            DictList dictList = field.getAnnotation(DictList.class);
            return StringUtils.join(strings, dictList == null ? "," : dictList.separator());
        }
        return THREAD_LOCAL_CACHE.get().get(field).get(code);
    }

    public List<String> dictLabels(Field field, Collection<Object> codes) {
        if (THREAD_LOCAL_CACHE.get().get(field) == null) {
            return Collections.emptyList();
        }

        DictList dictList = field.getAnnotation(DictList.class);
        boolean unique = dictList == null || dictList.unique();
        boolean filterBlank = dictList == null || dictList.filterBlank();
        // List<String> labels = dictList == null || dictList.unique() ? new ArrayList<>(codes.size()) : new LinkedHashSet<>(codes.size());
        List<String> labels = new ArrayList<>(codes.size());
        for (Object code : codes) {

            String label = THREAD_LOCAL_CACHE.get().get(field).get(code);
            if (filterBlank) {
                if (StringUtils.isBlank(label)) {
                    continue;
                }
            }
            if (unique) {
                if (!labels.contains(label)) {
                    labels.add(label);
                }
            } else {
                labels.add(label);
            }
        }
        return labels;
    }

    @Override
    public void clearCache() {
        THREAD_LOCAL_CACHE.remove();
        THREAD_LOCAL_READ_CACHE.remove();
    }
}
