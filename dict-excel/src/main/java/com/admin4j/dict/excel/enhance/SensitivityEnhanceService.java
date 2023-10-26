package com.admin4j.dict.excel.enhance;

import com.admin4j.dict.excel.ExcelEnhanceService;
import com.admin4j.framework.desensitize.core.Sensitivity;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Field;

/**
 * @author andanyang
 * @since 2022/7/20 14:02
 */

@Order(2)
public class SensitivityEnhanceService implements ExcelEnhanceService {

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
    public String convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        return null;
    }

    @Override
    public String convertToExcelData(Object value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        Field field = contentProperty.getField();
        Sensitivity sensitivity = field.getAnnotation(Sensitivity.class);
        if (sensitivity == null) {
            return null;
        } else {
            return sensitivity.strategy().desensitizer().apply((String) value);
        }
    }
}
