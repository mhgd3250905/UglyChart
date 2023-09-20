package com.bboyuglyk.mchart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bboyuglyk.chart_sdk.BaseAxis;
import com.bboyuglyk.chart_sdk.ILabelFormatter;
import com.bboyuglyk.chart_sdk.MChart;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {
    private MChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chart = findViewById(R.id.chart);

        initChart();
    }

    private void initChart() {
        chart.setPriorityHelper(new MPriorityHelper());


        BaseAxis xAxis = new BaseAxis();
        xAxis.setMin(0);
        xAxis.setMax(1440);
        xAxis.setMidCount(2);
        xAxis.setiLabelFormatter(new ILabelFormatter() {
            @Override
            public String getLabelFormat(int value, int min, int max) {
//                Calendar calendar = GregorianCalendar.getInstance();
//                calendar.setTime(new Date());
//                Date zeroHour = MTDateUtils.getZeroHour(calendar).getTime();
//                Date time = MTDateUtils.dateByAddingTimeInterval(zeroHour, value * MTDateUtils.MINUTE_IN_MILLIS);
//
//                return simpleDateFormat.format(time);
                return String.format("%02d:%02d", value / 60, value % 60);
            }
        });

        chart.setxAxis(xAxis);

        BaseAxis yAxis = new BaseAxis();
        yAxis.setMin(27);
        yAxis.setMidCount(7);
        yAxis.setMax(440);
        yAxis.setLabelArrs(new int[]{50,  150, 210, 250, 300, 310, 400, 440});

        yAxis.setiLabelFormatter(new ILabelFormatter() {
            @Override
            public String getLabelFormat(int value, int min, int max) {
                return value + "";
            }
        });

        chart.setyAxis(yAxis);

        BaseAxis y2Axis = new BaseAxis();
        y2Axis.setMin(0);
        y2Axis.setMidCount(4);
        y2Axis.setMax(500);
//        yAxis.setLabelArrs(new int[]{50,  150, 210, 250, 300, 310, 400, 440});

        y2Axis.setiLabelFormatter(new ILabelFormatter() {
            @Override
            public String getLabelFormat(int value, int min, int max) {
                return value + "";
            }
        });

        chart.sety2Axis(y2Axis);
    }
}