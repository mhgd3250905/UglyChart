package com.bboyuglyk.mchart.dataset;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import com.bboyuglyk.chart_sdk.DataType;
import com.bboyuglyk.chart_sdk.Entry;
import com.bboyuglyk.chart_sdk.dataset.DataSet;

import java.util.LinkedList;

public class EventSgDataSet extends DataSet {
    private Context context;
    private Bitmap bitmap;

    public EventSgDataSet(Context context, String tag, LinkedList<Entry> entries) {
        super(context,tag, DataType.bitmap,entries, entries);
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
            bitmap = optimizationBitmapByInSampleSize(com.bboyuglyk.chart_sdk.R.drawable.icon_sg_border, 45, 45);

        setShowMarker(false);
    }



    @Override
    public void drawPointEntry(Canvas canvas, float x, float y, float left, float top, float right, float bottom) {
        canvas.drawBitmap(bitmap, x - 15, y - 15, paint);
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

    //设置bitmap图片为主题配色
    public Bitmap setBitmapTintColor(int colorTint, @Nullable Bitmap bitmap) {
        if (bitmap != null && colorTint != 0) {
            //创建一个与原图一样大小的空图片
            Bitmap newBmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());

            //创建一个新的bitmap在上面绘制出指定的颜色的配色，mode使用默认值
            ColorFilter filter = new PorterDuffColorFilter(colorTint, PorterDuff.Mode.SRC_IN);
            //创建画笔及设置过滤器
            Paint paint = new Paint();
            paint.setColorFilter(filter);
            //创建canvas并绘制出图片
            Canvas canvas = new Canvas(newBmp);
            canvas.drawBitmap(bitmap, 0, 0, paint);

            return newBmp;
        } else {
            return null;
        }
    }

}


