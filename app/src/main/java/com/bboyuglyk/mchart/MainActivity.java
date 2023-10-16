package com.bboyuglyk.mchart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bboyuglyk.chart_sdk.MChart;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private MChart chart;
    private Button btnLineChart;
    private Button btnBarChart;
    private Button btnPieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chart = findViewById(R.id.chart_curve);
        btnLineChart = findViewById(R.id.btn_line_chart);
        btnBarChart = findViewById(R.id.btn_bar_chart);
        btnPieChart = findViewById(R.id.btn_pie_chart);

        btnLineChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LineActivity.class));
            }
        });

        btnBarChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,BarActivity.class));
            }
        });

        btnPieChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,PieActivity.class));
            }
        });

    }

}