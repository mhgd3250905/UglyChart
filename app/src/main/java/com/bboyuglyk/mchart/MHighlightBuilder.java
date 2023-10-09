package com.bboyuglyk.mchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.core.content.ContextCompat;

import com.bboyuglyk.chart_sdk.DataType;
import com.bboyuglyk.chart_sdk.DpPxSpUtils;
import com.bboyuglyk.chart_sdk.Entry;
import com.bboyuglyk.chart_sdk.HighlightBuilder;
import com.bboyuglyk.chart_sdk.MarkerBuilder;
import com.bboyuglyk.chart_sdk.ViewportInfo;

import java.util.List;

/**
 * 自定义HighLight Marker显示
 */
public class MHighlightBuilder extends HighlightBuilder {
    public MHighlightBuilder(Context context) {
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
