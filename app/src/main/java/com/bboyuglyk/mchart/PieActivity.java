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
import com.bboyuglyk.chart_sdk.MPieChart;
import com.bboyuglyk.mchart.new_dataset.SingleBarDataSet;
import com.bboyuglyk.mchart.new_dataset.TriplePie2DataSet;
import com.bboyuglyk.mchart.new_dataset.TriplePieDataSet;

import java.util.LinkedList;
import java.util.Random;

public class PieActivity extends AppCompatActivity {
    private MPieChart mPieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie);
        mPieChart = findViewById(R.id.chart_pie);

        initChart(mPieChart);
        initSingleBarData(mPieChart);
    }


    private void initSingleBarData(MPieChart chart) {
        LinkedList<Entry> entries = new LinkedList<>();

        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            String[] strArr = null;
            strArr = new String[2];
            strArr[0] = "" + i;
            int randomInt = random.nextInt(100);
            strArr[1] = randomInt + "";
            entries.add(new Entry(0, randomInt, strArr));
//            entries.add(new Entry(0, 100,strArr));
        }

//        String[] strArr = null;
//        strArr = new String[2];
//        strArr[0] = "AAA";
//        strArr[1] = "100";
//        entries.add(new Entry(0, 100, strArr));
//        entries.add(new Entry(0, 200, strArr));


        TriplePie2DataSet pieDataSet = new TriplePie2DataSet(PieActivity.this, ChartDataKeyMap.SG_GLUCOSE_LOW, entries);
        chart.addDataSet(pieDataSet);
        chart.invalidate();
    }


    private void initChart(MPieChart chart) {
        //设置数据集合绘制优先级
        chart.setPriorityHelper(new MPriorityHelper());
        //设置边框是否显示
        chart.setShowBorder(false);
        //设置纵横网格是否显示
        chart.setGridVisibility(false, false);
        //设置横纵坐标是否显示
        chart.setLabelVisibility(false, false);
        //设置图表label的颜色
        chart.setLabelColor(Color.WHITE, Color.WHITE, Color.WHITE);
        //设置背景颜色
        chart.setBgColor(ContextCompat.getColor(PieActivity.this, R.color.chart_bg_dark));
        //设置网格颜色
        chart.setGridColor(ContextCompat.getColor(PieActivity.this, R.color.white));
        //设置边框颜色
        chart.setBorderColor(Color.WHITE);
        //设置Marker构造器
        chart.setMarkerBuilder(new MBarMarkerBuilder(PieActivity.this));
        //设置highlight寻找模式
        chart.setHightlightMode(HightlightMode.desorption);
        //设置highlight是否可用
        chart.setNeedShowHighlight(true);
        //设置Highlight构造器
        chart.setHighlightBuilder(new MPieHighlightBuilder(PieActivity.this));
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
        yAxis.setLabelArrs(new int[]{0, 50, 100, 150, 200, 250, 300, 350, 400, 440});

        yAxis.setiLabelFormatter(new ILabelFormatter() {
            @Override
            public String getLabelFormat(int value, int min, int max) {
                return value + "";
            }
        });

        chart.setyAxis(yAxis);
    }
}