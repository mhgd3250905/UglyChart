package com.bboyuglyk.mchart.new_dataset;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import androidx.core.content.ContextCompat;

import com.bboyuglyk.chart_sdk.DataType;
import com.bboyuglyk.chart_sdk.Entry;
import com.bboyuglyk.chart_sdk.PXY;
import com.bboyuglyk.chart_sdk.ViewportInfo;
import com.bboyuglyk.chart_sdk.dataset.SingleDataSet;
import com.bboyuglyk.mchart.R;

import java.util.LinkedList;

public class LineDataSet extends SingleDataSet {
    private int lineColor=Color.BLACK;

    public LineDataSet(Context context, String tag, LinkedList<Entry> entries) {
        super(context, tag, DataType.DoublePoint, entries);
        init();
    }


    private void init() {
        setShowMarker(true);
        setEntryDrafter(this);

        Paint paint = new Paint();
        paint.setAntiAlias(true);//设置抗锯齿
        paint.setStyle(Paint.Style.STROKE);//实心
        paint.setStrokeWidth(4);//线条粗细
        paint.setColor(Color.BLUE);//线条粗细
        setPaint(paint);

        Paint highlightPaint = new Paint();
        highlightPaint.setAntiAlias(true);//设置抗锯齿
        highlightPaint.setStyle(Paint.Style.FILL);//实心
        highlightPaint.setStrokeWidth(0);//线条粗细
        highlightPaint.setColor(ContextCompat.getColor(context, R.color.high_light_shadow_color));//线条粗细
//        highlightPaint.setShadowLayer(5f,3f,3f,Color.YELLOW);
        setHighlightPaint(highlightPaint);
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
        paint.setColor(lineColor);
    }


    @Override
    public void drawDoubleEntry(Canvas canvas,int index, PXY p1, PXY p2, ViewportInfo viewportInfo) {
        Path path=new Path();
        path.moveTo(p1.x,p1.y);
        path.lineTo(p2.x,p2.y);
        canvas.drawPath(path,paint);
    }

    @Override
    public void drawHighlightEntry(Canvas canvas, int index, PXY p, ViewportInfo viewportInfo) {
        Path path=new Path();
        path.moveTo(p.x,p.y-10f);
        path.lineTo(p.x-20f,p.y-40f);
        path.lineTo(p.x+20f,p.y-40f);
        path.close();
        canvas.drawPath(path, highlightPaint);
        path=new Path();
        path.moveTo(p.x,p.y+10f);
        path.lineTo(p.x-20f,p.y+40f);
        path.lineTo(p.x+20f,p.y+40f);
        path.close();
        canvas.drawPath(path, highlightPaint);
    }


    @Override
    public float getFoundNearRange() {
        return 40f;
    }
}
