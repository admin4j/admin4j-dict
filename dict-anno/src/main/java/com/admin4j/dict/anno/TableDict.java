package com.admin4j.dict.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 数据库字典，支持成单表，查询
 *
 * @author andanyang
 * @since 2022/7/6 17:43
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TableDict {

    /**
     * @return 展示字段
     */
    String labelField() default "";

    /**
     * @return 值字段
     */
    String codeField() default "";

    /**
     * 查询sql。比如 status = 1
     */
    String whereSql() default "";
}
