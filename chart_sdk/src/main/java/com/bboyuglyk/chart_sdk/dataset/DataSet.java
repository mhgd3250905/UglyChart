package com.bboyuglyk.chart_sdk.dataset;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;


import com.bboyuglyk.chart_sdk.DataType;
import com.bboyuglyk.chart_sdk.Entry;
import com.bboyuglyk.chart_sdk.IEntryDrafter;
import com.bboyuglyk.chart_sdk.R;

import java.util.LinkedList;

public abstract class DataSet implements IEntryDrafter {
    //tip 上下文
    protected Context context;
    //tip tag区分
    protected String tag;
    //tip 数据类型
    protected DataType type;
    //tip 传入的完整数据
    protected LinkedList<Entry> entries;
    //tip entry绘制
    private IEntryDrafter entryDrafter;
    //tip 画笔
    protected Paint paint;
    //tip 是否可以显示到marker
    private boolean showMarker;
    private boolean isY2;


    public DataSet(Context context) {
        this.context = context;
    }

//    //tip 获取优先级
//    public abstract int getPriority();

    public DataSet(Context context,String tag, DataType type, LinkedList<Entry> entries) {
        this.tag = tag;
        this.context = context;
        this.type = type;
        this.entries = (LinkedList<Entry>) entries.clone();
        for (int i = 0; i < this.entries.size(); i++) {
            entries.get(i).setTag(tag);
            entries.get(i).setDataType(type);
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
    public void drawPointEntry(Canvas canvas, float x, float y, float left, float top, float right, float bottom) {

    }

    @Override
    public void drawRectEntry(Canvas canvas, float rLeft, float rTop, float rRight, float rBottom, float left, float top, float right, float bottom) {

    }

    @Override
    public void drawLineEntry(Canvas canvas, float fromX, float fromY, float toX, float toY, float left, float top, float right, float bottom) {

    }

    @Override
    public void drawComposeRect(Canvas canvas, float left, float top, float right, float bottom, float x, float maxY, float... midY) {

    }

    @Override
    public void drawRangeBar(Canvas canvas, float x, float y, float left, float top, float right, float bottom,float range){

    }




    public void setY2(boolean y2) {
        isY2 = y2;
    }

    public boolean isY2() {
        return this.isY2;
    }

    public  int getHighlightBgColor(){
        return ContextCompat.getColor(context, R.color.color_main);
    }
}
