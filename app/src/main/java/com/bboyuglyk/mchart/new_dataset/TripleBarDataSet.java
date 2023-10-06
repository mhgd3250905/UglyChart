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

public class TripleBarDataSet extends SingleDataSet {
    private float radius = 5f;
    private Drawable drawable1;
    private Drawable drawable2;
    private Drawable drawable3;

    public TripleBarDataSet(Context context, String tag, LinkedList<Entry> entries) {
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


        if (index % 3 == 0) {
            Rect rect1 = new Rect((int) (p1.x - 10), (int) y1, (int) (p1.x + 10), (int) viewportInfo.bottom);
            drawable1.setBounds(rect1);
            drawable1.draw(canvas);
            Rect rect2 = new Rect((int) (p2.x - 10), (int) y2, (int) (p2.x + 10), (int) viewportInfo.bottom);
            drawable2.setBounds(rect2);
            drawable2.draw(canvas);
            Rect rect3 = new Rect((int) (p3.x - 10), (int) y3, (int) (p3.x + 10), (int) viewportInfo.bottom);
            drawable3.setBounds(rect3);
            drawable3.draw(canvas);
        }
    }

    @Override
    public float getFoundNearRange() {
        return radius;
    }
}
