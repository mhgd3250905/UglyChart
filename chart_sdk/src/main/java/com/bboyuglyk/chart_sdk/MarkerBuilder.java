package com.bboyuglyk.chart_sdk;

import android.graphics.Canvas;

import java.util.List;

public class MarkerBuilder implements IMarkerBuilder{
    @Override
    public float builderMarker(Canvas canvas,DataType dataType, List<Entry> entries, float hightLightPx, float markerHeight, ViewportInfo viewportInfo) {
        return 0f;
    }
    @Override
    public float builderMarker(Canvas canvas, Entry entries, PXY touchPatch, float markerHeight, ViewportInfo viewportInfo) {
        return 0f;
    }
}
