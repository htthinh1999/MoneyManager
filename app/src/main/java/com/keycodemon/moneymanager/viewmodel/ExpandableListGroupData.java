package com.keycodemon.moneymanager.viewmodel;

import com.keycodemon.moneymanager.viewmodel.ExpandableListItemData;

import java.util.List;

public class ExpandableListGroupData {
    private String dayOfMonth, monthYear, dayofWeek, revenue, expenditure;
    private List<ExpandableListItemData> expandableListItemDataList;

    public ExpandableListGroupData(String dayOfMonth, String monthYear, String dayofWeek, String revenue, String expenditure, List<ExpandableListItemData> expandableListItemDataList) {
        this.dayOfMonth = dayOfMonth;
        this.monthYear = monthYear;
        this.dayofWeek = dayofWeek;
        this.revenue = revenue;
        this.expenditure = expenditure;
        this.expandableListItemDataList = expandableListItemDataList;
    }

    public String getDayOfMonth() {
        return dayOfMonth;
    }

    public String getMonthYear() {
        return monthYear;
    }

    public String getDayofWeek() {
        return dayofWeek;
    }

    public String getRevenue() {
        return revenue;
    }

    public String getExpenditure() {
        return expenditure;
    }

    public List<ExpandableListItemData> getExpandableListItemDataList() {
        return expandableListItemDataList;
    }

    public int itemCount(){
        return expandableListItemDataList.size();
    }
}
