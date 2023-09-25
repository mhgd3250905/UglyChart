package com.bboyuglyk.mchart.new_dataset;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.bboyuglyk.chart_sdk.DataType;
import com.bboyuglyk.chart_sdk.Entry;
import com.bboyuglyk.chart_sdk.PXY;
import com.bboyuglyk.chart_sdk.ViewportInfo;
import com.bboyuglyk.chart_sdk.dataset.SingleDataSet;

import java.util.LinkedList;

public class CurceLineDataSet extends SingleDataSet {
    private float radius = 5f;

    public CurceLineDataSet(Context context, String tag, LinkedList<Entry> entries) {
        super(context, tag, DataType.CurveLine, entries);
        init();
    }


    private void init() {
        setEntryDrafter(this);
        Paint paint = new Paint();
        paint.setAntiAlias(true);//设置抗锯齿
        paint.setStyle(Paint.Style.FILL);//实心
        paint.setStrokeWidth(2);//线条粗细
        paint.setColor(Color.BLACK);//线条粗细
        setPaint(paint);
        setShowMarker(true);
    }


    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public void drawPointEntry(Canvas canvas, float x, float y, float left, float top, float right, float bottom) {
        canvas.drawCircle(x, y, radius, paint);
    }

    @Override
    public void drawSingleEntry(Canvas canvas, PXY p, ViewportInfo viewportInfo) {
        canvas.drawCircle(p.getX(), p.getY(), radius, paint);
    }

    @Override
    public float getFoundNearRange() {
        return radius;
    }
}
