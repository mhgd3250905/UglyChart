package com.bboyuglyk.mchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.bboyuglyk.chart_sdk.DataType;
import com.bboyuglyk.chart_sdk.DpPxSpUtils;
import com.bboyuglyk.chart_sdk.Entry;
import com.bboyuglyk.chart_sdk.MarkerBuilder;
import com.bboyuglyk.chart_sdk.ViewportInfo;

import java.util.ArrayList;
import java.util.List;

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
    private float labelWidth;
    private float padding = 0f;

    public MMarkerBuilder(Context context) {
        this.context = context;

        padding = DpPxSpUtils.dp2px(context, 5);

        //notice 文字画笔
        labelPaint = new Paint();
        labelPaint.setAntiAlias(true);
        labelPaint.setStrokeWidth(5);
        labelPaint.setColor(Color.BLACK);
        labelPaint.setTextSize(DpPxSpUtils.dp2px(context, 15));
        //notice 文字画笔
        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setStrokeWidth(5);
        bgPaint.setColor(Color.YELLOW);
    }

    @Override
    public float builderMarker(Canvas canvas, DataType dataType, List<Entry> entries, float hightLightPx, float markerHeight, ViewportInfo viewportInfo) {
        labelTotalHeight = markerHeight;
        switch (dataType) {
            case SinglePoint:
                bgPaint.setColor(Color.YELLOW);
                break;
            case DoublePoint:
                bgPaint.setColor(Color.GRAY);
                break;
            case TriplePoint:
                break;
            case QuatraPoint:
                break;
            case CurveSingle:
                break;
            case CurveDouble:
                break;
        }
        labelWidth = 0f;
        for (int i = 0; i < entries.size(); i++) {
            Object data = entries.get(i).getData();
            if (data == null) continue;
            if (data instanceof String[]) {
                String[] strArr = (String[]) data;
                if (strArr.length == 0) continue;
                //计算最大宽度
                String maxLabel = strArr[0];
                for (String s : strArr) {
                    if (maxLabel.length() < s.length()) maxLabel = s;
                }

                labelStr = maxLabel;
                labelPaint.getTextBounds(labelStr, 0, labelStr.length(), labelRect);
                labelRectWidth = labelRect.width();
                labelRectHeight = labelRect.height();
                labelRect.set(
                        (int) hightLightPx,
                        (int) (viewportInfo.top + labelTotalHeight),
                        (int) (hightLightPx + labelRectWidth + 2 * padding),
                        (int) (viewportInfo.top + labelTotalHeight + labelRectHeight + 2 * padding)
                );
                labelWidth = labelRectWidth;

                for (int j = 0; j < strArr.length; j++) {
                    labelStr = strArr[j];
                    labelPaint.getTextBounds(labelStr, 0, labelStr.length(), labelRect);
                    labelRectWidth = labelRect.width();
                    labelRectHeight = labelRect.height();
                    labelRect.set(
                            (int) hightLightPx,
                            (int) (viewportInfo.top + labelTotalHeight),
                            (int) (hightLightPx + labelWidth + 2 * padding),
                            (int) (viewportInfo.top + labelTotalHeight + labelRectHeight + 2 * padding)
                    );
                    if (labelWidth < labelRectWidth) {
                        labelWidth = labelRectWidth;
                    }
                    canvas.drawRect(labelRect, bgPaint);
                    canvas.drawText(labelStr, hightLightPx + padding, viewportInfo.top + labelTotalHeight + labelRectHeight + padding, labelPaint);
                    labelTotalHeight += labelRect.height();
                }

            }
        }
        return labelTotalHeight;
    }
}
