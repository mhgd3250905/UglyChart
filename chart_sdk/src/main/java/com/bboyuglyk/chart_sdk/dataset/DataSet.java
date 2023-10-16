package com.bboyuglyk.chart_sdk.dataset;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;


import com.bboyuglyk.chart_sdk.DataType;
import com.bboyuglyk.chart_sdk.Entry;
import com.bboyuglyk.chart_sdk.IEntryDrafter;
import com.bboyuglyk.chart_sdk.PXY;
import com.bboyuglyk.chart_sdk.R;
import com.bboyuglyk.chart_sdk.ViewportInfo;

import java.util.LinkedList;

public abstract class DataSet implements IEntryDrafter {
    private static final String TAG = "DataSet";
    //tip 上下文
    protected Context context;
    //tip tag区分
    protected String tag;
    //tip 数据类型
    protected DataType type;
    //tip 原始数据
    protected LinkedList<Entry> baseEntries;
    //tip 传入的完整数据
    protected LinkedList<Entry> entries;
    //tip entry绘制
    protected IEntryDrafter entryDrafter;
    //tip 画笔
    protected Paint paint;
    //tip Highlight画笔
    protected Paint highlightPaint;
    //tip 是否可以显示到marker
    protected boolean showMarker;
    protected boolean isY2;


    public DataSet(Context context) {
        this.context = context;
    }


    public DataSet(Context context, String tag, DataType type) {
        this.tag = tag;
        this.context = context;
        this.type = type;
    }

    public DataSet(Context context, String tag, DataType type, LinkedList<Entry> baseEntries, LinkedList<Entry> entries) {
        switch (type) {
            case SinglePoint:
                break;
            case DoublePoint:
                if (entries.size() % 2 != 0) {
                    Log.e(TAG, "datrset DoubleLineSet entries size must %2 ==0");
                    assert false;
                }
                break;
            case TriplePoint:
                if (entries.size() % 3 != 0) {
                    Log.e(TAG, "datrset DoubleLineSet entries size must %3 ==0");
                    assert false;
                }
                break;
            case QuatraPoint:
                if (entries.size() % 2 != 0) {
                    Log.e(TAG, "datrset DoubleLineSet entries size must %4 ==0");
                    assert false;
                }
                break;
        }
        this.tag = tag;
        this.context = context;
        this.type = type;
        this.baseEntries = (LinkedList<Entry>) baseEntries.clone();
        this.entries = (LinkedList<Entry>) entries.clone();
        for (int i = 0; i < this.entries.size(); i++) {
            this.entries.get(i).setTag(tag);
            this.entries.get(i).setDataType(type);
        }
    }


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public DataType getType() {
        return type;
    }

    public void setType(DataType type) {
        this.type = type;
    }


    public IEntryDrafter getEntryDrafter() {
        return entryDrafter;
    }

    public void setEntryDrafter(IEntryDrafter entryDrafter) {
        this.entryDrafter = entryDrafter;
    }

    public LinkedList<Entry> getBaseEntries() {
        return baseEntries;
    }

    public void setBaseEntries(LinkedList<Entry> baseEntries) {
        this.baseEntries = baseEntries;
    }

    public LinkedList<Entry> getEntries() {
        return entries;
    }

    public void setEntries(LinkedList<Entry> entries) {
        this.entries = entries;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public Paint getHighlightPaint() {
        return highlightPaint;
    }

    public void setHighlightPaint(Paint highlightPaint) {
        this.highlightPaint = highlightPaint;
    }

    public boolean isShowMarker() {
        return showMarker;
    }

    public void setShowMarker(boolean showMarker) {
        this.showMarker = showMarker;
    }

    @Override
    public String toString() {
        return "DataSet{" +
                "tag='" + type + '\'' +
                ", entries=" + entries +
                '}';
    }

    /**
     * tip 设置选择高亮场景下的被找到的感知范围
     *
     * @return
     */
    public abstract float getFoundNearRange();

    @Override
    public void drawSingleEntry(Canvas canvas, int index, PXY p, ViewportInfo viewportInfo) {

    }

    @Override
    public void drawDoubleEntry(Canvas canvas, int index, PXY p1, PXY p2, ViewportInfo viewportInfo) {

    }

    @Override
    public void drawTripleEntry(Canvas canvas, int index, PXY p1, PXY p2, PXY p3, ViewportInfo viewportInfo) {

    }

    @Override
    public void drawQuatraEntry(Canvas canvas, int index, PXY p1, PXY p2, PXY p3, PXY p4, ViewportInfo viewportInfo) {

    }

    @Override
    public void drawPieEntries(Canvas canvas, ViewportInfo viewportInfo, PXY touchP, Entry... entries) {

    }

    @Override
    public void drawHighlightEntry(Canvas canvas, int index, PXY p, ViewportInfo viewportInfo) {

    }

    public void setY2(boolean y2) {
        isY2 = y2;
    }

    public boolean isY2() {
        return this.isY2;
    }

    public int getHighlightBgColor() {
        return ContextCompat.getColor(context, R.color.color_main);
    }

}
