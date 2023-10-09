package com.bboyuglyk.mchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.bboyuglyk.chart_sdk.DataType;
import com.bboyuglyk.chart_sdk.DpPxSpUtils;
import com.bboyuglyk.chart_sdk.Entry;
import com.bboyuglyk.chart_sdk.MarkerBuilder;
import com.bboyuglyk.chart_sdk.ViewportInfo;

import java.util.List;

/**
 * 自定义HighLight Marker显示
 */
public class MBarMarkerBuilder extends MarkerBuilder {

    private final Paint labelPaint;
    private final Paint bgPaint;
    private final Drawable markerBg;
    private Context context;
    private Rect labelRect = new Rect();
    private String labelStr;
    private int labelRectHeight;
    private int labelRectWidth;
    private float labelTotalHeight;
    private float labelWidth;
    private float labelHeight;
    private float padding = 0f;

    public MBarMarkerBuilder(Context context) {
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
        bgPaint.setColor(Color.WHITE);


        markerBg = ContextCompat.getDrawable(context, R.drawable.shape_marker_bg);
    }

    @Override
    public float builderMarker(Canvas canvas, DataType dataType, List<Entry> entries, float hightLightPx, float markerHeight, ViewportInfo viewportInfo) {

        switch (dataType) {
            case SinglePoint:
                bgPaint.setColor(Color.WHITE);
                break;
            case DoublePoint:
                bgPaint.setColor(Color.WHITE);
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
            labelTotalHeight = padding;
            Object data = entries.get(i).getData();
            if (data == null) continue;
            if (data instanceof String[]) {
                String[] strArr = (String[]) data;
                if (strArr.length == 0) continue;

                //step 计算最大宽度
                String maxLabel = strArr[0];
                for (String s : strArr) {
                    if (maxLabel.length() < s.length()) maxLabel = s;
                }
                labelStr = maxLabel;
                labelPaint.getTextBounds(labelStr, 0, labelStr.length(), labelRect);
                labelRectWidth = labelRect.width();
                labelRectHeight = labelRect.height();
                labelWidth = labelRectWidth;
                labelHeight = (labelRectHeight + padding) * strArr.length + padding;

                labelRect.set(
                        (int) (20f + entries.get(i).getPx()),
                        (int) (entries.get(i).getPy() - labelHeight / 2 ),
                        (int) (20f + entries.get(i).getPx() + labelWidth + 2 * padding),
                        (int) (entries.get(i).getPy() + labelHeight / 2)
                );

                markerBg.setBounds(labelRect);
                markerBg.draw(canvas);

//                canvas.drawRect(labelRect, bgPaint);

                for (int j = 0; j < strArr.length; j++) {
                    labelStr = strArr[j];
//                    labelPaint.getTextBounds(labelStr, 0, labelStr.length(), labelRect);
                    canvas.drawText(labelStr,
                            20f + entries.get(i).getPx()+padding,
                            labelRect.top + labelTotalHeight + labelRectHeight,
                            labelPaint);
                    labelTotalHeight += labelRectHeight + padding;
                }

            }
        }
        return labelTotalHeight;
    }
}
