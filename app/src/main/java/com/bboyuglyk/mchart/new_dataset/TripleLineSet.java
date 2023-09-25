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

public class TripleLineSet extends SingleDataSet {
    private float radius = 5f;

    public TripleLineSet(Context context, String tag, LinkedList<Entry> entries) {
        super(context, tag, DataType.TriplePoint, entries);
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
    public void drawTripleEntry(Canvas canvas, PXY p1, PXY p2, PXY p3, ViewportInfo viewportInfo) {
        super.drawTripleEntry(canvas, p1, p2, p3, viewportInfo);
        float u = 0.5f;
        float ax = (p1.getX()+p2.getX())/2;
        float ay = (p1.getY()+p2.getY())/2;
        float fx = p2.getX();
        float fy = p2.getY();
        float cx = (p3.getX()+p2.getX())/2;
        float cy = (p3.getY()+p2.getY())/2;
        float dx, dy, ex, ey, bx, by;
//        dx = (1 - u) * ax + u * bx;
//        dy = (1 - u) * ay + u * by;
//
//        ex = (1 - u) * bx + u * cx;
//        ey = (1 - u) * by + u * cy;
//
//        fx = (1 - u) * dx + u * ex;
//        fy = (1 - u) * dy + u * ey;

        bx=(fx-(1-u)*(1-u)*ax-u*u*cx)/(2*u*(1-u));
        by=(fy-(1-u)*(1-u)*ay-u*u*cy)/(2*u*(1-u));

        Path path=new Path();
        path.moveTo(ax,ay);
        path.quadTo(bx,by,cx,cy);
        
        canvas.drawPath(path,paint);



    }

    /// <summary>
    /// Catmull-Rom 曲线插值
    /// </summary>
    /// <param name="p0"></param>
    /// <param name="p1"></param>
    /// <param name="p2"></param>
    /// <param name="p3"></param>
    /// <param name="t">0-1</param>
    /// <returns></returns>
//    Vector3 CatmullRomPoint(Vector3 P0, Vector3 P1, Vector3 P2, Vector3 P3, float t)
//    {
//        float factor = 0.5f;
//        Vector3 c0 = P1;
//        Vector3 c1 = (P2 - P0) * factor;
//        Vector3 c2 = (P2 - P1) * 3f - (P3 - P1) * factor - (P2 - P0) * 2f * factor;
//        Vector3 c3 = (P2 - P1) * -2f + (P3 - P1) * factor + (P2 - P0) * factor;
//
//        Vector3 curvePoint = c3 * t * t * t + c2 * t * t + c1 * t + c0;
//
//        return curvePoint;
//    }

    @Override
    public float getFoundNearRange() {
        return radius;
    }
}
