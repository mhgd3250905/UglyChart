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

public class BarDataSet extends SingleDataSet {
    private float radius = 5f;

    public BarDataSet(Context context, String tag, LinkedList<Entry> entries) {
        super(context, tag, DataType.SinglePoint, entries);
        init();
    }


    private void init() {
        setEntryDrafter(this);
        Paint paint = new Paint();
        paint.setAntiAlias(true);//设置抗锯齿
        paint.setStyle(Paint.Style.FILL);//实心
        paint.setStrokeWidth(4);//线条粗细
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
    public void drawSingleEntry(Canvas canvas, PXY p, ViewportInfo viewportInfo) {
        canvas.drawRect(p.getX()-10,p.getY(),p.getX()+10,viewportInfo.bottom,paint);
    }

    @Override
    public float getFoundNearRange() {
        return radius;
    }
}
