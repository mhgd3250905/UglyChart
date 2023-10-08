package com.bboyuglyk.chart_sdk;

import android.graphics.Canvas;

public interface IMarkerBuilder {
    /**
     * 构建marker
     * @param entry
     * @return 返回绘制marker的高度
     */
    float builderMarker(Canvas canvas,Entry entry,float hightLightPx, float markerHeight, ViewportInfo viewportInfo);
}
