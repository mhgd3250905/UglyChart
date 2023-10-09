package com.bboyuglyk.mchart;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bboyuglyk.chart_sdk.BaseAxis;
import com.bboyuglyk.chart_sdk.ChartDataKeyMap;
import com.bboyuglyk.chart_sdk.Entry;
import com.bboyuglyk.chart_sdk.HightlightMode;
import com.bboyuglyk.chart_sdk.ILabelFormatter;
import com.bboyuglyk.chart_sdk.MChart;
import com.bboyuglyk.mchart.new_dataset.LineDataSet;
import com.bboyuglyk.mchart.new_dataset.SingleBarDataSet;
import com.bboyuglyk.mchart.new_dataset.TripleBarDataSet;
import com.bboyuglyk.mchart.new_dataset.TripleComboBarDataSet;

import java.util.LinkedList;

public class BarActivity extends AppCompatActivity {
    private MChart singleBarChart;
    private MChart comboBarChart1;
    private MChart comboBarChart2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
        singleBarChart = findViewById(R.id.chart_bar);
        comboBarChart1 = findViewById(R.id.chart_bar_combo_1);
        comboBarChart2 = findViewById(R.id.chart_bar_combo_2);

        initChart(singleBarChart);
        initChart(comboBarChart1);
        initChart(comboBarChart2);
        initSingleBarData(singleBarChart);
        initComboBarData(comboBarChart1);
        initCombo2BarData(comboBarChart2);
    }


    private void initSingleBarData(MChart chart) {
        LinkedList<Entry> entries = new LinkedList<>();
        String[] strArr=null;
        for (int i = 1; i < 10; i++) {
            float randomY = 200 + (float) (Math.random() * 200);
            strArr=new String[2];
            strArr[0]=i*200+"";
            strArr[1]=randomY+"";
            entries.add(new Entry(i * 20, randomY,strArr));
        }


        SingleBarDataSet barDataSet = new SingleBarDataSet(BarActivity.this, ChartDataKeyMap.SG_GLUCOSE_LOW, entries);
        chart.addDataSet(barDataSet);


        chart.invalidate();
    }


    private void initComboBarData(MChart chart) {
        LinkedList<Entry> entries = new LinkedList<>();
        for (int i = 1; i < 19; i++) {
            float randomY = 200 + (float) (Math.random() * 200);
            entries.add(new Entry(i * 10, randomY));
        }


        TripleBarDataSet linDataSet = new TripleBarDataSet(BarActivity.this, ChartDataKeyMap.SG_GLUCOSE_LOW, entries);
        chart.addDataSet(linDataSet);
        chart.invalidate();
    }


    private void initCombo2BarData(MChart chart) {
        LinkedList<Entry> entries = new LinkedList<>();
        for (int i = 1; i < 19; i++) {
            float randomY = 200 + (float) (Math.random() * 200);
            entries.add(new Entry(i * 10, randomY));
        }


        TripleComboBarDataSet linDataSet = new TripleComboBarDataSet(BarActivity.this, ChartDataKeyMap.SG_GLUCOSE_LOW, entries);
        chart.addDataSet(linDataSet);
        chart.invalidate();
    }

    private void initChart(MChart chart) {
        //设置数据集合绘制优先级
        chart.setPriorityHelper(new MPriorityHelper());
        //设置纵横网格是否显示
        chart.setGridVisibility(false, false);
        //设置横纵坐标是否显示
        chart.setLabelVisibility(true, true);
        //设置图表label的颜色
        chart.setLabelColor(Color.WHITE,Color.WHITE,Color.WHITE);
        //设置背景颜色
        chart.setBgColor(ContextCompat.getColor(BarActivity.this,R.color.chart_bg_dark));
        //设置网格颜色
        chart.setGridColor(ContextCompat.getColor(BarActivity.this,R.color.white));
        //设置边框颜色
        chart.setBorderColor(Color.WHITE);
        //设置Marker构造器
        chart.setMarkerBuilder(new MBarMarkerBuilder(BarActivity.this));
        //设置highlight寻找模式
        chart.setHightlightMode(HightlightMode.desorption);
        //设置Highlight构造器
        chart.setHighlightBuilder(new MBarHighlightBuilder(BarActivity.this));
        BaseAxis xAxis = new BaseAxis();
        xAxis.setMin(0);
        xAxis.setMax(200);
        xAxis.setMidCount(9);
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
    }
}