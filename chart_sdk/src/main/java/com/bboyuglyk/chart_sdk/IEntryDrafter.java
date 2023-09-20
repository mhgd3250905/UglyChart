package com.bboyuglyk.chart_sdk;

import android.graphics.Canvas;

public interface IEntryDrafter {
    void drawPointEntry(Canvas canvas, float x, float y, float left, float top, float right, float bottom);

    void drawRectEntry(Canvas canvas, float rLeft, float rTop, float rRight, float rBottom, float left, float top, float right, float bottom);

    void drawLineEntry(Canvas canvas, float fromX, float fromY, float toX, float toY, float left, float top, float right, float bottom);

    void drawComposeRect(Canvas canvas, float left, float top, float right, float bottom, float x, float maxY, float... midY);

    void drawRangeBar(Canvas canvas, float x, float y, float left, float top, float right, float bottom,float range);
}
