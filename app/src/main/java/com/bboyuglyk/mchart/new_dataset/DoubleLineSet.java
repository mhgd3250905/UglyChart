package com.bboyuglyk.mchart.new_dataset;

import static com.bboyuglyk.mchart.BezierUtils.calculatePointCoordinate;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.Log;

import androidx.annotation.IntRange;

import com.bboyuglyk.chart_sdk.DataType;
import com.bboyuglyk.chart_sdk.Entry;
import com.bboyuglyk.chart_sdk.PXY;
import com.bboyuglyk.chart_sdk.ViewportInfo;
import com.bboyuglyk.chart_sdk.dataset.SingleDataSet;
import com.bboyuglyk.mchart.BezierUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DoubleLineSet extends SingleDataSet {
    private float radius = 5f;

    public DoubleLineSet(Context context, String tag, LinkedList<Entry> entries) {
        super(context, tag, DataType.DoublePoint, entries);
        init();
    }


    private void init() {
        setEntryDrafter(this);
        Paint paint = new Paint();
        paint.setAntiAlias(true);//设置抗锯齿
        paint.setStyle(Paint.Style.STROKE);//实心
        paint.setStrokeWidth(4);//线条粗细
        paint.setColor(Color.BLUE);//线条粗细
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
    public void drawDoubleEntry(Canvas canvas, PXY p1, PXY p2, ViewportInfo viewportInfo) {
        Path path=new Path();
        path.moveTo(p1.x,p1.y);
        path.lineTo(p2.x,p2.y);
        canvas.drawPath(path,paint);
    }





    @Override
    public float getFoundNearRange() {
        return radius;
    }
}
