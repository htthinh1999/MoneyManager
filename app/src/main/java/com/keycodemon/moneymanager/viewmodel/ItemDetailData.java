package com.keycodemon.moneymanager.viewmodel;

public class ItemDetailData {
    private String category, note, account;
    private Long money;
    private int formID;

    public ItemDetailData() {
    }

    public ItemDetailData(String category, String note, String account, Long money, int formID) {
        this.category = category;
        this.note = note;
        this.money = money;
        this.account = account;
        this.formID = formID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public int getFormID() {
        return formID;
    }

    public void setFormID(int formID) {
        this.formID = formID;
    }
}
