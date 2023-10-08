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

public class PointDataSet extends SingleDataSet {
    private float radius = 5f;
    private int pointColor;

    public PointDataSet(Context context, String tag, LinkedList<Entry> entries) {
        super(context, tag, DataType.SinglePoint, entries);
        init();
    }


    private void init() {
        setEntryDrafter(this);
        Paint paint = new Paint();
        paint.setAntiAlias(true);//设置抗锯齿
        paint.setStyle(Paint.Style.STROKE);//实心
        paint.setStrokeWidth(4);//线条粗细
        paint.setColor(Color.WHITE);//线条粗细
        setPaint(paint);
        setShowMarker(true);
    }


    public void setLineColor(int pointColor) {
        this.pointColor = pointColor;
        paint.setColor(pointColor);
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
    public void drawSingleEntry(Canvas canvas,int index, PXY p, ViewportInfo viewportInfo) {
        canvas.drawCircle(p.getX(), p.getY(), radius, paint);
    }

    @Override
    public float getFoundNearRange() {
        return radius;
    }
}
