package com.bboyuglyk.mchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.bboyuglyk.chart_sdk.DpPxSpUtils;
import com.bboyuglyk.chart_sdk.Entry;
import com.bboyuglyk.chart_sdk.MarkerBuilder;
import com.bboyuglyk.chart_sdk.ViewportInfo;

/**
 * 自定义HighLight Marker显示
 */
public class MMarkerBuilder extends MarkerBuilder {

    private final Paint labelPaint;
    private final Paint bgPaint;
    private Context context;
    private Rect labelRect = new Rect();
    private String labelStr;
    private int labelRectHeight;
    private int labelRectWidth;
    private float labelTotalHeight;
    private float padding=0f;

    public MMarkerBuilder(Context context) {
        this.context = context;

        padding=DpPxSpUtils.dp2px(context, 5);

        //notice 文字画笔
        labelPaint = new Paint();
        labelPaint.setAntiAlias(true);
        labelPaint.setStrokeWidth(5);
        labelPaint.setColor(Color.BLACK);
        labelPaint.setTextSize(DpPxSpUtils.dp2px(context, 25));
        //notice 文字画笔
        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setStrokeWidth(5);
        bgPaint.setColor(Color.YELLOW);
    }

    @Override
    public float builderMarker(Canvas canvas, Entry entry, float hightLightPx, float markerHeight, ViewportInfo viewportInfo) {
        Object data = entry.getData();
        if (data == null) return 0f;
        labelTotalHeight = markerHeight;
        if (data instanceof String[]) {
            String[] strArr = (String[]) entry.getData();
            for (int i = 0; i < strArr.length; i++) {
                labelStr = strArr[i];
                labelPaint.getTextBounds(labelStr, 0, labelStr.length(), labelRect);
                labelRectWidth = labelRect.width();
                labelRectHeight = labelRect.height();
                labelRect.set(
                        (int) hightLightPx,
                        (int) (viewportInfo.top+labelTotalHeight),
                        (int) (hightLightPx + labelRectWidth+2*padding),
                        (int) (viewportInfo.top+labelTotalHeight + labelRectHeight+2*padding)
                );
                canvas.drawRect(labelRect, bgPaint);
                canvas.drawText(labelStr, hightLightPx+padding, viewportInfo.top+labelTotalHeight+labelRectHeight+padding, labelPaint);
                labelTotalHeight += labelRect.height();
            }

        }
        return labelTotalHeight;
    }
}
