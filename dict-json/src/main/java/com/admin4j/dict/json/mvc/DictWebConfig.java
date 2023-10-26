package com.admin4j.dict.json.mvc;


import com.admin4j.dict.anno.DictCacheManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author andanyang
 * @since 2022/8/30 9:30
 */
@RequiredArgsConstructor
public class DictWebConfig implements WebMvcConfigurer {

    private final DictCacheManager dictCacheManager;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DictInterceptor(dictCacheManager));
    }


}
