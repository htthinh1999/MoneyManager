package com.keycodemon.moneymanager;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.keycodemon.moneymanager.data.ViewDataManager;

import java.util.ArrayList;

public class BarChartActivity extends AppCompatActivity {

    BarChart barChart;
    BarDataSet barDataSet_Revenue;
    BarDataSet barDataSet_Expenditure;
    BarData barData;
    String[] titleValues;
    XAxis xAxis;
    float barSpace;
    float groupSpace;
    ViewDataManager viewDataManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);
        viewDataManager = new ViewDataManager(getApplicationContext());
        barChart = findViewById(R.id.bar_chart);

        barDataSet_Revenue = new BarDataSet(viewDataManager.GetEvenueBarEntry(),"Thu");
        Long tt = viewDataManager.GetEvenuebyMonth(1);
        barDataSet_Revenue.setColor(Color.BLUE);
        barDataSet_Expenditure = new BarDataSet(viewDataManager.GetExpenditureBarEntry(),"Chi");
        barDataSet_Expenditure.setColor(Color.RED);
        barData = new BarData(barDataSet_Revenue,barDataSet_Expenditure);
        barChart.setData(barData);

        titleValues= new String[]{"Tháng 1","Tháng 2","Tháng 3","Tháng 4","Tháng 5","Tháng 6","Tháng 7","Tháng 8","Tháng 9","Tháng 10","Tháng 11","Tháng 12"};
        xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(titleValues));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);

        barChart.setDragEnabled(true);
        barChart.setVisibleXRangeMaximum(3);

        barSpace = 0.08f;
        groupSpace = 0.44f;
        barData.setBarWidth(0.10f);
        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setAxisMaximum(0+barChart.getBarData().getGroupWidth(groupSpace,barSpace)*12);
        barChart.getAxisLeft().setAxisMinimum(0);
        barChart.groupBars(0,groupSpace,barSpace);
        barChart.invalidate();

    }
//    public ArrayList<BarEntry> barEntries_Revenue(){
//        ArrayList<BarEntry> barEntries_Revenue = new ArrayList<>();
//        barEntries_Revenue.add(new BarEntry(1, 1000000));
//        barEntries_Revenue.add(new BarEntry(2, 1000000));
//        barEntries_Revenue.add(new BarEntry(3, 1000000));
//        barEntries_Revenue.add(new BarEntry(4, 1000000));
//        return  barEntries_Revenue;
//    }
//    public ArrayList<BarEntry> barEntries_Expenditure(){
//        ArrayList<BarEntry> barEntries_Expenditure = new ArrayList<>();
//        barEntries_Expenditure.add(new BarEntry(1, 500000));
//        barEntries_Expenditure.add(new BarEntry(2, 500000));
//        barEntries_Expenditure.add(new BarEntry(3, 500000));
//        barEntries_Expenditure.add(new BarEntry(4, 500000));
//        return  barEntries_Expenditure;
//    }
}