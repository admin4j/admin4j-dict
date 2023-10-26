package com.admin4j.dict.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 字典对于转换数组支持
 *
 * @author andanyang
 * @since 2022/7/6 17:43
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DictList {

    /**
     * @return 数据间隔字符串
     */
    String separator() default "";


    /**
     * 过滤掉空的数据
     *
     * @return
     */
    boolean filterBlank() default true;

    /**
     * 是否需要去重
     *
     * @return 默认false:不去重
     */
    boolean unique() default false;
}
