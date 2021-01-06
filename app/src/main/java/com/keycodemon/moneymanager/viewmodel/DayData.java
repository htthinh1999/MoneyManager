package com.keycodemon.moneymanager.viewmodel;

import java.util.List;

public class DayData {
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
}
