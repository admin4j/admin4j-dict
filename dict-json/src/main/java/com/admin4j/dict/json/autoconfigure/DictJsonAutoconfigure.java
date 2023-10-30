package com.admin4j.dict.json.autoconfigure;

import com.admin4j.dict.core.DictCacheManager;
import com.admin4j.dict.json.mvc.DictWebConfig;
import org.springframework.context.annotation.Bean;

/**
 * @author andanyang
 * @since 2022/10/25 16:55
 */
public class DictJsonAutoconfigure {

    @Bean
    public DictWebConfig dictWebConfig(DictCacheManager dictCacheManager) {
        return new DictWebConfig(dictCacheManager);
    }
}
