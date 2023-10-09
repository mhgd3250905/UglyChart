package com.bboyuglyk.chart_sdk;

import android.content.Context;

import androidx.core.content.ContextCompat;


public class Entry {
    private float x;
    private float px;
    private float y;
    private float py;
    private float y2;
    private float[] yValues;
    private Object data;
    private String tag;
    private float nearGap;
    private DataType dataType;
    private int highlightColor;

    public Entry(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Entry(float x, float y, Object data) {
        this.x = x;
        this.y = y;
        this.data = data;
    }

    public Entry(float x, float y, float y2) {
        this.x = x;
        this.y = y;
        this.y2 = y2;
    }

    public Entry(float x, float y, float y2, Object data) {
        this.x = x;
        this.y = y;
        this.y2 = y2;
        this.data = data;
    }

    public Entry(float x, float[] yValues, Object data) {
        this.x = x;
        this.y = calcSum(yValues);
        this.yValues = yValues;
        this.data = data;
    }

    private static float calcSum(float[] vals) {
        if (vals == null) {
            return 0.0F;
        } else {
            float sum = 0.0F;
            float[] var2 = vals;
            int var3 = vals.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                float f = var2[var4];
                sum += f;
            }

            return sum;
        }
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public float getNearGap() {
        return nearGap;
    }

    public void setNearGap(float nearGap) {
        this.nearGap = nearGap;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public static Entry getNullEntry() {
        return new Entry(-1f, -1f);
    }

    public boolean isNull() {
        return getX() == -1f && getY() == -1f;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getY2() {
        return y2;
    }

    public void setY2(float y2) {
        this.y2 = y2;
    }

    public float getPx() {
        return px;
    }

    public void setPx(float px) {
        this.px = px;
    }

    public float getPy() {
        return py;
    }

    public void setPy(float py) {
        this.py = py;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public float[] getyValues() {
        return yValues;
    }

    public void setyValues(float[] yValues) {
        this.yValues = yValues;
    }

    public int getHighlightColor(Context context) {
        if(highlightColor==0){
            highlightColor= ContextCompat.getColor(context,R.color.color_main);
        }
        return highlightColor;
    }

    public void setHighlightColor(int highlightColor) {
        this.highlightColor = highlightColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entry entry = (Entry) o;

        if (Float.compare(entry.x, x) != 0) return false;
        if (Float.compare(entry.px, px) != 0) return false;
        if (Float.compare(entry.y, y) != 0) return false;
        if (Float.compare(entry.y2, y2) != 0) return false;
        if (tag != null ? !tag.equals(entry.tag) : entry.tag != null) return false;
        return dataType == entry.dataType;
    }

    @Override
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (px != +0.0f ? Float.floatToIntBits(px) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        result = 31 * result + (y2 != +0.0f ? Float.floatToIntBits(y2) : 0);
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        result = 31 * result + (dataType != null ? dataType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "x=" + x +
                ", y=" + y +
                ", y2=" + y2 +
                ", data=" + data +
                '}';
    }


}
