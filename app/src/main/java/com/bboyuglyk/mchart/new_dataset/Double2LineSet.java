package com.bboyuglyk.mchart.new_dataset;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import com.bboyuglyk.chart_sdk.DataType;
import com.bboyuglyk.chart_sdk.Entry;
import com.bboyuglyk.chart_sdk.PXY;
import com.bboyuglyk.chart_sdk.ViewportInfo;
import com.bboyuglyk.chart_sdk.dataset.SingleDataSet;

import java.util.LinkedList;

public class Double2LineSet extends SingleDataSet {
    private float radius = 5f;

    public Double2LineSet(Context context, String tag, LinkedList<Entry> entries) {
        super(context, tag, DataType.DoublePoint, entries);
        init();
    }


    private void init() {
        setEntryDrafter(this);
        Paint paint = new Paint();
        paint.setAntiAlias(true);//设置抗锯齿
        paint.setStyle(Paint.Style.STROKE);//实心
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
    public void drawDoubleEntry(Canvas canvas,int index, PXY p1, PXY p2, ViewportInfo viewportInfo) {
        float x1=p1.getX();
        float x2=p2.getX();
        float y1=p1.getY();
        float y2=p2.getY();

        float midX=(x1+x2)/2;
        float midY=(y1+y2)/2;

        float cX1=midX;
        float cY1=y1;
        float cX2=midX;
        float cY2=y2;

        Path path=new Path();
        path.moveTo(x1,y1);
        path.lineTo(midX,midY);
        path.lineTo(x2,y2);
        canvas.drawPath(path,paint);

//        canvas.drawLine(p1.getX(),viewportInfo.getBottom(),p1.getX(),p1.getY(),paint);
//        canvas.drawLine(p1.getX(),p1.getY(),p2.getX(),p2.getY(),paint);
//        canvas.drawLine(p2.getX(),p2.getY(),p2.getX(),viewportInfo.getBottom(),paint);
    }


    @Override
    public float getFoundNearRange() {
        return radius;
    }
}
