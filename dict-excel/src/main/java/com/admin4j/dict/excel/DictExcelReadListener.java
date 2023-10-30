package com.admin4j.dict.excel;

import com.admin4j.dict.core.impl.CachedDictProviderManager;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import lombok.AllArgsConstructor;

/**
 * @author andanyang
 * @since 2022/8/8 16:48
 */
@AllArgsConstructor
public class DictExcelReadListener implements ReadListener<Object> {


    private final CachedDictProviderManager cachedDictProviderManager;

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        cachedDictProviderManager.clearCache();
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {

        cachedDictProviderManager.clearCache();
    }

    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {

    }
}
