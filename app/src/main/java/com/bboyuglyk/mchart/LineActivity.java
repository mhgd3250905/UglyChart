package com.bboyuglyk.mchart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;

import com.bboyuglyk.chart_sdk.BaseAxis;
import com.bboyuglyk.chart_sdk.ChartDataKeyMap;
import com.bboyuglyk.chart_sdk.Entry;
import com.bboyuglyk.chart_sdk.ILabelFormatter;
import com.bboyuglyk.chart_sdk.MChart;
import com.bboyuglyk.mchart.new_dataset.CurveLineDataSet;
import com.bboyuglyk.mchart.new_dataset.LineDataSet;

import java.util.LinkedList;

public class LineActivity extends AppCompatActivity {
    private MChart curveLineChart;
    private MChart lineChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);
        curveLineChart = findViewById(R.id.chart_curve);
        lineChart = findViewById(R.id.chart_line);

        initChart(curveLineChart);
        initChart(lineChart);
        initCurveData(curveLineChart);
        initLineData(lineChart);
    }


    private void initCurveData(MChart chart) {
        LinkedList<Entry> entries = new LinkedList<>();
        LinkedList<Entry> entries2 = new LinkedList<>();
        for (int i = 0; i < 11; i++) {
            float randomY = 200 + (float) (Math.random() * 200);
            float randomY2 = 100 + (float) (Math.random() * 100);
            entries.add(new Entry(i * 200, randomY));
            entries2.add(new Entry(i * 200, randomY2));
        }


        CurveLineDataSet linDataSet = new CurveLineDataSet(LineActivity.this, ChartDataKeyMap.SG_GLUCOSE_LOW, entries);
        linDataSet.setColors(new int[]{
                ContextCompat.getColor(LineActivity.this,R.color.teal_200),
                ContextCompat.getColor(LineActivity.this,R.color.purple_200),
                Color.TRANSPARENT
        });
        chart.addAnimDataSet(linDataSet);

        CurveLineDataSet linDataSet2 = new CurveLineDataSet(LineActivity.this, ChartDataKeyMap.SG_GLUCOSE, entries2);
        linDataSet2.setColors(new int[]{Color.WHITE,Color.WHITE});
        chart.addAnimDataSet(linDataSet2);

        chart.invalidate();
    }


    private void initLineData(MChart chart) {
        LinkedList<Entry> entries = new LinkedList<>();
        LinkedList<Entry> entries2 = new LinkedList<>();
        for (int i = 0; i < 11; i++) {
            float randomY = 200 + (float) (Math.random() * 200);
            float randomY2 = 100 + (float) (Math.random() * 100);
            entries.add(new Entry(i * 200, randomY));
            entries2.add(new Entry(i * 200, randomY2));
        }


        LineDataSet linDataSet = new LineDataSet(LineActivity.this, ChartDataKeyMap.SG_GLUCOSE_LOW, entries);
        linDataSet.setLineColor(ContextCompat.getColor(LineActivity.this,R.color.teal_200));
        chart.addAnimDataSet(linDataSet);

        LineDataSet linDataSet2 = new LineDataSet(LineActivity.this, ChartDataKeyMap.SG_GLUCOSE, entries2);
        linDataSet2.setLineColor(ContextCompat.getColor(LineActivity.this,R.color.purple_500));
        chart.addAnimDataSet(linDataSet2);

        chart.invalidate();
    }

    private void initChart(MChart chart) {
        //设置数据集合绘制优先级
        chart.setPriorityHelper(new MPriorityHelper());
        //设置纵横网格是否显示
        chart.setGridVisibility(true, false);
        //设置横纵坐标是否显示
        chart.setLabelVisibility(true, true);
        //设置图表label的颜色
        chart.setLabelColor(Color.GREEN,Color.YELLOW,Color.RED);
        //设置背景颜色
        chart.setBgColor(ContextCompat.getColor(LineActivity.this,R.color.chart_bg_dark));
        //设置网格颜色
        chart.setGridColor(ContextCompat.getColor(LineActivity.this,R.color.white));
        //设置边框颜色
        chart.setBorderColor(Color.YELLOW);
        BaseAxis xAxis = new BaseAxis();
        xAxis.setMin(0);
        xAxis.setMax(2000);
        xAxis.setMidCount(3);
        xAxis.setiLabelFormatter(new ILabelFormatter() {
            @Override
            public String getLabelFormat(int value, int min, int max) {
                return String.format("%d", value);
            }
        });

        chart.setxAxis(xAxis);

        BaseAxis yAxis = new BaseAxis();
        yAxis.setMin(27);
        yAxis.setMidCount(7);
        yAxis.setMax(440);
        yAxis.setLabelArrs(new int[]{0,50, 100, 150, 200, 250, 300, 350, 400, 440});

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