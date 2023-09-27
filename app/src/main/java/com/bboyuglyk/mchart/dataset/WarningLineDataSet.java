package com.bboyuglyk.mchart.dataset;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.bboyuglyk.chart_sdk.DataType;
import com.bboyuglyk.chart_sdk.Entry;
import com.bboyuglyk.chart_sdk.dataset.DataSet;

import java.util.LinkedList;

public class WarningLineDataSet extends DataSet {


    public WarningLineDataSet(Context context, String tag, LinkedList<Entry> entries) {
        super(context,tag, DataType.line,entries, entries);
        init();
    }

    private void init() {
        setEntryDrafter(this);
        Paint paint = new Paint();
        paint.setAntiAlias(true);//设置抗锯齿
        paint.setStyle(Paint.Style.STROKE);//实心
        paint.setStrokeWidth(4);//线条粗细
        paint.setColor(Color.RED);//线条粗细
        setPaint(paint);
        setShowMarker(false);
    }


    @Override
    public void drawLineEntry(Canvas canvas, float fromX, float fromY, float toX, float toY, float left, float top, float right, float bottom) {
        canvas.drawLine(fromX, fromY, toX, toY, paint);
    }


    @Override
    public float getFoundNearRange() {
        return 0;
    }
}
