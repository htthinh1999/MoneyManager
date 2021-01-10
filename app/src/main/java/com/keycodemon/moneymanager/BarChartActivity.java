package com.keycodemon.moneymanager;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.keycodemon.moneymanager.data.ViewDataManager;

import java.util.ArrayList;
import java.util.List;

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

    PieChart pieChart_Evenue;
    PieChart pieChart_Expenditure;
    Spinner spinner_Month;
    Spinner spinner_Year;
    Spinner spinner_Quarter;
    ArrayList<String> spinnerList_Month;
    ArrayList<String> spinnerList_Quarter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);
        viewDataManager = new ViewDataManager(getApplicationContext());
        barChart = findViewById(R.id.bar_chart);
        Init_BarChart();
        pieChart_Evenue = findViewById(R.id.pieChart_Enenue);
        pieChart_Expenditure = findViewById(R.id.pieChart_Expenditure);
        spinner_Month = findViewById(R.id.Spinner_Month);
        spinner_Year = findViewById(R.id.Spinner_Year);
        spinner_Quarter = findViewById(R.id.Spinner_Quarter);
        Init_PieChart();
        Init_Spinner();

    }
    public void Init_Spinner(){
       spinnerList_Month = new ArrayList<String>();
       spinnerList_Month.add("1");
        spinnerList_Month.add("2");
        spinnerList_Month.add("3");
        spinnerList_Month.add("4");
        spinnerList_Month.add("5");
        spinnerList_Month.add("6");
        spinnerList_Month.add("7");
        spinnerList_Month.add("8");
        spinnerList_Month.add("9");
        spinnerList_Month.add("10");
        spinnerList_Month.add("11");
        spinnerList_Month.add("12");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,spinnerList_Month);
        spinner_Month.setAdapter(arrayAdapter);
        spinnerList_Quarter = new ArrayList<>();
        spinnerList_Quarter.add("1");
        spinnerList_Quarter.add("2");
        spinnerList_Quarter.add("3");
        spinnerList_Quarter.add("4");
        ArrayAdapter arrayAdapter_Quarter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,spinnerList_Quarter);
        spinner_Quarter.setAdapter(arrayAdapter_Quarter);
        ArrayAdapter arrayAdapter_year = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,viewDataManager.GetYearForSpinner());
        spinner_Year.setAdapter(arrayAdapter_year);
    }

    public void  Init_BarChart(){
        barDataSet_Revenue = new BarDataSet(viewDataManager.GetEvenueBarEntryByMonth(),"Thu");
        barDataSet_Revenue.setColor(Color.BLUE);
        barDataSet_Expenditure = new BarDataSet(viewDataManager.GetExpenditureBarEntryByMonth(),"Chi");
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
    public void Init_PieChart(){


        PieDataSet pieDataSet_Evenue = new PieDataSet(viewDataManager.GetEvenuePieChartByMonth(2,2021),"Thu");
        pieDataSet_Evenue.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData pieData_Evenue = new PieData(pieDataSet_Evenue);
        pieChart_Evenue.setData(pieData_Evenue);
        pieChart_Evenue.getDescription().setEnabled(false);
        pieChart_Evenue.setCenterText("Thu");
        pieChart_Evenue.setEntryLabelColor(Color.BLACK);
        pieChart_Evenue.setEntryLabelTextSize(10);
        pieChart_Evenue.animate();

        PieDataSet pieDataSet_Expenditure = new PieDataSet(viewDataManager.GetExpenditurePieChartByMonth(2,2021),"Chi");
        pieDataSet_Expenditure.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData pieData_Expenditure = new PieData(pieDataSet_Expenditure);
        pieChart_Expenditure.setData(pieData_Expenditure);
        pieChart_Expenditure.getDescription().setEnabled(false);
        pieChart_Expenditure.setCenterText("Chi");
        pieChart_Expenditure.setEntryLabelColor(Color.BLACK);
        pieChart_Expenditure.setEntryLabelTextSize(10);
        pieChart_Expenditure.animate();
    }


}