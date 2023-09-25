package com.bboyuglyk.chart_sdk;

public class BaseAxis {
    private int midCount;
    private int min;
    private int max;
    private ILabelFormatter iLabelFormatter;
    private int[] labelArrs;

    public BaseAxis() { }

    public BaseAxis(int midCount, int min, int max, ILabelFormatter iLabelFormatter) {
        this.midCount = midCount;
        this.min = min;
        this.max = max;
        this.iLabelFormatter = iLabelFormatter;
    }


    public int getMidCount() {
        return midCount;
    }

    /**
     * 设置中间分隔个数:优先级低于 LabelArrs
     * @param midCount
     */
    public void setMidCount(int midCount) {
        this.midCount = midCount;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public ILabelFormatter getiLabelFormatter() {
        return iLabelFormatter;
    }

    public void setiLabelFormatter(ILabelFormatter iLabelFormatter) {
        this.iLabelFormatter = iLabelFormatter;
    }

    public int[] getLabelArrs() {
        return labelArrs;
    }

    public void setLabelArrs(int[] labelArrs) {
        this.labelArrs = labelArrs;
    }

    public BaseAxis clone(){
        return new BaseAxis(midCount,min,max,iLabelFormatter);
    }

    @Override
    public String toString() {
        return "BaseAxis{" +
                "midCount=" + midCount +
                ", min=" + min +
                ", max=" + max +
                '}';
    }
}
