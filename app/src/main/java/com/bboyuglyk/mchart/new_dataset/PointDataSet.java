package com.bboyuglyk.mchart.new_dataset;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.bboyuglyk.chart_sdk.DataType;
import com.bboyuglyk.chart_sdk.Entry;
import com.bboyuglyk.chart_sdk.PXY;
import com.bboyuglyk.chart_sdk.ViewportInfo;
import com.bboyuglyk.chart_sdk.dataset.SingleDataSet;
import com.bboyuglyk.mchart.R;

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


        Paint highlightPaint = new Paint();
        highlightPaint.setAntiAlias(true);//设置抗锯齿
        highlightPaint.setStyle(Paint.Style.STROKE);//实心
        highlightPaint.setStrokeWidth(5f);//线条粗细
        highlightPaint.setColor(ContextCompat.getColor(context, R.color.high_light_shadow_color));//线条粗细
        highlightPaint.setShadowLayer(20f,0f,0f, ContextCompat.getColor(context, R.color.high_light_shadow_color));
        setHighlightPaint(highlightPaint);
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
    public void drawHighlightEntry(Canvas canvas, int index, PXY p, ViewportInfo viewportInfo) {
        canvas.drawCircle(p.x, p.y, 15f, highlightPaint);
    }

    @Override
    public float getFoundNearRange() {
        return 40f;
    }
}
