package com.keycodemon.moneymanager.model;

public class Form {
    private int mFormID;
    private String mFormName;

    public Form() {
    }

    public Form(String mFormName) {
        this.mFormName = mFormName;
    }

    public Form(int mFormID, String mFormName) {
        this.mFormID = mFormID;
        this.mFormName = mFormName;
    }

    public int getmFormID() {
        return mFormID;
    }

    public void setmFormID(int mFormID) {
        this.mFormID = mFormID;
    }

    public String getmFormName() {
        return mFormName;
    }

    public void setmFormName(String mFormName) {
        this.mFormName = mFormName;
    }
}
