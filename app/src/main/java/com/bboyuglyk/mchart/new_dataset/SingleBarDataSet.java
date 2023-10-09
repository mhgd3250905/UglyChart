package com.bboyuglyk.mchart.new_dataset;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.bboyuglyk.chart_sdk.DataType;
import com.bboyuglyk.chart_sdk.Entry;
import com.bboyuglyk.chart_sdk.PXY;
import com.bboyuglyk.chart_sdk.ViewportInfo;
import com.bboyuglyk.chart_sdk.dataset.SingleDataSet;
import com.bboyuglyk.mchart.R;

import java.util.LinkedList;

public class SingleBarDataSet extends SingleDataSet {
    private float radius = 5f;

    public SingleBarDataSet(Context context, String tag, LinkedList<Entry> entries) {
        super(context, tag, DataType.SinglePoint, entries);
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
    }


    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public void drawSingleEntry(Canvas canvas, int index, PXY p, ViewportInfo viewportInfo) {
        Rect rect=new Rect((int) (p.x-10), (int) p.y, (int) (p.x+10), (int) viewportInfo.bottom);
//        canvas.drawRect(rect,paint);
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.shape_bar);
        drawable.setBounds(rect);
        drawable.draw(canvas);

    }


    @Override
    public float getFoundNearRange() {
        return 40f;
    }
}
