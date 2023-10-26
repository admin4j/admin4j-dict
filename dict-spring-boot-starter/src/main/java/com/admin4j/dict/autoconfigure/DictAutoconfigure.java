package com.admin4j.dict.autoconfigure;

import com.admin4j.dict.anno.impl.CachedDictProviderManager;
import org.springframework.context.annotation.Bean;

/**
 * @author andanyang
 * @since 2022/10/25 16:55
 */
public class DictAutoconfigure {

    @Bean
    public CachedDictProviderManager cachedDictProviderManager() {
        return new CachedDictProviderManager();
    }
}
