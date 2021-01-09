package com.keycodemon.moneymanager.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;
import com.keycodemon.moneymanager.model.RevenueExpenditureDetail;
import com.keycodemon.moneymanager.viewmodel.DayData;
import com.keycodemon.moneymanager.viewmodel.ItemDetailData;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ViewDataManager extends DBManager{

    private Context context;
    private static final String TABLE_FORM = "HINHTHUC";
    private static final String FORM_ID = "IDHinhThuc";
    private static final String FORM_NAME ="TenHinhThuc";

    private static final String TABLE_CATEGORY = "THELOAI";
    private static final String CATEGORY_ID = "IDTheLoai";
    private static final String CATEGORY_NAME = "TenTheLoai";

    private static final String TABLE_ACCOUNT = "TAIKHOAN";
    private static final String ACCOUNT_ID = "IDTaiKhoan";
    private static final String ACCOUNT_NAME ="TenTaiKhoan";
    private static final String ACCOUNT_BALANCE ="SoDu";

    private static final String TABLE_REVENUE_EXPENDITURE_DETAIL = "CHITIETTHUCHI";
    private static final String REVENUE_EXPENDITURE_ID = "IDThuChi";
    private static final String REVENUE_EXPENDITURE_MONEY = "SoTien";
    private static final String REVENUE_EXPENDITURE_NOTE = "GhiChu";
    private static final String REVENUE_EXPENDITURE_PERIODIC = "DinhKy";
    private static final String REVENUE_EXPENDITURE_DATE = "Ngay";


    private Map<String, Integer> dayAddedPosition;

    public ViewDataManager(@Nullable Context context) {
        super(context);
        init();
    }

    private void init(){
        this.context = context;
        dayAddedPosition = new HashMap<>();
    }

    public Long GetEvenuebyMonth(int month) {
        Long revenueMoney = 0l;
        String revenueQuery = "SELECT " + REVENUE_EXPENDITURE_DATE + ", SUM(" + REVENUE_EXPENDITURE_MONEY + ")" +
                " FROM " + TABLE_REVENUE_EXPENDITURE_DETAIL +
                " WHERE " + FORM_ID + " = 1" +
                " GROUP BY " + REVENUE_EXPENDITURE_DATE +
                " ORDER BY " + REVENUE_EXPENDITURE_DATE + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
         Cursor cursor = db.rawQuery(revenueQuery, null);
        if(cursor.moveToFirst()){
            do{
                String date = cursor.getString(0);
                int revenuemonth = Integer.valueOf(date.substring(3, 5));
                if(revenuemonth == month){
                    revenueMoney += cursor.getLong(1);
                }
            }while (cursor.moveToNext());
        }
        Log.d("tt",revenueMoney.toString());
        cursor.close();
        db.close();

        return revenueMoney;
    }
    public ArrayList<String> GetYearForSpinner(){
        ArrayList<String> arrayList = new ArrayList<>();
        String query = " SELECT "+REVENUE_EXPENDITURE_DATE+" FROM "+TABLE_REVENUE_EXPENDITURE_DETAIL;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            String temp="";
            do{
                    String date = cursor.getString(0);
                    String year = date.substring(6, 10);
                    if(!temp.equals(year)){
                        arrayList.add(year);
                        temp = date.substring(6, 10);
                    }
            }while (cursor.moveToNext());
        }
        return  arrayList;
    }
    public ArrayList<PieEntry> GetEvenuePieChartByMonth(int Pmonth, int Pyear){
        ArrayList<PieEntry> pieEntryArrayList = new ArrayList<>();
        String query = "SELECT "+REVENUE_EXPENDITURE_DATE+","+REVENUE_EXPENDITURE_MONEY+", "+CATEGORY_NAME+"" +
                "  FROM "+TABLE_REVENUE_EXPENDITURE_DETAIL+" INNER JOIN "+TABLE_CATEGORY+" ON "+TABLE_REVENUE_EXPENDITURE_DETAIL+"."+
        CATEGORY_ID+" = "+TABLE_CATEGORY+"."+CATEGORY_ID+" WHERE "+TABLE_REVENUE_EXPENDITURE_DETAIL+"."+FORM_ID+" = 1" ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                    String date = cursor.getString(0);
                    int month = Integer.parseInt(date.substring(3, 5));
                    int year = Integer.parseInt(date.substring(6, 10));
                    if(month == Pmonth && year == Pyear){
                        Long money = cursor.getLong(1);
                        String category = cursor.getString(2);
                    pieEntryArrayList.add(new PieEntry(money,category));
                }
            }while (cursor.moveToNext());
        }
        return pieEntryArrayList;
    }

    public ArrayList<PieEntry> GetExpenditurePieChartByMonth(int Pmonth, int Pyear){
        ArrayList<PieEntry> pieEntryArrayList = new ArrayList<>();
        String query = "SELECT "+REVENUE_EXPENDITURE_DATE+","+REVENUE_EXPENDITURE_MONEY+", "+CATEGORY_NAME+"" +
                "  FROM "+TABLE_REVENUE_EXPENDITURE_DETAIL+" INNER JOIN "+TABLE_CATEGORY+" ON "+TABLE_REVENUE_EXPENDITURE_DETAIL+"."+
                CATEGORY_ID+" = "+TABLE_CATEGORY+"."+CATEGORY_ID+" WHERE "+TABLE_REVENUE_EXPENDITURE_DETAIL+"."+FORM_ID+" = 2" ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String date = cursor.getString(0);
                int month = Integer.parseInt(date.substring(3, 5));
                int year = Integer.parseInt(date.substring(6, 10));
                if(month == Pmonth && year == Pyear){
                    Long money = cursor.getLong(1);
                    String category = cursor.getString(2);
                    pieEntryArrayList.add(new PieEntry(money,category));
                }
            }while (cursor.moveToNext());
        }
        return pieEntryArrayList;
    }

    public ArrayList<BarEntry> GetEvenueBarEntryByMonth(){
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        BarEntry barEntry =new BarEntry(1, GetEvenuebyMonth(1));
        barEntries.add(barEntry);
        barEntries.add(new BarEntry(2, GetEvenuebyMonth(2)));
        barEntries.add(new BarEntry(3, GetEvenuebyMonth(3)));
        barEntries.add(new BarEntry(4, GetEvenuebyMonth(4)));
        barEntries.add(new BarEntry(5, GetEvenuebyMonth(5)));
        barEntries.add(new BarEntry(6, GetEvenuebyMonth(6)));
        barEntries.add(new BarEntry(7, GetEvenuebyMonth(7)));
        barEntries.add(new BarEntry(8, GetEvenuebyMonth(8)));
        barEntries.add(new BarEntry(9, GetEvenuebyMonth(9)));
        barEntries.add(new BarEntry(10, GetEvenuebyMonth(10)));
        barEntries.add(new BarEntry(11, GetEvenuebyMonth(11)));
        barEntries.add(new BarEntry(12, GetEvenuebyMonth(12)));
        return  barEntries;
    }

    public Long GetExpenditurebyMonth(int month) {
        Long expenditureMoney = 0l;
        String revenueQuery = "SELECT " + REVENUE_EXPENDITURE_DATE + ", SUM(" + REVENUE_EXPENDITURE_MONEY + ")" +
                " FROM " + TABLE_REVENUE_EXPENDITURE_DETAIL +
                " WHERE " + FORM_ID + " = 2" +
                " GROUP BY " + REVENUE_EXPENDITURE_DATE +
                " ORDER BY " + REVENUE_EXPENDITURE_DATE + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(revenueQuery, null);
        if(cursor.moveToFirst()){
            do{
                String date = cursor.getString(0);
                int revenuemonth = Integer.valueOf(date.substring(3, 5));
                if(revenuemonth == month){
                    expenditureMoney += cursor.getLong(1);
                }
            }while (cursor.moveToNext());
        }
        return expenditureMoney;
    }

    public ArrayList<BarEntry>  GetExpenditureBarEntryByMonth(){
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1, GetExpenditurebyMonth(1)));
        barEntries.add(new BarEntry(2, GetExpenditurebyMonth(2)));
        barEntries.add(new BarEntry(3, GetExpenditurebyMonth(3)));
        barEntries.add(new BarEntry(4, GetExpenditurebyMonth(4)));
        barEntries.add(new BarEntry(5, GetExpenditurebyMonth(5)));
        barEntries.add(new BarEntry(6, GetExpenditurebyMonth(6)));
        barEntries.add(new BarEntry(7, GetExpenditurebyMonth(7)));
        barEntries.add(new BarEntry(8, GetExpenditurebyMonth(8)));
        barEntries.add(new BarEntry(9, GetExpenditurebyMonth(9)));
        barEntries.add(new BarEntry(10, GetExpenditurebyMonth(10)));
        barEntries.add(new BarEntry(11, GetExpenditurebyMonth(11)));
        barEntries.add(new BarEntry(12, GetExpenditurebyMonth(12)));
        return  barEntries;
    }

    public List<DayData> getAllDayData(){
        List<DayData> dayDataList = new ArrayList<>();

        // Get Day Date with revenue
        String revenueQuery = "SELECT " + REVENUE_EXPENDITURE_DATE + ", SUM(" + REVENUE_EXPENDITURE_MONEY + ")" +
                        " FROM " + TABLE_REVENUE_EXPENDITURE_DETAIL +
                        " WHERE " + FORM_ID + " = 1" +
                        " GROUP BY " + REVENUE_EXPENDITURE_DATE +
                        " ORDER BY " + REVENUE_EXPENDITURE_DATE + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(revenueQuery, null);

        if(cursor.moveToFirst())
        {
            int position = 0;
            do{
                DayData dayData = new DayData();

                String date = cursor.getString(0);
                String dayOfMonth = date.substring(0, 2);
                String month = date.substring(3, 5);
                String year = date.substring(6, 10);
                String month_year = date.substring(3, 10);
                Date dateFormat = Date.valueOf(year + "-" + month + "-" + dayOfMonth);
                String dayOfWeek = convertDayOfWeek((String) DateFormat.format("EEEE", dateFormat));

                dayData.setDayOfMonth(dayOfMonth);
                dayData.setDayofWeek(dayOfWeek);
                dayData.setMonthYear(month_year);
                dayData.setRevenue(cursor.getLong(1));
                dayData.setExpenditure(0L);
                dayData.setDate(date);
                dayAddedPosition.put(date, position);

                dayDataList.add(dayData);

                position++;
            }while (cursor.moveToNext());
        }

        // Insert expenditure of each Day Data

        String expenditureQuery = "SELECT " + REVENUE_EXPENDITURE_DATE + ", SUM(" + REVENUE_EXPENDITURE_MONEY + ")" +
                " FROM " + TABLE_REVENUE_EXPENDITURE_DETAIL +
                " WHERE " + FORM_ID + " = 2" +
                " GROUP BY " + REVENUE_EXPENDITURE_DATE +
                " ORDER BY " + REVENUE_EXPENDITURE_DATE + " DESC";

        cursor = db.rawQuery(expenditureQuery, null);

        if(cursor.moveToFirst())
        {
            do{
                String date = cursor.getString(0);
                Long expenditureMoney = cursor.getLong(1);

                if(dayAddedPosition.containsKey(date)){
                    dayDataList.get(dayAddedPosition.get(date)).setExpenditure(expenditureMoney);
                }else{

                    String dayOfMonth = date.substring(0, 2);
                    String month = date.substring(3, 5);
                    String year = date.substring(6, 10);
                    String month_year = date.substring(3, 10);
                    Date dateFormat = Date.valueOf(year + "-" + month + "-" + dayOfMonth);
                    String dayOfWeek = convertDayOfWeek((String) DateFormat.format("EEEE", dateFormat));

                    DayData dayData = new DayData();
                    dayData.setDayOfMonth(dayOfMonth);
                    dayData.setDayofWeek(dayOfWeek);
                    dayData.setMonthYear(month_year);
                    dayData.setRevenue(0L);
                    dayData.setExpenditure(expenditureMoney);
                    dayData.setDate(date);

                    dayDataList.add(dayData);
                }

            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // Sort Day Data List
        Collections.sort(dayDataList);

        return dayDataList;
    }

    private String convertDayOfWeek(String day){
        switch (day){
            case "Monday":
                return "Thứ 2";
            case "Tuesday":
                return "Thứ 3";
            case "Wednesday":
                return "Thứ 4";
            case "Thursday":
                return "Thứ 5";
            case "Friday":
                return "Thứ 6";
            case "Saturday":
                return "Thứ 7";
            case "Sunday":
                return "Chủ Nhật";
        }
        return "";
    }

    public List<ItemDetailData> getItemDetailDateListByDate(String date){
        List<ItemDetailData> itemDetailDataList = new ArrayList<>();

        String query = "SELECT " + REVENUE_EXPENDITURE_ID + ", "+ CATEGORY_NAME + ", " + REVENUE_EXPENDITURE_NOTE + ", " + ACCOUNT_NAME + ", "+ REVENUE_EXPENDITURE_MONEY + ", re." + FORM_ID +
                " FROM " + TABLE_REVENUE_EXPENDITURE_DETAIL + " re INNER JOIN " + TABLE_CATEGORY + " c ON re." + CATEGORY_ID + "=c." + CATEGORY_ID +
                " INNER JOIN " + TABLE_ACCOUNT + " a ON re." + ACCOUNT_ID + "=a." + ACCOUNT_ID +
                " WHERE " + REVENUE_EXPENDITURE_DATE + " = '" + date + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst())
        {
            do{
                ItemDetailData itemDetailData = new ItemDetailData();

                itemDetailData.setID(cursor.getInt(0));
                itemDetailData.setCategory(cursor.getString(1));
                itemDetailData.setNote(cursor.getString(2));
                itemDetailData.setAccount(cursor.getString(3));
                itemDetailData.setMoney(cursor.getLong(4));
                itemDetailData.setFormID(cursor.getInt(5));

                itemDetailDataList.add(itemDetailData);

            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return itemDetailDataList;


    }

}
