package com.keycodemon.moneymanager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.keycodemon.moneymanager.data.model.Account;
import com.keycodemon.moneymanager.data.model.RevenueExpenditureDetail;

import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "QUANLYTHUCHI";

    private static final String TABLE_ACCOUNT = "TAIKHOAN";
    private static final String ACCOUNT_ID = "IDTaiKhoan";
    private static final String ACCOUNT_NAME ="TenTaiKhoan";
    private static final String ACCOUNT_BALANCE ="SoDu";

    private static final String TABLE_REVENUE_EXPENDITURE_DETAIL = "CHITIETTHUCHI";
    private static final String REVENUE_EXPENDITURE_ID = "IDThuChi";
    private static final String FORM_ID = "IDHinhThuc";
    private static final String CATEGORY_ID = "IDTheLoai";
    //private static final String ACCOUNT_ID = "IDTaiKhoan";
    private static final String REVENUE_EXPENDITURE_MONEY = "SoTien";
    private static final String REVENUE_EXPENDITURE_NOTE = "GhiChu";
    private static final String REVENUE_EXPENDITURE_PERIODIC = "DinhKy";

    private static final String TABLE_FORM = "HINHTHUC";
    private static final String TABLE_CATEGORY = "THELOAI";

    private Context context;


    public DBManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Create_table_account_querry = "CREATE TABLE "+TABLE_ACCOUNT+" (" +
                ACCOUNT_ID +" integer primary key, "+
                ACCOUNT_NAME +" TEXT, "+
                ACCOUNT_BALANCE +" REAL)" ;

        String Create_table_RevenueExpenditureDetail_querry = "CREATE TABLE "+TABLE_REVENUE_EXPENDITURE_DETAIL+" ("+
                REVENUE_EXPENDITURE_ID +" integer primary key, "+
                FORM_ID +" integer, "+
                CATEGORY_ID +" integer, "+
                ACCOUNT_ID +" integer, "+
                REVENUE_EXPENDITURE_MONEY +" REAL, "+
                REVENUE_EXPENDITURE_NOTE +" TEXT, FOREIGN KEY ( "+FORM_ID+") REFERENCES "+TABLE_FORM+"("+FORM_ID+")," +
                "FOREIGN KEY ( "+CATEGORY_ID+") REFERENCES "+TABLE_CATEGORY+"("+CATEGORY_ID+")," +
                "FOREIGN KEY ( "+ACCOUNT_ID+") REFERENCES "+TABLE_ACCOUNT+"("+ACCOUNT_ID+"))";
                         
        db.execSQL(Create_table_account_querry);
        db.execSQL(Create_table_RevenueExpenditureDetail_querry);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ACCOUNT);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_REVENUE_EXPENDITURE_DETAIL);

        onCreate(db);
    }
