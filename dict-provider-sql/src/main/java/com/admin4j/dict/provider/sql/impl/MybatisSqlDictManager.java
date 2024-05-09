package com.admin4j.dict.provider.sql.impl;


import com.admin4j.dict.provider.sql.SqlDictManager;
import com.admin4j.dict.provider.sql.impl.mapper.SqlDictMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author andanyang
 * @since 2022/7/24 13:36
 */

@RequiredArgsConstructor
public class MybatisSqlDictManager implements SqlDictManager {

    private final SqlDictMapper sqlDictMapper;

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
        return sqlDictMapper.dictLabel(dictType, code, codeFiled, labelFiled, whereSql);
    }

    /**
     * 批量获取使用 in 查询
     */
    @Override
    public Map<Object, String> dictLabels(String dictType, Collection<Object> dictCodes, String codeFiled, String labelFiled, String whereSql) {
        if (dictCodes == null || dictCodes.isEmpty()) {
            return Collections.emptyMap();
        }
        if (codeFiled.length() == 1) {
            Object code = dictCodes.iterator().next();
            String label = dictLabel(dictType, code, codeFiled, labelFiled, whereSql);
            if (StringUtils.isBlank(label)) {
                return Collections.emptyMap();
            }

            Map<Object, String> dict = new HashMap<>(2);
            dict.put(code, label);
            return dict;
        }
        ArrayList<HashMap<String, Object>> hashMaps = sqlDictMapper.dictLabels(dictType, dictCodes, codeFiled, labelFiled, whereSql);

        Map<Object, String> result = new HashMap<>(codeFiled.length());
        for (HashMap<String, Object> map : hashMaps) {
            Object o = map.get("label");
            if (o instanceof String) {
                result.put(map.get("code"), (String) o);
            } else {
                result.put(map.get("code"), o == null ? null : o.toString());
            }
        }
        return result;
    }

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
        return sqlDictMapper.dictCode(dictType, dictLabel, codeFiled, labelFiled, whereSql);
    }
}
