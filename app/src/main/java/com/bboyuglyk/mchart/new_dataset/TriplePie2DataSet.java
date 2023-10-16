package com.bboyuglyk.mchart.new_dataset;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.bboyuglyk.chart_sdk.DataType;
import com.bboyuglyk.chart_sdk.DpPxSpUtils;
import com.bboyuglyk.chart_sdk.Entry;
import com.bboyuglyk.chart_sdk.PXY;
import com.bboyuglyk.chart_sdk.ViewportInfo;
import com.bboyuglyk.chart_sdk.dataset.SingleDataSet;
import com.bboyuglyk.mchart.R;

import java.util.LinkedList;
import java.util.Random;

public class TriplePie2DataSet extends SingleDataSet {
    private static final String TAG = "TriplePieDataSet";
    private float radius = 5f;
    private Random random = new Random();
    private Rect labelRect = new Rect();
    private String labelStr;
    private int labelRectHeight;
    private int labelRectWidth;
    private float labelTotalHeight;
    private float labelWidth;
    private float labelHeight;
    private Paint labelPaint;
    private Drawable markerBg;

    public TriplePie2DataSet(Context context, String tag, LinkedList<Entry> entries) {
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


        //notice 文字画笔
        labelPaint = new Paint();
        labelPaint.setAntiAlias(true);
        labelPaint.setStrokeWidth(5);
        labelPaint.setColor(Color.BLACK);
        labelPaint.setTextSize(DpPxSpUtils.dp2px(context, 15));

        markerBg = ContextCompat.getDrawable(context, R.drawable.shape_marker_bg);
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


        float padding = 20;
        Entry findEntry = drawArcs(canvas, touchP, centerX, centerY, padding, radius - padding, entries);

        if (findEntry != null) {
            //判断选中：可自行绘制Marker
            drawMarker(canvas, findEntry, touchP);
        }

//        paint.setColor(Color.WHITE);
//        canvas.drawLine(centerX, top, centerX, bottom, paint);
//        canvas.drawLine(left, centerY, right, centerY, paint);
//        paint.setStyle(Paint.Style.STROKE);
//        canvas.drawCircle(centerX, centerY, padding, paint);

    }

    /**
     * 绘制扇形，并且寻找出highlight Entry
     *
     * @param canvas
     * @param touchP
     * @param centerX
     * @param centerY
     * @param padding
     * @param radius
     * @param entries
     * @return
     */
    private Entry drawArcs(Canvas canvas, PXY touchP, float centerX, float centerY, float padding, float radius, Entry[] entries) {

//        canvas.translate(padding,padding);
        Entry findEntry = null;

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

//            {x=463.0, y=-8.0
//            touchP = new PXY(253.0f, 168f);

            padding = 0;
            if (touchP != null) {
                double touchAnglePi = Math.atan(Math.abs(centerY - touchP.y) / Math.abs(centerX - touchP.x));
                double touchAngle = 180 * touchAnglePi / Math.PI;
                if (touchP.x > centerX) {
                    if (touchP.y < centerY) {
                        touchAngle = 360 - touchAngle;
                    }
                } else {
                    if (touchP.y < centerY) {
                        touchAngle = 180 + touchAngle;
                    } else {
                        touchAngle = 180 - touchAngle;
                    }
                }
                if (touchAngle > startAngle && touchAngle < startAngle + sweepAngle) {
                    padding = 20;
                    findEntry = entry;
                }
            }

            float anglePiPer = (startCalcuatorAngle + sweepAngle / 2) / 180;
            /*
             * x1   =   x0   +   r   *   cos( a )
             * y1   =   y0   +   r   *   sin( a )
             * */
            float xPadding = (float) (padding * Math.cos(anglePiPer * Math.PI));
            float yPadding = (float) (padding * Math.sin(anglePiPer * Math.PI));
            float px = centerX + xPadding;
            float py = centerY + yPadding;

            paint.setColor(Color.argb(255, (100 + i * 50) % 255, (150 + i * 50) % 255, (200 + i * 50) % 255));

            canvas.translate(xPadding, yPadding);


            canvas.drawArc(rectF, startAngle, sweepAngle, true, paint);

//            paint.setColor(Color.WHITE);

//            canvas.drawCircle(px, py, 10f, paint);

            canvas.translate(-1 * xPadding, -1 * yPadding);

            startAngle += sweepAngle;
            startCalcuatorAngle += sweepAngle;

        }

        return findEntry;
//        canvas.translate(-1*padding,-1*padding);
    }

    private void drawMarker(Canvas canvas, Entry entry, PXY touchP) {
        float padding = DpPxSpUtils.dp2px(context, 5);
        labelTotalHeight = padding;
        Object data = entry.getData();
        if (data == null) return;
        if (data instanceof String[]) {
            String[] strArr = (String[]) data;
            if (strArr.length == 0) return;

            //step 计算最大宽度
            String maxLabel = strArr[0];
            for (String s : strArr) {
                if (maxLabel.length() < s.length()) maxLabel = s;
            }
            labelStr = maxLabel;
            labelPaint.getTextBounds(labelStr, 0, labelStr.length(), labelRect);
            labelRectWidth = labelRect.width();
            labelRectHeight = labelRect.height();
            labelWidth = labelRectWidth;
            labelHeight = (labelRectHeight + padding) * strArr.length + padding;

            labelRect.set(
                    (int) (20f + touchP.x),
                    (int) (touchP.y - labelHeight / 2),
                    (int) (20f + touchP.x + labelWidth + 2 * padding),
                    (int) (touchP.y + labelHeight / 2)
            );

            markerBg.setBounds(labelRect);
            markerBg.draw(canvas);

//                canvas.drawRect(labelRect, bgPaint);

            for (int j = 0; j < strArr.length; j++) {
                labelStr = strArr[j];
//                    labelPaint.getTextBounds(labelStr, 0, labelStr.length(), labelRect);
                canvas.drawText(labelStr,
                        20f + touchP.x + padding,
                        labelRect.top + labelTotalHeight + labelRectHeight,
                        labelPaint);
                labelTotalHeight += labelRectHeight + padding;
            }

        }
    }


    @Override
    public float getFoundNearRange() {
        return radius;
    }
}