//------------------------ADD--------------------------------//
    public void addAccount(Account account)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ACCOUNT_NAME, account.getmAccountName());
        values.put(ACCOUNT_BALANCE, account.getmBalance());

        db.insert(TABLE_ACCOUNT,null,values);
        db.close();
    }

    public void addRevenueExpenditureDetail(RevenueExpenditureDetail revenueExpenditureDetail)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(REVENUE_EXPENDITURE_ID,revenueExpenditureDetail.getmRevenueExpenditureID());
        values.put(FORM_ID,revenueExpenditureDetail.getmFormID());
        values.put(CATEGORY_ID,revenueExpenditureDetail.getmCategoryID());
        values.put(ACCOUNT_ID,revenueExpenditureDetail.getmAccountID());
        values.put(REVENUE_EXPENDITURE_MONEY,revenueExpenditureDetail.getmMoney());
        values.put(REVENUE_EXPENDITURE_NOTE,revenueExpenditureDetail.getmNote());

        db.insert(TABLE_REVENUE_EXPENDITURE_DETAIL,null,values);
        db.close();
    }
    //-----------------*-----------------------//

    //-----------------------------GET BY ID-------------------------------//

    public Account getAccountByID(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ACCOUNT, new String[]{ACCOUNT_ID,ACCOUNT_NAME,ACCOUNT_BALANCE},
                ACCOUNT_ID +"=?", new String[]{String.valueOf(id)},null,null,null,null);
                if(cursor != null){
                    cursor.moveToFirst();
                }
                Account account = new Account(cursor.getString(1),
                 cursor.getFloat(2)
                 );
        cursor.close();
        db.close();
        return account;
    }

    public RevenueExpenditureDetail getRevenueExpenditureDetailByID(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_REVENUE_EXPENDITURE_DETAIL, new String[]{REVENUE_EXPENDITURE_ID,
        FORM_ID,CATEGORY_ID,ACCOUNT_ID,REVENUE_EXPENDITURE_MONEY,REVENUE_EXPENDITURE_NOTE,REVENUE_EXPENDITURE_PERIODIC},
                REVENUE_EXPENDITURE_ID +"=?", new String[]{String.valueOf(id)}, null,null,null,null);
                if (cursor != null){
                    cursor.moveToFirst();
                }
                RevenueExpenditureDetail revenueExpenditureDetail = new RevenueExpenditureDetail(cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getInt(3),
                        cursor.getFloat(4),
                        cursor.getString(5),
                        cursor.getInt(6)
                        );
        cursor.close();
        db.close();
        return revenueExpenditureDetail;
    }

    //-------------------------*------------------------------//

    //---------------------------GET ALL--------------------------------//

    public List<Account> getAllAccount(){
        List<Account> accountList = new ArrayList<Account>();

        String query = "SELECT * FROM " + TABLE_ACCOUNT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst())
        {
            do{
                Account account = new Account();
                account.setmAccountID(cursor.getInt(0));
                account.setmAccountName(cursor.getString(1));
                account.setmBalance(cursor.getFloat(2));
                accountList.add(account);

            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return accountList;
    }
    
    public List<RevenueExpenditureDetail> getAllRevenueExpenditureDetail(){
        List<RevenueExpenditureDetail> revenueExpenditureDetailList = new ArrayList<RevenueExpenditureDetail>();

        String query = "SELECT * FROM " + TABLE_REVENUE_EXPENDITURE_DETAIL;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst())
        {
            do{
                RevenueExpenditureDetail revenueExpenditureDetail = new RevenueExpenditureDetail();
                revenueExpenditureDetail.setmRevenueExpenditureID(cursor.getInt(0));
                revenueExpenditureDetail.setmFormID(cursor.getInt(1));
                revenueExpenditureDetail.setmCategoryID(cursor.getInt(2));
                revenueExpenditureDetail.setmAccountID(cursor.getInt(3));
                revenueExpenditureDetail.setmMoney(cursor.getFloat(4));
                revenueExpenditureDetail.setmNote(cursor.getString(5));
                revenueExpenditureDetail.setmPeriodic(cursor.getInt(6));

                revenueExpenditureDetailList.add(revenueExpenditureDetail);

            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return revenueExpenditureDetailList;
    }

    //-----------------------------*-----------------------------//

    //------------------------------UPDATE------------------------------//

    public int updateAccount(Account account){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ACCOUNT_NAME, account.getmAccountName());
        values.put(ACCOUNT_BALANCE,account.getmBalance());

       return db.update(TABLE_ACCOUNT,values, ACCOUNT_ID + " =?", new String[]{String.valueOf(account.getmAccountID())});
    }

    public int updateRevenueExpenditureDetail(RevenueExpenditureDetail revenueExpenditureDetail){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FORM_ID, revenueExpenditureDetail.getmFormID());
        values.put(CATEGORY_ID, revenueExpenditureDetail.getmCategoryID());
        values.put(ACCOUNT_ID, revenueExpenditureDetail.getmAccountID());
        values.put(REVENUE_EXPENDITURE_MONEY, revenueExpenditureDetail.getmMoney());
        values.put(REVENUE_EXPENDITURE_NOTE, revenueExpenditureDetail.getmNote());
        values.put(REVENUE_EXPENDITURE_PERIODIC, revenueExpenditureDetail.getmPeriodic());

        return db.update(TABLE_REVENUE_EXPENDITURE_DETAIL,values, REVENUE_EXPENDITURE_ID + " =?", new String[]{String.valueOf(revenueExpenditureDetail.getmRevenueExpenditureID())});
    }

    //-----------------------*----------------------//

    //------------------------------DELETE-----------------------------------------//

    public int deleteAccount(Account account){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_ACCOUNT,ACCOUNT_ID+" =?", new String[]{String.valueOf(account.getmAccountID())});
    }

    public int deleteRevenueExpenditureDetail(RevenueExpenditureDetail revenueExpenditureDetail){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_REVENUE_EXPENDITURE_DETAIL,REVENUE_EXPENDITURE_ID+" =?", new String[]{String.valueOf(revenueExpenditureDetail.getmRevenueExpenditureID())});
    }

    //----------------------------*--------------------------------//

}
