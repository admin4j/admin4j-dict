package com.admin4j.dict.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author andanyang
 * @since 2022/8/30 10:19
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DictCache {

    Class<?> value() default DictCache.class;
}
