package com.admin4j.dict.provider.sql.impl;


import com.admin4j.dict.provider.sql.SqlDictManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

/**
 * @author andanyang
 * @since 2022/7/6 9:23
 */
//@Service
@Slf4j
@AllArgsConstructor
public class JdbcSqlDictManager implements SqlDictManager {

    private final JdbcTemplate jdbcTemplate;

    /**
     * 根据label 获取字典code
     *
     * @param dictType
     * @param dictLabel
     * @param codeFiled
     * @param labelFiled
     * @param whereSql
     * @return
     */
    @Override
    public Object dictCode(String dictType, String dictLabel, String codeFiled, String labelFiled, String whereSql) {
        if (dictLabel == null) {
            return null;
        }
        StringBuilder sql = new StringBuilder("select " + codeFiled + " as `code` from " + dictType);


        List<String> wheres = new ArrayList<>(2);
        if (StringUtils.isNotBlank(whereSql)) {
            wheres.add(whereSql);
        }

        wheres.add(labelFiled + "= ? ");

        sql.append(" WHERE ");
        boolean isFirst = true;
        for (String where : wheres) {
            if (!isFirst) {
                sql.append(" AND ");
            }
            sql.append(where);
            isFirst = false;
        }

        sql.append(" LIMIT 1");
        String sqlStr = sql.toString();
        if (log.isDebugEnabled()) {
            log.debug("dict SQL: {},code: {}", sqlStr, dictLabel);
        }

        // TODO: 租户
        try {
            Map<String, Object> map = jdbcTemplate.queryForMap(sqlStr, dictLabel);
            return map.get("code").toString();
        } catch (EmptyResultDataAccessException exception) {
            return "";
        }
    }

    /**
     * 获取字典显示值
     *
     * @param dictType
     * @param code
     * @param codeFiled
     * @param labelFiled
     * @param whereSql
     * @return
     */
    @Override
    public String dictLabel(String dictType, Object code, String codeFiled, String labelFiled, String whereSql) {

        if (code == null) {
            return null;
        }
        StringBuilder sql = new StringBuilder("select " + labelFiled + " as `label` from " + dictType);


        List<String> wheres = new ArrayList<>(2);
        if (StringUtils.isNotBlank(whereSql)) {
            wheres.add(whereSql);
        }

        wheres.add(codeFiled + "= ? ");

        sql.append(" WHERE ");
        boolean isFirst = true;
        for (String where : wheres) {
            if (!isFirst) {
                sql.append(" AND ");
            }
            sql.append(where);
            isFirst = false;
        }

        sql.append(" LIMIT 1");
        String sqlStr = sql.toString();
        if (log.isDebugEnabled()) {
            log.debug("dict SQL: {},code: {}", sqlStr, code);
        }

        // TODO: 租户
        try {
            Map<String, Object> map = jdbcTemplate.queryForMap(sqlStr, code);
            return map.get("label").toString();
        } catch (EmptyResultDataAccessException exception) {
            return "";
        }
    }

    /**
     * 批量获取使用 in 查询
     */
    @Override
    public Map<Object, String> dictLabels(String dictType, Collection<Object> dictCodes, String codeFiled, String labelFiled, String whereSql) {
        if (dictCodes == null || dictCodes.isEmpty()) {
            return Collections.emptyMap();
        }

        if (dictCodes.size() == 1) {
            Object code = dictCodes.iterator().next();
            String dictLabel = dictLabel(dictType, code, codeFiled, labelFiled, whereSql);
            if (dictLabel == null) {
                return Collections.emptyMap();
            }
            Map<Object, String> dict = new HashMap<>(2);
            dict.put(code, dictLabel);
            return dict;
        }

        StringBuilder sql = new StringBuilder("select " + labelFiled + " as `value`," + codeFiled + " as `key` from " + dictType);
        sql.append(" WHERE ");

        // inSql
        sql.append(codeFiled).append(" IN (");

        boolean isFirst = true;
        for (Object code : dictCodes) {
            sql.append(isFirst ? "?" : ",?");
            isFirst = false;
        }
        sql.append(") ");


        if (StringUtils.isNotBlank(whereSql)) {
            sql.append(" AND ");
            sql.append(whereSql);
        }

        String sqlStr = sql.toString();
        if (log.isDebugEnabled()) {
            log.debug("dict SQL: {},code: {}", sqlStr, dictCodes);
        }

        // TODO: 租户
        try {
            Object[] dictCodesArray = dictCodes.toArray();

            List<Map<String, Object>> maps = jdbcTemplate.queryForList(sqlStr, dictCodesArray);

            Map<Object, String> result = new HashMap<>(dictCodes.size());
            for (Map<String, Object> map : maps) {
                result.put(map.get("key"), map.get("value").toString());
            }
            return result;
        } catch (EmptyResultDataAccessException exception) {
            return Collections.emptyMap();
        }
    }
}
