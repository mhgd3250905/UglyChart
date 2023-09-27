package com.bboyuglyk.mchart.dataset;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import androidx.annotation.ColorInt;

import com.bboyuglyk.chart_sdk.DataType;
import com.bboyuglyk.chart_sdk.Entry;
import com.bboyuglyk.chart_sdk.dataset.DataSet;

import java.util.LinkedList;

public class PumpNormalBolusDataSet extends DataSet {
    private float foundNearRange = 5f;
    private RectF rectF;
    private @ColorInt
    int[] colors;
    private float lastBottom;
    private float lastTop;
    private int i;

    public PumpNormalBolusDataSet(Context context, String tag, LinkedList<Entry> entries, int[] colors) {
        super(context,tag, DataType.bar,entries, entries);
        this.colors = colors;
        if (entries != null && entries.size() > 0) {
            if (entries.getFirst().getyValues() != null) {
                if (entries.getFirst().getyValues().length != colors.length) {
                    throw new IllegalArgumentException("you must ensure entries's yValues.length equals colors's length!");
                }
            }
        }
        init();
    }

    public PumpNormalBolusDataSet(Context context,String tag, LinkedList<Entry> entries) {
        super(context,tag, DataType.bar,entries, entries);
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
        rectF = new RectF(x - foundNearRange, y, x + foundNearRange, bottom);
        canvas.drawRect(rectF, paint);
    }

//    @Override
//    public void drawComposeRect(Canvas canvas, float left, float top, float right, float bottom, float x, float maxY, float... midYs) {
//        super.drawComposeRect(canvas, left, top, right, bottom, x, maxY, midYs);
//        if (midYs == null || midYs.length == 0) {
//            rectF = new RectF(x - foundNearRange, maxY, x + foundNearRange, bottom);
//            canvas.drawRect(rectF, paint);
//            return;
//        }
//
//        lastBottom = bottom;
//        lastTop = 0f;
//        for (i = 0; i < midYs.length; i++) {
//            paint.setColor(colors[i]);
//            if (i == 0) {
//                lastTop = midYs[i];
//            } else {
//                lastBottom = lastTop;
//                lastTop = midYs[i];
//            }
//            rectF = new RectF(x - foundNearRange, lastTop, x + foundNearRange, lastBottom);
//            canvas.drawRect(rectF, paint);
//        }
//    }

    public void setFoundNearRange(float foundNearRange) {
        this.foundNearRange = foundNearRange;
    }


    @Override
    public float getFoundNearRange() {
        return foundNearRange;
    }
}
