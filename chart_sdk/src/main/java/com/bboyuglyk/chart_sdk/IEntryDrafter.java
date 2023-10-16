package com.bboyuglyk.chart_sdk;

import android.graphics.Canvas;

public interface IEntryDrafter {

    /**
     * 绘制单个数据点
     * @param canvas 画布
     * @param index 序号
     * @param p 数据坐标
     * @param viewportInfo 图表参数
     */
    void drawSingleEntry(Canvas canvas, int index, PXY p, ViewportInfo viewportInfo);

    /**
     * 绘制2个数据点
     * @param canvas
     * @param index 第一个数据序号
     * @param p1
     * @param p2
     * @param viewportInfo
     */
    void drawDoubleEntry(Canvas canvas, int index, PXY p1, PXY p2, ViewportInfo viewportInfo);

    void drawTripleEntry(Canvas canvas, int index, PXY p1, PXY p2, PXY p3, ViewportInfo viewportInfo);

    void drawQuatraEntry(Canvas canvas, int index, PXY p1, PXY p2, PXY p3, PXY p4, ViewportInfo viewportInfo);

    /**
     * 绘制饼图
     * @param canvas
     * @param viewportInfo
     * @param entries 饼图数据
     */
    void drawPieEntries(Canvas canvas, ViewportInfo viewportInfo, PXY touchP, Entry ...entries);

    /**
     * 绘制数据被选中时的样式
     * @param canvas
     * @param index 数据序号
     * @param p 坐标
     * @param viewportInfo 图表信息
     */
    void drawHighlightEntry(Canvas canvas, int index, PXY p, ViewportInfo viewportInfo);


}
