package com.bboyuglyk.chart_sdk.dataset;

import java.util.Comparator;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class DataSetBox {
    //tip 保存各种类型的数据源
    private ConcurrentSkipListMap<String, DataSet> dataSetMap;

    public DataSetBox(Comparator<String> tagComparator) {
        this.dataSetMap = new ConcurrentSkipListMap<>(tagComparator);
    }

    public SortedMap<String, DataSet> getDataSetMap() {
        return dataSetMap;
    }

    public void setDataSetMap(ConcurrentSkipListMap<String, DataSet> dataSetMap) {
        this.dataSetMap = dataSetMap;
    }

    public void addDataSet(DataSet dataSet) {
        dataSetMap.put(dataSet.getTag(), dataSet);
    }
}
