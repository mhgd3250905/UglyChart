package com.bboyuglyk.chart_sdk;

import android.content.Context;

public class DpPxSpUtils {

    public static int dp2px(Context context, int value) {
        float v = context.getResources().getDisplayMetrics().density;
        return (int) (v * value + 0.5f);
    }

    public static int sp2px(Context context,int value) {
        float v = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (v * value + 0.5f);
    }
}
