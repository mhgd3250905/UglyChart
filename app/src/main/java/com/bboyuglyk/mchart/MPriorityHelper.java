package com.bboyuglyk.mchart;

import com.bboyuglyk.chart_sdk.ChartDataKeyMap;
import com.bboyuglyk.chart_sdk.PriorityHelper;

public class MPriorityHelper extends PriorityHelper {

    @Override
    public int getTagPriority(String tag) {
        switch (tag.toLowerCase()) {
            case ChartDataKeyMap.STATISTIC_TREND:
                return 1;
            case ChartDataKeyMap.STATISTIC_TREND_WATCH_LOW:
                return 2;
            case ChartDataKeyMap.STATISTIC_TREND_WATCH_NORMAL:
                return 3;
            case ChartDataKeyMap.STATISTIC_TREND_WATCH_HIGH:
                return 4;
            case ChartDataKeyMap.CALIB:
                return 5;
            case ChartDataKeyMap.BG_BORDER:
                return 6;
            case ChartDataKeyMap.EVENT_SG:
                return 7;
            case ChartDataKeyMap.EVENT_INSULIN:
                return 8;
            case ChartDataKeyMap.EVENT_EXERCISE:
                return 9;
            case ChartDataKeyMap.EVENT_CARB:
                return 10;
            case ChartDataKeyMap.ACTIVITY_BREAKFAST:
                return 11;
            case ChartDataKeyMap.ACTIVITY_LUNCH:
                return 12;
            case ChartDataKeyMap.ACTIVITY_DINNER:
                return 13;
            case ChartDataKeyMap.ACTIVITY_BREAKFAST_LARGE:
                return 14;
            case ChartDataKeyMap.ACTIVITY_LUNCH_LARGE:
                return 15;
            case ChartDataKeyMap.ACTIVITY_DINNER_LARGE:
                return 16;
            case ChartDataKeyMap.ACTIVITY_SNACK:
                return 17;
            case ChartDataKeyMap.ACTIVITY_EXERCISE:
                return 18;
            case ChartDataKeyMap.TIME_CHANGE:
                return 19;
            case ChartDataKeyMap.SG_NEW:
                return 20;
            case ChartDataKeyMap.SG_GLUCOSE:
                return 21;
            case ChartDataKeyMap.SG_GLUCOSE_LOW:
                return 22;
            case ChartDataKeyMap.SG_GLUCOSE_NORMAL:
                return 23;
            case ChartDataKeyMap.SG_GLUCOSE_HIGH:
                return 24;
            case ChartDataKeyMap.SG_TRANSPARENT:
                return 25;
            case ChartDataKeyMap.WARNING_SG_LILNE:
                return 26;
            case ChartDataKeyMap.PUMP_STOP:
                return 27;
            case ChartDataKeyMap.PUMP_BASAL:
                return 28;
            case ChartDataKeyMap.PUMP_TEMP_BASAL:
                return 29;
            case ChartDataKeyMap.PUMP_NORMAL_BOLUS:
                return 30;
            case ChartDataKeyMap.PUMP_EXTEND_BOLUS:
                return 31;
            case ChartDataKeyMap.PUMP_AUTO_MODE_BOLUS:
                return 32;
            case ChartDataKeyMap.PUMP_AUTO_MODE_BASAL:
                return 33;
            case ChartDataKeyMap.PUMP_ACTIVE:
                return 34;
            case ChartDataKeyMap.PUMP_SUSPEND_SOLID:
                return 35;
            case ChartDataKeyMap.PUMP_SUSPEND_DASHED:
                return 36;
            case ChartDataKeyMap.PUMP_SLEEP:
                return 37;
        }
        return 9999;
    }
}
