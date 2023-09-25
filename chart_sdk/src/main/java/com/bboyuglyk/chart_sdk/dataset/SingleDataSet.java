package com.bboyuglyk.chart_sdk.dataset;

import android.content.Context;
import android.graphics.Canvas;

import com.bboyuglyk.chart_sdk.CurveUtils;
import com.bboyuglyk.chart_sdk.DataType;
import com.bboyuglyk.chart_sdk.Entry;
import com.bboyuglyk.chart_sdk.PXY;
import com.bboyuglyk.chart_sdk.ViewportInfo;

import java.util.LinkedList;

public class SingleDataSet extends DataSet {

    public SingleDataSet(Context context) {
        super(context);
    }

    public SingleDataSet(Context context, String tag, DataType type, LinkedList<Entry> entries) {
        super(context, tag, type);
        if (type == DataType.CurveLine) {
            LinkedList<Entry> tempEntries = new LinkedList<>();
            Entry first = entries.get(0);
            Entry last = entries.get(entries.size() - 1);
            entries.add(0, first);
            entries.add(entries.size() - 1, last);
            for (int i = 0; i < entries.size() - 3; i++) {
                PXY p0 = new PXY(entries.get(0).getX(), entries.get(0).getY());
                PXY p1 = new PXY(entries.get(1).getX(), entries.get(1).getY());
                PXY p2 = new PXY(entries.get(2).getX(), entries.get(2).getY());
                PXY p3 = new PXY(entries.get(3).getX(), entries.get(3).getY());
                tempEntries.add(entries.get(1));
                for (int j = 0; j < 10; j++) {
                    PXY pxy = CurveUtils.CatmullRomPoint(p0, p1, p2, p3, j / 100.0f);
                    tempEntries.add(new Entry(pxy.x, pxy.y));
                }
                tempEntries.add(entries.get(2));
            }
            setEntries(tempEntries);
        } else {
            setEntries(entries);
        }
    }


    @Override
    public float getFoundNearRange() {
        return 0;
    }

    @Override
    public void drawSingleEntry(Canvas canvas, PXY p, ViewportInfo viewportInfo) {
        super.drawSingleEntry(canvas, p, viewportInfo);
    }
}
