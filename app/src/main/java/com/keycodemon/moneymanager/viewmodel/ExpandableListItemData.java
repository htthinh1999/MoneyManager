package com.keycodemon.moneymanager.viewmodel;

public class ExpandableListItemData {
    private String groupItem, item, wallet, revenue, expenditure;

    public String getGroupItem() {
        return groupItem;
    }

    public String getItem() {
        return item;
    }

    public String getWallet() {
        return wallet;
    }

    public String getRevenue(){
        return revenue;
    }

    public String getExpenditure() {
        return expenditure;
    }

    public ExpandableListItemData(String groupItem, String item, String wallet, String revenue, String expenditure) {
        this.groupItem = groupItem;
        this.item = item;
        this.wallet = wallet;
        this.revenue = revenue;
        this.expenditure = expenditure;

    }

}
