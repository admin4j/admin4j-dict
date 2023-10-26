package com.admin4j.dict.excel.autoconfigure;

import com.admin4j.dict.anno.DictCacheManager;
import com.admin4j.dict.anno.impl.CachedDictProviderManager;
import com.admin4j.dict.excel.DictExcelReadListener;
import com.admin4j.dict.excel.enhance.DictEnhanceService;
import com.admin4j.dict.excel.enhance.SensitivityEnhanceService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

/**
 * @author andanyang
 * @since 2022/10/25 16:55
 */
public class DictExcelAutoconfigure {

    @Bean
    @ConditionalOnBean(CachedDictProviderManager.class)
    DictExcelReadListener dictExcelReadListener(CachedDictProviderManager cachedDictProviderManager) {
        return new DictExcelReadListener(cachedDictProviderManager);
    }

    @Bean
    public DictEnhanceService dictEnhanceService() {
        return new DictEnhanceService();
    }

    @Bean
    @ConditionalOnClass(name = "com.admin4j.framework.desensitize.core.Sensitivity")
    public SensitivityEnhanceService sensitivityEnhanceService() {
        return new SensitivityEnhanceService();
    }

    @Bean
    @ConditionalOnClass(name = "com.admin4j.framework.excel.ExcelWriteLifecycle")
    @ConditionalOnBean(DictCacheManager.class)
    public ExcelConfiguration excelConfiguration(DictCacheManager dictCacheManager) {
        return new ExcelConfiguration(dictCacheManager);
    }
}
