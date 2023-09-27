package com.bboyuglyk.chart_sdk.dataset;

import com.bboyuglyk.chart_sdk.CurveUtils;
import com.bboyuglyk.chart_sdk.Entry;
import com.bboyuglyk.chart_sdk.PXY;

import java.util.LinkedList;
import java.util.List;

public class DataConverter {



    /**
     * 转换到Cutmull-Rom插值列表
     * @param baseEntries
     * @return
     */
    public static LinkedList<Entry> convertToCurve(LinkedList<Entry> baseEntries) {
        LinkedList<Entry> entries = (LinkedList<Entry>) baseEntries.clone();
        LinkedList<Entry> tempEntries = new LinkedList<>();
        Entry first = entries.get(0);
        Entry last = entries.get(entries.size() - 1);
        entries.add(0, first);
        entries.add(entries.size() - 1, last);
        for (int i = 0; i < entries.size() - 3; i++) {
            PXY p0 = new PXY(entries.get(i).getX(), entries.get(i).getY());
            PXY p1 = new PXY(entries.get(i + 1).getX(), entries.get(i + 1).getY());
            PXY p2 = new PXY(entries.get(i + 2).getX(), entries.get(i + 2).getY());
            PXY p3 = new PXY(entries.get(i + 3).getX(), entries.get(i + 3).getY());
//                tempEntries.add(entries.get(i));
            for (int j = 0; j < 100; j++) {
                PXY pxy = CurveUtils.CatmullRomPoint(p0, p1, p2, p3, j / 100.0f);
                tempEntries.add(new Entry(pxy.x, pxy.y));
            }
//                tempEntries.add(entries.get(2));
        }
        return tempEntries;
    }
}
