package com.bboyuglyk.mchart.dataset;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bboyuglyk.chart_sdk.DataType;
import com.bboyuglyk.chart_sdk.Entry;
import com.bboyuglyk.chart_sdk.dataset.DataSet;

import java.util.LinkedList;

public class NormalBolusDataSet extends DataSet {
    private float foundNearRange = 10f;

    public NormalBolusDataSet(Context context, String tag, LinkedList<Entry> entries) {
        super(context,tag, DataType.bar, entries);
        init();
    }

    private void init() {
        setEntryDrafter(this);
        Paint paint = new Paint();
        paint.setAntiAlias(true);//设置抗锯齿
        paint.setStyle(Paint.Style.FILL);//实心
        paint.setStrokeWidth(2);//线条粗细
        paint.setColor(Color.argb(150, 0, 188, 255));//线条粗细
        setPaint(paint);
        setShowMarker(true);
    }

    @Override
    public void drawPointEntry(Canvas canvas, float x, float y, float left, float top, float right, float bottom) {
        RectF rectF = new RectF(x - foundNearRange, y, x + foundNearRange, bottom);
        canvas.drawRect(rectF, paint);
    }


    public void setFoundNearRange(float foundNearRange) {
        this.foundNearRange = foundNearRange;
    }


    @Override
    public float getFoundNearRange() {
        return foundNearRange;
    }
}
