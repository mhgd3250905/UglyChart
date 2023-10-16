package com.bboyuglyk.mchart.new_dataset;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.bboyuglyk.chart_sdk.DataType;
import com.bboyuglyk.chart_sdk.Entry;
import com.bboyuglyk.chart_sdk.PXY;
import com.bboyuglyk.chart_sdk.ViewportInfo;
import com.bboyuglyk.chart_sdk.dataset.SingleDataSet;
import com.bboyuglyk.mchart.R;

import java.util.LinkedList;
import java.util.Random;

public class TriplePieDataSet extends SingleDataSet {
    private static final String TAG = "TriplePieDataSet";
    private float radius = 5f;
    private Random random = new Random();

    public TriplePieDataSet(Context context, String tag, LinkedList<Entry> entries) {
        super(context, tag, DataType.Pie, entries);
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
    public void drawPieEntries(Canvas canvas, ViewportInfo viewportInfo, PXY touchP, Entry... entries) {

        float left = viewportInfo.getLeft();
        float right = viewportInfo.getRight();
        float top = viewportInfo.getTop();
        float bottom = viewportInfo.getBottom();
        float width = right - left;
        float height = bottom - top;

        float centerX = left + width / 2;
        float centerY = top + height / 2;
        float radius = Math.min(width, height) / 2;


        paint.setColor(ContextCompat.getColor(context, R.color.teal_200));


        float padding = 10;
        drawArcs(canvas, centerX, centerY, padding, radius-padding, entries);

//        paint.setColor(Color.WHITE);
//        canvas.drawLine(centerX, top, centerX, bottom, paint);
//        canvas.drawLine(left, centerY, right, centerY, paint);
//        paint.setStyle(Paint.Style.STROKE);
//        canvas.drawCircle(centerX, centerY, padding, paint);

    }

    private void drawArcs(Canvas canvas, float centerX, float centerY, float padding, float radius, Entry[] entries) {

//        canvas.translate(padding,padding);

        //圆Rect
        RectF rectF = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);

        float ySum = 0f;
        for (Entry entry : entries) {
            ySum += entry.getY();
        }

        float startAngle = 0;
        float startCalcuatorAngle = 0;
        float sweepAngle = 0;
        //遍历绘制
        for (int i = 0; i < entries.length; i++) {
            Entry entry = entries[i];

            float anglePer = entry.getY() / ySum;
            sweepAngle = 360 * anglePer;

            float anglePiPer = (startCalcuatorAngle + sweepAngle / 2) / 180;
            /*
             * x1   =   x0   +   r   *   cos( a )
             * y1   =   y0   +   r   *   sin( a )
             * */
            float xPadding = (float) (padding * Math.cos(anglePiPer * Math.PI));
            float yPadding = (float) (padding * Math.sin(anglePiPer * Math.PI));
            float px = centerX + xPadding;
            float py = centerY + yPadding;

            paint.setColor(Color.argb(255,random.nextInt(255),random.nextInt(255),random.nextInt(255)));

            canvas.translate(xPadding,yPadding);

            canvas.drawArc(rectF, startAngle, sweepAngle, true, paint);

            paint.setColor(Color.WHITE);

            canvas.drawCircle(px, py, 10f, paint);

            canvas.translate(-1*xPadding,-1*yPadding);

            startAngle += sweepAngle;
            startCalcuatorAngle += sweepAngle;

        }

//        canvas.translate(-1*padding,-1*padding);
    }


    @Override
    public float getFoundNearRange() {
        return radius;
    }
}
