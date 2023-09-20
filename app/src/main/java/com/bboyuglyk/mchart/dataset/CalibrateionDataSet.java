package com.bboyuglyk.mchart.dataset;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.annotation.DrawableRes;

import com.bboyuglyk.chart_sdk.DataType;
import com.bboyuglyk.chart_sdk.Entry;
import com.bboyuglyk.chart_sdk.dataset.DataSet;

import java.util.LinkedList;

public class CalibrateionDataSet extends DataSet {
    private Context context;
    private Bitmap bitmap;

    public CalibrateionDataSet(Context context, String tag, LinkedList<Entry> entries) {
        super(context,tag, DataType.bitmap, entries);
        this.context = context;
        init();
    }

    private void init() {
        setEntryDrafter(this);
        Paint paint = new Paint();
        paint.setAntiAlias(true);//设置抗锯齿
        paint.setStyle(Paint.Style.FILL);//实心
        paint.setStrokeWidth(2);//线条粗细
        paint.setColor(Color.BLACK);//线条粗细
        setPaint(paint);
        bitmap = optimizationBitmapByInSampleSize(com.bboyuglyk.chart_sdk.R.drawable.icon_sg_fill, 45, 45);
        setShowMarker(true);
    }




    @Override
    public void drawPointEntry(Canvas canvas, float x, float y, float left, float top, float right, float bottom) {

        canvas.drawBitmap(bitmap, x-15, y-20, paint);
    }

    /**
     * 通过调整options.inSampleSize来修改大小
     */
    private Bitmap optimizationBitmapByInSampleSize(@DrawableRes int bitmapRes, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), bitmapRes, options);
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inMutable = true;
        options.inJustDecodeBounds = false;
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        int inSampleSize = 1;
        if (srcHeight > height || srcWidth > width) {
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round(srcHeight / height);
            } else {
                inSampleSize = Math.round(srcWidth / width);
            }
        }
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeResource(context.getResources(), bitmapRes, options);
    }

    @Override
    public float getFoundNearRange() {
        return 100f;
    }
}
