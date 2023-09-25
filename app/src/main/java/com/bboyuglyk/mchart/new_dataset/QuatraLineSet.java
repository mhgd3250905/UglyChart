package com.bboyuglyk.mchart.new_dataset;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

import com.bboyuglyk.chart_sdk.DataType;
import com.bboyuglyk.chart_sdk.Entry;
import com.bboyuglyk.chart_sdk.PXY;
import com.bboyuglyk.chart_sdk.ViewportInfo;
import com.bboyuglyk.chart_sdk.dataset.SingleDataSet;

import java.util.LinkedList;
import java.util.Vector;

public class QuatraLineSet extends SingleDataSet {
    private float radius = 5f;

    public QuatraLineSet(Context context, String tag, LinkedList<Entry> entries) {
        super(context, tag, DataType.QuatraPoint, entries);

        init();
    }


    private void init() {
        setEntryDrafter(this);
        Paint paint = new Paint();
        paint.setAntiAlias(true);//设置抗锯齿
        paint.setStyle(Paint.Style.FILL);//实心
        paint.setStrokeWidth(2);//线条粗细
        paint.setColor(Color.GRAY);//线条粗细
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
    public void drawQuatraEntry(Canvas canvas, PXY p1, PXY p2, PXY p3, PXY p4, ViewportInfo viewportInfo) {
        super.drawQuatraEntry(canvas, p1, p2, p3, p4, viewportInfo);

        canvas.drawCircle(p2.x, p2.y, radius, paint);

        for (int i = 0; i <100; i++) {
            PXY pxy = QuatraLineSet.CatmullRomPoint(p1, p2, p3, p4, (i * 1.0f) / 100);
            canvas.drawCircle(pxy.x, pxy.y, radius, paint);
        }
        canvas.drawCircle(p3.x, p3.y, radius, paint);

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
    public static PXY CatmullRomPoint(PXY P0, PXY P1, PXY P2, PXY P3, float t) {
        float factor = 0.5f;
        PXY c0 = P1;
        PXY c1 = P2.subduct(P0).multiply(factor);
        PXY c2 = (P2.subduct(P1).multiply(3f))
                .subduct(P3.subduct(P1).multiply(factor))
                .subduct(P2.subduct(P0).multiply(2f * factor));
        PXY c3 = (P2.subduct(P1).multiply(-2f))
                .add(P3.subduct(P1).multiply(factor))
                .add(P2.subduct(P0).multiply(factor));

        PXY curvePoint = c3.multiply(t * t * t)
                .add(c2.multiply(t * t))
                .add(c1.multiply(t))
                .add(c0);
        return curvePoint;
    }

    @Override
    public float getFoundNearRange() {
        return radius;
    }
}
