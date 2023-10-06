package com.bboyuglyk.mchart.new_dataset;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;

import com.bboyuglyk.chart_sdk.DataType;
import com.bboyuglyk.chart_sdk.Entry;
import com.bboyuglyk.chart_sdk.PXY;
import com.bboyuglyk.chart_sdk.ViewportInfo;
import com.bboyuglyk.chart_sdk.dataset.SingleDataSet;

import java.util.LinkedList;

public class CurveLineDataSet extends SingleDataSet {
    private float radius = 5f;
    private int[] colors;

    public CurveLineDataSet(Context context, String tag, LinkedList<Entry> entries) {
        super(context, tag, DataType.CurveDouble, entries);
        init();
    }

    public void setColors(int[] colors) {
        this.colors = colors;
    }

    private void init() {
        setEntryDrafter(this);
        Paint paint = new Paint();
        paint.setAntiAlias(true);//设置抗锯齿
        paint.setStyle(Paint.Style.FILL_AND_STROKE);//实心
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
    public void drawDoubleEntry(Canvas canvas,int index, PXY p1,PXY p2, ViewportInfo viewportInfo) {
        Path path=new Path();
        path.moveTo(p1.x,p1.y);
        path.lineTo(p2.x,p2.y);
        path.lineTo(p2.x,viewportInfo.bottom);
        path.lineTo(p1.x,viewportInfo.bottom);
        path.close();

        LinearGradient linearGradient = new LinearGradient(p1.x, viewportInfo.top, p1.x, viewportInfo.bottom, colors,
                null,
                Shader.TileMode.REPEAT);
        paint.setShader(linearGradient);

        canvas.drawPath(path, paint);
//        canvas.drawLine(p1.getX(), p1.getY(),p2.getX(), p2.getY(), paint);
    }

    @Override
    public float getFoundNearRange() {
        return radius;
    }
}
