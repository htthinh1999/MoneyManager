package com.keycodemon.moneymanager.Model;

public class Category {
    private int mCategoryID;
    private int mFormID;
    private String mCategoryName;

    public Category() {
    }

    public Category(int mFormID, String mCategoryName) {
        this.mFormID = mFormID;
        this.mCategoryName = mCategoryName;
    }

    public Category(int mCategoryID, int mFormID, String mCategoryName) {
        this.mCategoryID = mCategoryID;
        this.mFormID = mFormID;
        this.mCategoryName = mCategoryName;
    }

    public int getmCategoryID() {
        return mCategoryID;
    }

    public void setmCategoryID(int mCategoryID) {
        this.mCategoryID = mCategoryID;
    }

    public int getmFormID() {
        return mFormID;
    }

    public void setmFormID(int mFormID) {
        this.mFormID = mFormID;
    }

    public String getmCategoryName() {
        return mCategoryName;
    }

    public void setmCategoryName(String mCategoryName) {
        this.mCategoryName = mCategoryName;
    }
}
