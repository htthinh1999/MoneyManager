package com.keycodemon.moneymanager;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
    Spinner spinner;
    ArrayList<String> spinnerList;
    ArrayList<String> spinnerList_Month;
    ArrayList<String> spinnerList_Quarter;
    PieData pieData_Evenue;
    PieDataSet pieDataSet_Evenue;
    PieDataSet pieDataSet_Expenditure;
    PieData pieData_Expenditure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);
        viewDataManager = new ViewDataManager(getApplicationContext());
        barChart = findViewById(R.id.bar_chart);
        pieChart_Evenue = findViewById(R.id.pieChart_Enenue);
        pieChart_Expenditure = findViewById(R.id.pieChart_Expenditure);
        spinner = findViewById(R.id.Spinner);
        spinner_Month = findViewById(R.id.Spinner_Month);
        spinner_Year = findViewById(R.id.Spinner_Year);
        spinner_Quarter = findViewById(R.id.Spinner_Quarter);
        Init_Spinner();
        Init_PieChart();
        SnipperSelect();

    }
    public void Init_Spinner(){
        spinnerList = new ArrayList<>();
        spinnerList.add("Hàng tháng");
        spinnerList.add("Hàng quý");
        spinnerList.add("Hàng năm");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,spinnerList);
        spinner.setAdapter(arrayAdapter);
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
        ArrayAdapter arrayAdapter_month = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,spinnerList_Month);
        spinner_Month.setAdapter(arrayAdapter_month);
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

    public void  Init_BarChart_Month(){
        String slectitem = spinner_Year.getSelectedItem().toString();
        barDataSet_Revenue = new BarDataSet(viewDataManager.GetEvenueBarEntryByMonth(Integer.parseInt(slectitem)),"Thu");
        barDataSet_Revenue.setColor(Color.BLUE);
        barDataSet_Revenue.notifyDataSetChanged();
        barDataSet_Expenditure = new BarDataSet(viewDataManager.GetExpenditureBarEntryByMonth(Integer.parseInt(slectitem)),"Chi");
        barDataSet_Expenditure.setColor(Color.RED);
        barDataSet_Expenditure.notifyDataSetChanged();
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
        barData.setBarWidth(0.20f);
        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setAxisMaximum(0+barChart.getBarData().getGroupWidth(groupSpace,barSpace)*12);
        barChart.getAxisLeft().setAxisMinimum(0);
        barChart.groupBars(0,groupSpace,barSpace);
        barChart.getDescription().setEnabled(false);
        barChart.notifyDataSetChanged();
        barChart.invalidate();
    }

    public void  Init_BarChart_Quarter(){
        String slectitem = spinner_Year.getSelectedItem().toString();
        barDataSet_Revenue = new BarDataSet(viewDataManager.GetEvenueBarEntryByQuarter(Integer.parseInt(slectitem)),"Thu");
        barDataSet_Revenue.setColor(Color.BLUE);
        barDataSet_Revenue.notifyDataSetChanged();
        barDataSet_Expenditure = new BarDataSet(viewDataManager.GetExpenditureBarEntryByQuarter(Integer.parseInt(slectitem)),"Chi");
        barDataSet_Expenditure.setColor(Color.RED);
        barDataSet_Expenditure.notifyDataSetChanged();
        barData = new BarData(barDataSet_Revenue,barDataSet_Expenditure);
        barChart.setData(barData);

        titleValues= new String[]{"Quý 1","Quý 2","Quý 3","Quý 4"};
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
        barData.setBarWidth(0.20f);
        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setAxisMaximum(0+barChart.getBarData().getGroupWidth(groupSpace,barSpace)*4);
        barChart.getAxisLeft().setAxisMinimum(0);
        barChart.groupBars(0,groupSpace,barSpace);
        barChart.getDescription().setEnabled(false);
        barChart.notifyDataSetChanged();
        barChart.invalidate();
    }

    public void Init_BarChart_Year(){
        barDataSet_Revenue = new BarDataSet(viewDataManager.GetEvenueBarEntryByYear(),"Thu");
        barDataSet_Revenue.setColor(Color.BLUE);
        barDataSet_Revenue.notifyDataSetChanged();
        barDataSet_Expenditure = new BarDataSet(viewDataManager.GetExpenditureEntryByYear(),"Chi");
        barDataSet_Expenditure.setColor(Color.RED);
        barDataSet_Expenditure.notifyDataSetChanged();
        barData = new BarData(barDataSet_Revenue,barDataSet_Expenditure);
        barChart.setData(barData);
        ArrayList<String> title = viewDataManager.GetYearForSpinner();
//        titleValues= new String[]{"Quý 1","Quý 2","Quý 3","Quý 4"};
        xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(title));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);

        barChart.setDragEnabled(true);
        barChart.setVisibleXRangeMaximum(3);

        barSpace = 0.08f;
        groupSpace = 0.44f;
        barData.setBarWidth(0.20f);
        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setAxisMaximum(0+barChart.getBarData().getGroupWidth(groupSpace,barSpace)*title.size());
        barChart.getAxisLeft().setAxisMinimum(0);
        barChart.groupBars(0,groupSpace,barSpace);
        barChart.getDescription().setEnabled(false);
        barChart.notifyDataSetChanged();
        barChart.invalidate();
    }

    public void Init_PieChart(){
        String itemMonth = spinner_Month.getSelectedItem().toString();
        String itemYear = spinner_Year.getSelectedItem().toString();
        pieDataSet_Evenue = new PieDataSet(viewDataManager.GetEvenuePieChartByMonth(Integer.parseInt(itemMonth),Integer.parseInt(itemYear)),"Thu");
        pieDataSet_Expenditure = new PieDataSet(viewDataManager.GetExpenditurePieChartByMonth(Integer.parseInt(itemMonth),Integer.parseInt(itemYear)),"Chi");
        setupPieChart();
    }
    public void setupPieChart(){
        pieDataSet_Evenue.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet_Evenue.notifyDataSetChanged();
        pieData_Evenue = new PieData(pieDataSet_Evenue);
        pieData_Evenue.notifyDataChanged();
        pieChart_Evenue.setData(pieData_Evenue);

        pieChart_Evenue.getDescription().setEnabled(false);
        pieChart_Evenue.setCenterText("Thu");
        pieChart_Evenue.setEntryLabelColor(Color.BLACK);
        pieChart_Evenue.setEntryLabelTextSize(10);
        pieChart_Evenue.animate();
        pieChart_Evenue.notifyDataSetChanged();
        pieChart_Evenue.invalidate();

        pieDataSet_Expenditure.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet_Expenditure.notifyDataSetChanged();
        pieData_Expenditure = new PieData(pieDataSet_Expenditure);
        pieData_Expenditure.notifyDataChanged();
        pieChart_Expenditure.setData(pieData_Expenditure);

        pieChart_Expenditure.getDescription().setEnabled(false);
        pieChart_Expenditure.setCenterText("Chi");
        pieChart_Expenditure.setEntryLabelColor(Color.BLACK);
        pieChart_Expenditure.setEntryLabelTextSize(12);
        pieChart_Expenditure.animate();
        pieChart_Expenditure.notifyDataSetChanged();
        pieChart_Expenditure.invalidate();
    }
    public void Spinner_Month_Onclick(){
        spinner_Month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String selectitem = adapterView.getItemAtPosition(position).toString();
                pieChart_Evenue.clearValues();
                String itemYear = spinner_Year.getSelectedItem().toString();
                pieDataSet_Evenue = new PieDataSet(viewDataManager.GetEvenuePieChartByMonth(Integer.parseInt(selectitem),Integer.parseInt(itemYear)),"Thu");
                pieChart_Expenditure.clearValues();
                pieDataSet_Expenditure = new PieDataSet(viewDataManager.GetExpenditurePieChartByMonth(Integer.parseInt(selectitem),Integer.parseInt(itemYear)),"Chi");
                setupPieChart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public  void Spinner_Quarter_OnClick(){
        spinner_Quarter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String selectitem = adapterView.getItemAtPosition(position).toString();

//                pieChart_Evenue.clearValues();
                String itemYear = spinner_Year.getSelectedItem().toString();
                pieDataSet_Evenue = new PieDataSet(viewDataManager.GetEvenuePieChartByQuarter(Integer.parseInt(selectitem),Integer.parseInt(itemYear)),"Thu");
//                pieChart_Expenditure.clearValues();
                pieDataSet_Expenditure = new PieDataSet(viewDataManager.GetExpenditurePieChartByQuarter(Integer.parseInt(selectitem),Integer.parseInt(itemYear)),"Chi");
                setupPieChart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public void Spinner_Year_Onclick(){
        spinner_Year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String selectitem = adapterView.getItemAtPosition(position).toString();
                pieChart_Evenue.clearValues();
                pieDataSet_Evenue = new PieDataSet(viewDataManager.GetEvenuePieChartByYear(Integer.parseInt(selectitem)),"Thu");

                pieChart_Expenditure.clearValues();
                pieDataSet_Expenditure = new PieDataSet(viewDataManager.GetExpenditurePieChartByYear(Integer.parseInt(selectitem)),"Chi");
                setupPieChart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public void Spinner_Year_Onclick_For_Month(){
        spinner_Year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String selectitem = adapterView.getItemAtPosition(position).toString();
                String itemmonth = spinner_Month.getSelectedItem().toString();
                pieChart_Evenue.clearValues();
                pieDataSet_Evenue = new PieDataSet(viewDataManager.GetEvenuePieChartByMonth(Integer.parseInt(itemmonth),Integer.parseInt(selectitem)),"Thu");

                pieChart_Expenditure.clearValues();
                pieDataSet_Expenditure = new PieDataSet(viewDataManager.GetExpenditurePieChartByMonth(Integer.parseInt(itemmonth),Integer.parseInt(selectitem)),"Chi");
                setupPieChart();
                Init_BarChart_Month();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void Spinner_Year_Onclick_For_Quarter(){
        spinner_Year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String selectitem = adapterView.getItemAtPosition(position).toString();
                String itemQuarter = spinner_Quarter.getSelectedItem().toString();
                pieChart_Evenue.clearValues();
                pieDataSet_Evenue = new PieDataSet(viewDataManager.GetEvenuePieChartByQuarter(Integer.parseInt(itemQuarter),Integer.parseInt(selectitem)),"Thu");

                pieChart_Expenditure.clearValues();
                pieDataSet_Expenditure = new PieDataSet(viewDataManager.GetExpenditurePieChartByQuarter(Integer.parseInt(itemQuarter),Integer.parseInt(selectitem)),"Chi");
                setupPieChart();
                Init_BarChart_Quarter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void SnipperSelect(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String selectitem = adapterView.getItemAtPosition(position).toString();
                if(selectitem.equals("Hàng tháng")){

                    spinner_Quarter.setEnabled(false);
                    spinner_Month.setEnabled(true);
                    String itemmonth = spinner_Month.getSelectedItem().toString();
                    String itemyear = spinner_Year.getSelectedItem().toString();
                    pieChart_Evenue.clearValues();
                    pieDataSet_Evenue = new PieDataSet(viewDataManager.GetEvenuePieChartByMonth(Integer.parseInt(itemmonth),Integer.parseInt(itemyear)),"Thu");

                    pieChart_Expenditure.clearValues();
                    pieDataSet_Expenditure = new PieDataSet(viewDataManager.GetExpenditurePieChartByMonth(Integer.parseInt(itemmonth),Integer.parseInt(itemyear)),"Chi");
                    setupPieChart();
                    Spinner_Month_Onclick();
                    Init_BarChart_Month();
                    Spinner_Year_Onclick_For_Month();
                }else
                    if(selectitem.equals("Hàng quý")){
                        spinner_Month.setEnabled(false);
                        spinner_Quarter.setEnabled(true);
                        String itemquarter = spinner_Quarter.getSelectedItem().toString();
                        String itemyear = spinner_Year.getSelectedItem().toString();
                        pieChart_Evenue.clearValues();
                        pieDataSet_Evenue = new PieDataSet(viewDataManager.GetEvenuePieChartByQuarter(Integer.parseInt(itemquarter),Integer.parseInt(itemyear)),"Thu");

                        pieChart_Expenditure.clearValues();
                        pieDataSet_Expenditure = new PieDataSet(viewDataManager.GetExpenditurePieChartByQuarter(Integer.parseInt(itemquarter),Integer.parseInt(itemyear)),"Chi");
                        setupPieChart();
                        Spinner_Quarter_OnClick();
                        Init_BarChart_Quarter();
                        Spinner_Year_Onclick_For_Quarter();
                    }else
                    if(selectitem.equals("Hàng năm")){
                        spinner_Month.setEnabled(false);
                        spinner_Quarter.setEnabled(false);
                        String itemyear = spinner_Year.getSelectedItem().toString();
                        pieChart_Evenue.clearValues();
                        pieDataSet_Evenue = new PieDataSet(viewDataManager.GetEvenuePieChartByYear(Integer.parseInt(itemyear)),"Thu");

                        pieChart_Expenditure.clearValues();
                        pieDataSet_Expenditure = new PieDataSet(viewDataManager.GetExpenditurePieChartByYear(Integer.parseInt(itemyear)),"Chi");
                        setupPieChart();
                        Init_BarChart_Year();
                        Spinner_Year_Onclick();
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

}