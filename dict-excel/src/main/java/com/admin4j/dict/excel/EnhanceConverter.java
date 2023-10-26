package com.admin4j.dict.excel;

import com.admin4j.dict.anno.DictList;
import com.admin4j.spring.util.SpringUtils;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author andanyang
 * @since 2022/7/6 11:07
 */
public class EnhanceConverter implements Converter<Object> {


    /**
     * Back to object types in Java
     *
     * @return Support for Java class
     */
    @Override
    public Class<?> supportJavaTypeKey() {
        return Object.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /**
     * Convert excel objects to Java objects
     *
     * @param cellData            Excel cell data.NotNull.
     * @param contentProperty     Content property.Nullable.
     * @param globalConfiguration Global configuration.NotNull.
     * @return Data to put into a Java object
     * @throws Exception Exception.
     */
    @Override
    public Object convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {


        return enhanceConvertToJavaData(cellData, contentProperty, globalConfiguration);
    }

    protected Object enhanceConvertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {

        Map<String, ExcelEnhanceService> beansOfType = SpringUtils.getApplicationContext().getBeansOfType(ExcelEnhanceService.class);
        Collection<ExcelEnhanceService> excelEnhanceServices = beansOfType.values();
        Object value = null;
        for (ExcelEnhanceService service : excelEnhanceServices) {
            Object newValue = service.convertToJavaData(cellData, contentProperty, globalConfiguration);
            if (newValue != null) {
                value = newValue;
            }
        }

        return value;
    }

    @Override
    public WriteCellData<?> convertToExcelData(Object value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {

        String cellStr;
        if (value instanceof Collection) {

            DictList dictList = contentProperty.getField().getAnnotation(DictList.class);

            Collection<?> values = (Collection<?>) value;
            List<String> cells = new ArrayList<>(values.size());

            for (Object v : values) {
                String excelData = enhanceConvertToExcelData(v, contentProperty, globalConfiguration);
                if (dictList != null && dictList.filterBlank() && StringUtils.isBlank(excelData)) {
                    continue;
                }
                cells.add(excelData);
            }

            cellStr = StringUtils.join(cells, dictList == null ? "," : dictList.separator());
        } else {
            cellStr = enhanceConvertToExcelData(value, contentProperty, globalConfiguration);
        }

        return new WriteCellData<>(cellStr);
    }

    private String enhanceConvertToExcelData(Object value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {

        Map<String, ExcelEnhanceService> beansOfType = SpringUtils.getApplicationContext().getBeansOfType(ExcelEnhanceService.class);
        Collection<ExcelEnhanceService> excelEnhanceServices = beansOfType.values();
        for (ExcelEnhanceService service : excelEnhanceServices) {
            String newValue = service.convertToExcelData(value, contentProperty, globalConfiguration);
            if (newValue != null) {
                value = newValue;
            }
        }

        return value == null ? "" : value.toString();
    }
}
