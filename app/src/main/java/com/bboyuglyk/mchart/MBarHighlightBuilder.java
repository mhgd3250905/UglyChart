package com.bboyuglyk.mchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.bboyuglyk.chart_sdk.HighlightBuilder;
import com.bboyuglyk.chart_sdk.ViewportInfo;

/**
 * 自定义HighLight Marker显示
 */
public class MBarHighlightBuilder extends HighlightBuilder {
    public MBarHighlightBuilder(Context context) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);//设置抗锯齿
        paint.setStyle(Paint.Style.STROKE);//实心
        paint.setStrokeWidth(80f);//线条粗细
        paint.setColor(ContextCompat.getColor(context,R.color.high_light_find_color));//线条粗细

        setPaint(paint);
    }

    @Override
    public void drawHighlight(Canvas canvas, float px, ViewportInfo viewportInfo) {
        super.drawHighlight(canvas, px, viewportInfo);
    }
}
