package com.bboyuglyk.mchart.dataset;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;

import androidx.core.content.ContextCompat;

import com.bboyuglyk.chart_sdk.DataType;
import com.bboyuglyk.chart_sdk.Entry;
import com.bboyuglyk.chart_sdk.dataset.DataSet;

import java.util.LinkedList;

public class PumpActiveDataSet extends DataSet {
    private static final String TAG = "NewSensorDataSet";
    public PumpActiveDataSet(Context context, String tag, LinkedList<Entry> entries) {
        super(context,tag, DataType.point,entries, entries);
        init();
    }

    private void init() {
        setEntryDrafter(this);
        Paint paint = new Paint();
        paint.setAntiAlias(true);//设置抗锯齿
        paint.setStyle(Paint.Style.FILL);//实心
        paint.setStrokeWidth(2);//线条粗细
        paint.setColor(ContextCompat.getColor(context, com.bboyuglyk.chart_sdk.R.color.color_chart_new));//线条粗细
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        setPaint(paint);

        setShowMarker(true);
    }


    @Override
    public void drawPointEntry(Canvas canvas, float x, float y, float left, float top, float right, float bottom) {
//        Log.d(TAG, "drawPointEntry() called with: canvas = [" + canvas + "], x = [" + x + "], y = [" + y + "], left = [" + left + "], top = [" + top + "], right = [" + right + "], bottom = [" + bottom + "]");
        RectF rect = new RectF(x - 15, y - 20, x + 15, y);
        canvas.drawRect(rect, paint);
    }

    @Override
    public float getFoundNearRange() {
        return 20f;
    }
}
