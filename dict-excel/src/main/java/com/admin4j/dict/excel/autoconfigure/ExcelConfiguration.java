package com.admin4j.dict.excel.autoconfigure;


import com.admin4j.dict.core.DictCacheManager;
import com.admin4j.framework.excel.ExcelWriteLifecycle;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * @author andanyang
 * @since 2022/7/24 15:46
 */
@RequiredArgsConstructor
public class ExcelConfiguration {

    private final DictCacheManager dictCacheManager;

    @Bean
    ExcelWriteLifecycle writeLifecycle() {
        return new ExcelWriteLifecycle() {
            @Override
            public <T> void before(List<T> list, Class<T> aClass) {
                try {
                    dictCacheManager.startCache(list, aClass);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public <T> void after(List<T> list, Class<T> aClass) {
                dictCacheManager.clearCache();
            }
        };
    }
}
