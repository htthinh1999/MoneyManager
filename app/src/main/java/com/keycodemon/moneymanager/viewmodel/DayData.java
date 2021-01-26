package com.keycodemon.moneymanager.viewmodel;

import java.sql.Date;
import java.util.List;

public class DayData implements Comparable<DayData>{
    private String dayOfMonth, monthYear, dayofWeek, date;
    private Long revenue, expenditure;
    private List<ItemDetailData> itemDetailDataList;

    public DayData(){}

    public DayData(String dayOfMonth, String monthYear, String dayofWeek, Long revenue, Long expenditure, String date, List<ItemDetailData> itemDetailDataList) {
        this.dayOfMonth = dayOfMonth;
        this.monthYear = monthYear;
        this.dayofWeek = dayofWeek;
        this.revenue = revenue;
        this.expenditure = expenditure;
        this.date = date;
        this.itemDetailDataList = itemDetailDataList;
    }

    public String getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(String dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    public String getDayofWeek() {
        return dayofWeek;
    }

    public void setDayofWeek(String dayofWeek) {
        this.dayofWeek = dayofWeek;
    }

    public Long getRevenue() {
        return revenue;
    }

    public void setRevenue(Long revenue) {
        this.revenue = revenue;
    }

    public Long getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(Long expenditure) {
        this.expenditure = expenditure;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ItemDetailData> getItemDetailDataList() {
        return itemDetailDataList;
    }

    public void setItemDetailDataList(List<ItemDetailData> itemDetailDataList) {
        this.itemDetailDataList = itemDetailDataList;
    }

    public int itemCount(){
        return itemDetailDataList.size();
    }

    @Override
    public int compareTo(DayData dayData) {

        int day = Integer.valueOf(date.substring(0, 2));
        int month = Integer.valueOf(date.substring(3, 5));
        int year = Integer.valueOf(date.substring(6, 10));

        Date dateFormat = new Date(year, month, day);

        day = Integer.valueOf(dayData.getDate().substring(0, 2));
        month = Integer.valueOf(dayData.getDate().substring(3, 5));
        year = Integer.valueOf(dayData.getDate().substring(6, 10));
        Date dayDataFormat = new Date(year, month, day);

        if(dateFormat.after(dayDataFormat)){
            return -1;
        }else if(dateFormat.before(dayDataFormat)){
            return 1;
        }
        return 0;
    }
}
