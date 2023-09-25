package com.bboyuglyk.mchart;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bboyuglyk.chart_sdk.BaseAxis;
import com.bboyuglyk.chart_sdk.ChartDataKeyMap;
import com.bboyuglyk.chart_sdk.Entry;
import com.bboyuglyk.chart_sdk.ILabelFormatter;
import com.bboyuglyk.chart_sdk.MChart;
import com.bboyuglyk.chart_sdk.PXY;
import com.bboyuglyk.mchart.new_dataset.Double2LineSet;
import com.bboyuglyk.mchart.new_dataset.DoubleLineSet;
import com.bboyuglyk.mchart.new_dataset.LineDataSet;
import com.bboyuglyk.mchart.new_dataset.QuatraLineSet;
import com.bboyuglyk.mchart.new_dataset.TripleLineSet;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private MChart chart;
    private Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chart = findViewById(R.id.chart);
        btnTest = findViewById(R.id.btn_test);

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });

        initChart();
        initData();
    }

    private void initData() {
        LinkedList<Entry> entries = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            entries.add(new Entry(i*200, (float) (Math.random()*400)));
        }

        Entry first = entries.get(0);
        Entry last = entries.get(entries.size()-1);
        entries.add(0,first);
        entries.add(entries.size()-1,last);

        QuatraLineSet quatraLineSet = new QuatraLineSet(MainActivity.this, ChartDataKeyMap.SG_GLUCOSE_LOW,entries);
        chart.addDataSet(quatraLineSet);

        DoubleLineSet linDataSet = new DoubleLineSet(MainActivity.this, ChartDataKeyMap.SG_GLUCOSE,entries);
        chart.addDataSet(linDataSet);


        chart.invalidate();
//        Double2LineSet linDataSet3 = new Double2LineSet(MainActivity.this, ChartDataKeyMap.SG_GLUCOSE_HIGH,doubleLineEnries);
//        chart.addDataSet(linDataSet3);
    }

    private void initChart() {
        //设置数据集合绘制优先级
        chart.setPriorityHelper(new MPriorityHelper());
        //设置纵横网格是否显示
        chart.setGridVisibility(true, false);
        //设置横纵坐标是否显示
        chart.setLabelVisibility(true, true);

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