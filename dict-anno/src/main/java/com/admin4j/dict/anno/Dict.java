package com.admin4j.dict.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author andanyang
 * @since 2022/7/6 17:43
 */
@Retention(RetentionPolicy.RUNTIME)

public @interface Dict {

    /**
     * @return 字典策略
     */
    String dictStrategy() default "";

    /**
     * 如果是表就是表名
     *
     * @return 字典类型
     */
    String dictType();

    /**
     * 忽略控制
     *
     * @return
     */
    boolean ignoreEmpty() default true;
}
