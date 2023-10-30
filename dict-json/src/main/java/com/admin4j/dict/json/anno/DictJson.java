package com.admin4j.dict.json.anno;

import com.admin4j.dict.json.DictSerializer;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author andanyang
 * @since 2022/8/10 17:12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@JacksonAnnotationsInside
@JsonSerialize(
        using = DictSerializer.class
        // keyUsing = DictSerializer.class
)
public @interface DictJson {

    /**
     * 显示字段你名称,默认 "" 写在原先的字段上
     * 列子：
     * 原本： {"userId":1}。设置 fieldName = "username"，最终渲染结果为  {"userId":1,"username":"anonymous"}
     *
     * @return
     */
    String fieldName() default "";
}
