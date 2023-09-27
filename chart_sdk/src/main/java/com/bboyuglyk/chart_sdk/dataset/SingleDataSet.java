package com.bboyuglyk.chart_sdk.dataset;

import android.content.Context;
import android.graphics.Canvas;

import com.bboyuglyk.chart_sdk.CurveUtils;
import com.bboyuglyk.chart_sdk.DataType;
import com.bboyuglyk.chart_sdk.Entry;
import com.bboyuglyk.chart_sdk.PXY;
import com.bboyuglyk.chart_sdk.ViewportInfo;

import java.util.LinkedList;

public class SingleDataSet extends AnimDataSet {

    public SingleDataSet(Context context) {
        super(context);
    }

    public SingleDataSet(Context context, String tag, DataType type, LinkedList<Entry> baseEntries) {
        super(context, tag, type);
        setBaseEntries(baseEntries);
        if (type == DataType.CurveSingle||type == DataType.CurveDouble) {
            setEntries(DataConverter.convertToCurve(baseEntries));
        } else {
            setEntries(baseEntries);
        }
    }


    @Override
    public float getFoundNearRange() {
        return 0;
    }

}
