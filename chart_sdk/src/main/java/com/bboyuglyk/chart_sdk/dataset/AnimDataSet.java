package com.bboyuglyk.chart_sdk.dataset;

import android.content.Context;
import android.graphics.Paint;

import androidx.annotation.NonNull;

import com.bboyuglyk.chart_sdk.DataType;
import com.bboyuglyk.chart_sdk.Entry;
import com.bboyuglyk.chart_sdk.IEntryDrafter;

import java.util.LinkedList;

public class AnimDataSet extends DataSet{
    public AnimDataSet(Context context) {
        super(context);
    }

    public AnimDataSet(Context context, String tag, DataType type) {
        super(context, tag, type);
    }

    public AnimDataSet(Context context, String tag, DataType type, LinkedList<Entry> baseEntries, LinkedList<Entry> entries) {
        super(context, tag, type,baseEntries, entries);
    }

    @Override
    public float getFoundNearRange() {
        return 0;
    }


    protected AnimDataSet clone() {
        AnimDataSet dataSet=new AnimDataSet(context,tag,type,baseEntries,entries);
        dataSet.setEntryDrafter(entryDrafter);
        dataSet.setPaint(paint);
        dataSet.setShowMarker(showMarker);
        dataSet.setY2(isY2);
        return dataSet;
    }
}
