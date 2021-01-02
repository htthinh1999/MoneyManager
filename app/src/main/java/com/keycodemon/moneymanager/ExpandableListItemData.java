package com.keycodemon.moneymanager;

public class ExpandableListItemData {
    private String groupItem, item, wallet, expenditure;

    public String getGroupItem() {
        return groupItem;
    }

    public String getItem() {
        return item;
    }

    public String getWallet() {
        return wallet;
    }

    public String getExpenditure() {
        return expenditure;
    }

    public ExpandableListItemData(String groupItem, String item, String wallet, String expenditure) {
        this.groupItem = groupItem;
        this.item = item;
        this.wallet = wallet;
        this.expenditure = expenditure;

    }

}
