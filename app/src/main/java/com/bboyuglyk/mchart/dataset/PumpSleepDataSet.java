package com.bboyuglyk.mchart.dataset;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.bboyuglyk.chart_sdk.DataType;
import com.bboyuglyk.chart_sdk.Entry;
import com.bboyuglyk.chart_sdk.dataset.DataSet;

import java.util.LinkedList;

public class PumpSleepDataSet extends DataSet {
    public PumpSleepDataSet(Context context, String tag, LinkedList<Entry> entries) {
        super(context,tag, DataType.line_rect,entries, entries);
        init();
    }

    private void init() {
        setEntryDrafter(this);
        Paint paint = new Paint();
        paint.setAntiAlias(true);//设置抗锯齿
        paint.setStyle(Paint.Style.FILL);//实心
        paint.setStrokeWidth(8);//线条粗细
        paint.setColor(Color.rgb(235, 235, 235));//线条粗细
        setPaint(paint);
        setShowMarker(false);
    }

    @Override
    public void drawRectEntry(Canvas canvas, float rLeft, float rTop, float rRight, float rBottom, float left, float top, float right, float bottom) {
        canvas.drawRect(rLeft, rTop, rRight, rBottom, paint);
    }


    @Override
    public float getFoundNearRange() {
        return 0;
    }
}
