package com.admin4j.dict.excel.enhance;

import com.admin4j.dict.anno.Dict;
import com.admin4j.dict.anno.DictList;
import com.admin4j.dict.anno.DictProviderManager;
import com.admin4j.dict.anno.ObjectCast;
import com.admin4j.dict.excel.ExcelEnhanceService;
import com.admin4j.spring.util.SpringUtils;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author andanyang
 * @since 2022/7/20 14:02
 */
@Order(1)
public class DictEnhanceService implements ExcelEnhanceService {

    /**
     * Convert excel objects to Java objects
     * Params:
     * cellData – Excel cell data.NotNull. contentProperty – Content property.Nullable. globalConfiguration – Global configuration.NotNull.
     * Returns:
     * Data to put into a Java object
     * Throws:
     * Exception – Exception.
     *
     * @param cellData
     * @param contentProperty
     * @param globalConfiguration
     */
    @Override
    public Object convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if (cellData.getStringValue() == null) {
            return null;
        }
        Field field = contentProperty.getField();
        Dict dict = field.getAnnotation(Dict.class);
        if (dict == null) {
            return null;
        } else {
            if (Collection.class.isAssignableFrom(field.getType())) {

                boolean isList = List.class.isAssignableFrom(field.getType());
                DictList dictList = field.getAnnotation(DictList.class);
                String[] split = StringUtils.split(cellData.getStringValue(), dictList == null ? "," : dictList.separator());
                if (split.length == 1) {
                    Object dictCode = SpringUtils.getBean(DictProviderManager.class).dictCode(field, dict.dictStrategy(), dict.dictType(), cellData.getStringValue());
                    return isList ? Collections.singletonList(dictCode) : Collections.singleton(dictCode);
                }

                Collection<Object> splitList = isList ? new ArrayList<>(split.length) : new HashSet<>(split.length);
                for (int i = 0; i < split.length; i++) {
                    Object dictCode = SpringUtils.getBean(DictProviderManager.class).dictCode(field, dict.dictStrategy(), dict.dictType(), split[i]);

                    splitList.add(dictCode);
                }

                return splitList;
            } else {
                Object dictCode = SpringUtils.getBean(DictProviderManager.class).dictCode(field, dict.dictStrategy(), dict.dictType(), cellData.getStringValue());
                if (dictCode == null) {
                    return null;
                }
                return ObjectCast.cast(dictCode, field.getType());
            }
        }
    }

    @Override
    public String convertToExcelData(Object value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        Field field = contentProperty.getField();
        Dict dict = field.getAnnotation(Dict.class);
        if (dict == null) {
            return null;
        } else {

            String label = SpringUtils.getBean(DictProviderManager.class).dictLabel(field, dict.dictStrategy(), dict.dictType(), value);
            return StringUtils.defaultString(label);
        }
    }
}
