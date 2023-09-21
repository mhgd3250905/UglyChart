package com.bboyuglyk.mchart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bboyuglyk.chart_sdk.BaseAxis;
import com.bboyuglyk.chart_sdk.ChartDataKeyMap;
import com.bboyuglyk.chart_sdk.Entry;
import com.bboyuglyk.chart_sdk.ILabelFormatter;
import com.bboyuglyk.chart_sdk.MChart;
import com.bboyuglyk.mchart.new_dataset.LineDataSet;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private MChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chart = findViewById(R.id.chart);

        initChart();
        initData();
    }

    private void initData() {
        LinkedList<Entry> lineEnries = new LinkedList<>();
        for (int i = 0; i < 720; i++) {
            float y = (float) (100*Math.sin(Math.PI*i/100)+200);
            lineEnries.add(new Entry(i * 2, y));
        }
        LineDataSet linDataSet = new LineDataSet(MainActivity.this, ChartDataKeyMap.SG_GLUCOSE,lineEnries);
        chart.addDataSet(linDataSet);
    }

    private void initChart() {
        //设置数据集合绘制优先级
        chart.setPriorityHelper(new MPriorityHelper());
        //设置纵横网格是否显示
        chart.setGridVisibility(true, false);
        //设置横纵坐标是否显示
        chart.setLabelVisibility(true, false);

        BaseAxis xAxis = new BaseAxis();
        xAxis.setMin(0);
        xAxis.setMax(1440);
        xAxis.setMidCount(2);
        xAxis.setiLabelFormatter(new ILabelFormatter() {
            @Override
            public String getLabelFormat(int value, int min, int max) {
                return String.format("%02d:%02d", value / 60, value % 60);
            }
        });

        chart.setxAxis(xAxis);

        BaseAxis yAxis = new BaseAxis();
        yAxis.setMin(27);
        yAxis.setMidCount(7);
        yAxis.setMax(440);
        yAxis.setLabelArrs(new int[]{50, 100, 150, 200, 250, 300, 350, 400, 440});

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