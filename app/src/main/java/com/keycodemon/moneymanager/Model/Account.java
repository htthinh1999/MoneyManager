package com.keycodemon.moneymanager.Model;

public class Account {
    private int mAccountID;
    private String mAccountName;
    private float mBalance;

    public Account() {
    }

    public Account(String mAccountName, float mBalance) {
        this.mAccountName = mAccountName;
        this.mBalance = mBalance;
    }

    public Account(int mAccountID, String mAccountName, float mBalance) {
        this.mAccountID = mAccountID;
        this.mAccountName = mAccountName;
        this.mBalance = mBalance;
    }

    public int getmAccountID() {
        return mAccountID;
    }

    public void setmAccountID(int mAccountID) {
        this.mAccountID = mAccountID;
    }

    public String getmAccountName() {
        return mAccountName;
    }

    public void setmAccountName(String mAccountName) {
        this.mAccountName = mAccountName;
    }

    public float getmBalance() {
        return mBalance;
    }

    public void setmBalance(float mBalance) {
        this.mBalance = mBalance;
    }
}
