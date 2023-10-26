package com.admin4j.dict.excel;

import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * @author andanyang
 * @since 2022/7/20 13:45
 */
public interface ExcelEnhanceService {


    /**
     * Convert excel objects to Java objects
     * Params:
     * cellData – Excel cell data.NotNull. contentProperty – Content property.Nullable. globalConfiguration – Global configuration.NotNull.
     * Returns:
     * Data to put into a Java object
     * Throws:
     * Exception – Exception.
     */
    Object convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration);


    /**
     * 返回null 表示不处理
     */
    String convertToExcelData(Object value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration);
}
