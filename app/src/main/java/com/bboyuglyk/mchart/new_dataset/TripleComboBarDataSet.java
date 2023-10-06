package com.bboyuglyk.mchart.new_dataset;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.bboyuglyk.chart_sdk.DataType;
import com.bboyuglyk.chart_sdk.Entry;
import com.bboyuglyk.chart_sdk.PXY;
import com.bboyuglyk.chart_sdk.ViewportInfo;
import com.bboyuglyk.chart_sdk.dataset.SingleDataSet;
import com.bboyuglyk.mchart.R;

import java.util.LinkedList;

public class TripleComboBarDataSet extends SingleDataSet {
    private float radius = 5f;
    private Drawable drawable1;
    private Drawable drawable2;
    private Drawable drawable3;

    public TripleComboBarDataSet(Context context, String tag, LinkedList<Entry> entries) {
        super(context, tag, DataType.TriplePoint, entries);
        init();
    }


    private void init() {
        setEntryDrafter(this);
        Paint paint = new Paint();
        paint.setAntiAlias(true);//设置抗锯齿
        paint.setStyle(Paint.Style.FILL);//实心
        paint.setStrokeWidth(4);//线条粗细
        paint.setColor(ContextCompat.getColor(context, R.color.teal_200));//线条粗细
        setPaint(paint);
        setShowMarker(true);


        drawable1 = ContextCompat.getDrawable(context, R.drawable.shape_triple_bar_1);
        drawable2 = ContextCompat.getDrawable(context, R.drawable.shape_triple_bar_2);
        drawable3 = ContextCompat.getDrawable(context, R.drawable.shape_triple_bar_3);
    }


    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public void drawTripleEntry(Canvas canvas, int index, PXY p1, PXY p2, PXY p3, ViewportInfo viewportInfo) {
        float y1 = p1.y;
        float y2 = p2.y;
        float y3 = p3.y;
        float total = y1 + y2 + y3;
        float height = viewportInfo.bottom - viewportInfo.top;
        float p1Height = height * y1 / total;
        float p2Height = height * y2 / total;
        float p3Height = height - p1Height - p2Height;


        float y1Bottom = viewportInfo.bottom;
        float y1Top = viewportInfo.bottom - p1Height;
        float y2Bottom = y1Top;
        float y2Top = y1Top - p2Height;
        float y3Bottom = y2Top;
        float y3Top = viewportInfo.top;


        if (index % 3 == 0) {
            Rect rect1 = new Rect((int) (p2.x - 10), (int) y1Top, (int) (p2.x + 10), (int) y1Bottom);
            drawable1.setBounds(rect1);
            drawable1.draw(canvas);
            Rect rect2 = new Rect((int) (p2.x - 10), (int) y2Top, (int) (p2.x + 10), (int) y2Bottom);
            drawable2.setBounds(rect2);
            drawable2.draw(canvas);
            Rect rect3 = new Rect((int) (p2.x - 10), (int) y3Top, (int) (p2.x + 10), (int) y3Bottom);
            drawable3.setBounds(rect3);
            drawable3.draw(canvas);
        }
    }

    @Override
    public float getFoundNearRange() {
        return radius;
    }
}
