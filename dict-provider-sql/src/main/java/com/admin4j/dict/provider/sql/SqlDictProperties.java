package com.admin4j.dict.provider.sql;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author andanyang
 * @since 2023/10/30 15:35
 */
@Data
@ConfigurationProperties(prefix = "admin4j.dict")
public class SqlDictProperties {

    /**
     * 使用 ymal 的方式 sql 配置字典
     */
    private Map<String, SqlDict> sqlDictMap;
    /**
     * 是否开启sql 配置字典
     */
    private boolean sqlDict = false;

    @Data
    public static class SqlDict {
     
        // private String dictType;
        /**
         * 字典值字段
         */
        private String codeFiled;
        /**
         * 字典显示字段
         */
        private String labelFiled;
        /**
         * 字典添加字段
         */
        private String whereSql;
    }
}
