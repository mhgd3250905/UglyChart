package com.bboyuglyk.chart_sdk;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.List;

public class HighlightBuilder implements IHighlightBuilder{

    protected Paint paint;

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public HighlightBuilder() {
        paint = new Paint();
        paint.setAntiAlias(true);//设置抗锯齿
        paint.setStyle(Paint.Style.STROKE);//实心
        paint.setStrokeWidth(3);//线条粗细
        paint.setColor(Color.BLACK);//线条粗细
    }

    @Override
    public void drawHighlight(Canvas canvas, float px, ViewportInfo viewportInfo) {
        canvas.drawLine(px, viewportInfo.bottom, px, viewportInfo.top, paint);
    }

    @Override
    public void drawHighlight(Canvas canvas, PXY touchP, ViewportInfo viewportInfo) {
        if(touchP==null){
            return;
        }
        float left = viewportInfo.left;
        float right = viewportInfo.right;
        float top = viewportInfo.top;
        float bottom = viewportInfo.bottom;
        float centerX=left+(right-left)/2;
        float centerY=top+(bottom-top)/2;
        canvas.drawLine(centerX, centerY, touchP.x, touchP.y, paint);
    }
}
