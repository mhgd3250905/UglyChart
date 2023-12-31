package com.bboyuglyk.chart_sdk;

import android.graphics.Canvas;

import java.util.List;

public interface IMarkerBuilder {
    /**
     * 构建marker
     * @param entries
     * @return 返回绘制marker的高度
     */
    float builderMarker(Canvas canvas,DataType dataType, List<Entry> entries, float hightLightPx, float markerHeight, ViewportInfo viewportInfo);

    /**
     * 构建marker
     * @param canvas
     * @param entry
     * @param touchPatch
     * @param markerHeight
     * @param viewportInfo
     * @return
     */
    float builderMarker(Canvas canvas, Entry entry, PXY touchPatch, float markerHeight, ViewportInfo viewportInfo);
}
