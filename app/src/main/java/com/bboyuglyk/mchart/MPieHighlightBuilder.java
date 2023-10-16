package com.bboyuglyk.mchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.bboyuglyk.chart_sdk.HighlightBuilder;
import com.bboyuglyk.chart_sdk.PXY;
import com.bboyuglyk.chart_sdk.ViewportInfo;

/**
 * 自定义HighLight Marker显示
 */
public class MPieHighlightBuilder extends HighlightBuilder {
    Paint paint1 = new Paint();
    Paint paint2 = new Paint();
    public MPieHighlightBuilder(Context context) {
        paint1.setAntiAlias(true);//设置抗锯齿
        paint1.setStyle(Paint.Style.FILL);//实心
        paint1.setStrokeWidth(20f);//线条粗细
        paint1.setColor(Color.BLACK);//线条粗细
        paint2.setAntiAlias(true);//设置抗锯齿
        paint2.setStyle(Paint.Style.FILL);//实心
        paint2.setStrokeWidth(20f);//线条粗细
        paint2.setColor(Color.WHITE);//线条粗细

    }


    @Override
    public void drawHighlight(Canvas canvas, PXY touchP, ViewportInfo viewportInfo) {
//        super.drawHighlight(canvas, touchP, viewportInfo);
        if(touchP==null) return;
        canvas.drawCircle(touchP.x,touchP.y,40f,paint2);
        canvas.drawCircle(touchP.x,touchP.y,20f,paint1);
    }
}
