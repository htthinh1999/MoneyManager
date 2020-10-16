package com.keycodemon.moneymanager.data.model;

public class RevenueExpenditureDetail {
    private int mRevenueExpenditureID;
    private int mFormID;
    private int mCategoryID;
    private int mAccountID;
    private float mMoney;
    private String mNote;
    private int mPeriodic;

    public RevenueExpenditureDetail() {
    }

    public RevenueExpenditureDetail(int mFormID, int mCategoryID, int mAccountID, float mMoney, String mNote, int mPeriodic) {
        this.mFormID = mFormID;
        this.mCategoryID = mCategoryID;
        this.mAccountID = mAccountID;
        this.mMoney = mMoney;
        this.mNote = mNote;
        this.mPeriodic = mPeriodic;
    }

    public RevenueExpenditureDetail(int mRevenueExpenditureID, int mFormID, int mCategoryID, int mAccountID, float mMoney, String mNote, int mPeriodic) {
        this.mRevenueExpenditureID = mRevenueExpenditureID;
        this.mFormID = mFormID;
        this.mCategoryID = mCategoryID;
        this.mAccountID = mAccountID;
        this.mMoney = mMoney;
        this.mNote = mNote;
        this.mPeriodic = mPeriodic;
    }

    public int getmRevenueExpenditureID() {
        return mRevenueExpenditureID;
    }

    public void setmRevenueExpenditureID(int mRevenueExpenditureID) {
        this.mRevenueExpenditureID = mRevenueExpenditureID;
    }

    public int getmFormID() {
        return mFormID;
    }

    public void setmFormID(int mFormID) {
        this.mFormID = mFormID;
    }

    public int getmCategoryID() {
        return mCategoryID;
    }

    public void setmCategoryID(int mCategoryID) {
        this.mCategoryID = mCategoryID;
    }

    public int getmAccountID() {
        return mAccountID;
    }

    public void setmAccountID(int mAccountID) {
        this.mAccountID = mAccountID;
    }

    public float getmMoney() {
        return mMoney;
    }

    public void setmMoney(float mMoney) {
        this.mMoney = mMoney;
    }

    public String getmNote() {
        return mNote;
    }

    public void setmNote(String mNote) {
        this.mNote = mNote;
    }

    public int getmPeriodic() {
        return mPeriodic;
    }

    public void setmPeriodic(int mPeriodic) {
        this.mPeriodic = mPeriodic;
    }
}
