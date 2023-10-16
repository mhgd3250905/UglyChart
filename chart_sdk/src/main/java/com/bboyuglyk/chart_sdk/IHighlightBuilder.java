package com.bboyuglyk.chart_sdk;

import android.graphics.Canvas;

public interface IHighlightBuilder {
    /**
     * 绘制Highlight触摸区域
     * @param canvas 画布
     * @param px 触摸x坐标
     * @param viewportInfo 图表信息
     */
    void drawHighlight(Canvas canvas,float px,ViewportInfo viewportInfo);

    /**
     * 绘制Highlight区域
     * @param canvas
     * @param touchP 触摸点
     * @param viewportInfo
     */
    void drawHighlight(Canvas canvas,PXY touchP,ViewportInfo viewportInfo);
}
