package com.bboyuglyk.mchart.dataset;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;

import com.bboyuglyk.chart_sdk.DataType;
import com.bboyuglyk.chart_sdk.Entry;
import com.bboyuglyk.chart_sdk.dataset.DataSet;

import java.util.LinkedList;

public class SuspendLineDataSet extends DataSet {

    public SuspendLineDataSet(Context context, String tag, LinkedList<Entry> entries) {
        super(context,tag, DataType.line,entries, entries);
        init();
    }

    private void init() {
        setEntryDrafter(this);
        Paint paint = new Paint();
        paint.setAntiAlias(true);//设置抗锯齿
        paint.setStyle(Paint.Style.STROKE);//实心
        paint.setStrokeWidth(8);//线条粗细
        paint.setColor(Color.RED);//线条粗细
        paint.setPathEffect(new DashPathEffect(new float[]{4,4},0));
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        setPaint(paint);
        setShowMarker(true);
    }

    @Override
    public void drawLineEntry(Canvas canvas, float fromX, float fromY, float toX, float toY, float left, float top, float right, float bottom) {
        canvas.drawLine(fromX,fromY,toX,toY,paint);
    }


    @Override
    public float getFoundNearRange() {
        return 0;
    }

}
