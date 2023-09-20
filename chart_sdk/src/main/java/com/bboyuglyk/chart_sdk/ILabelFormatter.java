package com.bboyuglyk.chart_sdk;

public interface ILabelFormatter {

    /**
     * 格式化label
     * @param value 数值
     * @param min 最小值
     * @param max 最大值
     * @return formatterStr
     */
    String getLabelFormat(int value, int min, int max);
}
