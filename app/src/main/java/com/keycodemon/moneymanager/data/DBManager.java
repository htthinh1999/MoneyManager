package com.keycodemon.moneymanager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.keycodemon.moneymanager.model.Account;
import com.keycodemon.moneymanager.model.Category;
import com.keycodemon.moneymanager.model.Form;
import com.keycodemon.moneymanager.model.RevenueExpenditureDetail;

import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {
    private static final String DATEBASE_NAME="Quan_Ly_Thu_Chi";

    private static final String TABLE_FORM = "HINHTHUC";
    private static final String FORM_ID = "IDHinhThuc";
    private static final String FORM_NAME ="TenHinhThuc";

    private static final String TABLE_CATEGORY = "THELOAI";
    private static final String CATEGORY_ID = "IDTheLoai";
    private static final String CATEGORY_NAME = "TenTheLoai";

    private static final String TABLE_ACCOUNT = "TAIKHOAN";
    private static final String ACCOUNT_ID = "IDTaiKhoan";
    private static final String ACCOUNT_NAME ="TenTaiKhoan";
    private static final String ACCOUNT_BALANCE ="SoDu";

    private static final String TABLE_REVENUE_EXPENDITURE_DETAIL = "CHITIETTHUCHI";
    private static final String REVENUE_EXPENDITURE_ID = "IDThuChi";
    private static final String REVENUE_EXPENDITURE_MONEY = "SoTien";
    private static final String REVENUE_EXPENDITURE_NOTE = "GhiChu";
    private static final String REVENUE_EXPENDITURE_PERIODIC = "DinhKy";
    private static final String REVENUE_EXPENDITURE_DATE = "Ngay";

    private Context context;
    private static int VERSION = 1;

    private String Create_table_form = "CREATE TABLE "+TABLE_FORM+" ("+
            FORM_ID +" integer primary key, "+
            FORM_NAME +" TEXT) ";

    private  String Create_table_category = "CREATE TABLE "+TABLE_CATEGORY+" ("+
            CATEGORY_ID +" integer primary key, "+
            FORM_ID +" integer, "+
            CATEGORY_NAME +" TEXT, " +
            "FOREIGN KEY (IDHinhThuc) references HINHTHUC(IDHinhThuc)) ";

    private String Create_table_account = "CREATE TABLE "+TABLE_ACCOUNT+" (" +
            ACCOUNT_ID +" integer primary key, "+
            ACCOUNT_NAME +" TEXT, "+
            ACCOUNT_BALANCE +" REAL)" ;

    private String Create_table_RevenueExpenditureDetail = "CREATE TABLE "+TABLE_REVENUE_EXPENDITURE_DETAIL+" ("+
            REVENUE_EXPENDITURE_ID +" integer primary key, "+
            FORM_ID +" integer, "+
            CATEGORY_ID +" integer, "+
            ACCOUNT_ID +" integer, "+
            REVENUE_EXPENDITURE_MONEY +" REAL, "+
            REVENUE_EXPENDITURE_NOTE +" TEXT, " +
            REVENUE_EXPENDITURE_PERIODIC +" integer, " +
            REVENUE_EXPENDITURE_DATE +" DATE, " +
            "FOREIGN KEY ( "+FORM_ID+") REFERENCES "+TABLE_FORM+"("+FORM_ID+"), " +
            "FOREIGN KEY ( "+CATEGORY_ID+") REFERENCES "+TABLE_CATEGORY+"("+CATEGORY_ID+"), " +
            "FOREIGN KEY ( "+ACCOUNT_ID+") REFERENCES "+TABLE_ACCOUNT+"("+ACCOUNT_ID+"))";

    public DBManager(@Nullable Context context) {
        super(context, DATEBASE_NAME, null, VERSION);
        this.context = context;
        initData();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_table_form);
        db.execSQL(Create_table_category);
        db.execSQL(Create_table_account);
        db.execSQL(Create_table_RevenueExpenditureDetail);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // Add first data
    public void initData(){
        // Add account data
        boolean exist = false;
        List<Account> accountList = getAllAccount();
        for(Account acc: accountList){
            if(acc.getmAccountName().equals("Tiền mặt")){
                exist = true;
                break;
            }
        }
        if(!exist){
            addAccount(new Account("Tiền mặt", 0));
            addAccount(new Account("Tài khoản ngân hàng", 0));
            addAccount(new Account("Thẻ tín dụng", 0));
        }


        // Add form data
        exist = false;
        List<Form> formList = getAllForm();
        for(Form form: formList){
            if(form.getmFormName().equals("Thu")){
                exist = true;
                break;
            }
        }
        if(!exist){
            addForm(new Form("Thu"));
            addForm(new Form("Chi"));
        }


        // Add category data
        exist = false;
        List<Category> categoryList = getAllCategory();
        for(Category category: categoryList){
            if(category.getmCategoryName().equals("Trả thêm giờ")){
                exist = true;
                break;
            }
        }
        if(!exist){
            addCategory(new Category(1, "Trả thêm giờ"));
            addCategory(new Category(1, "Tiền lương"));
            addCategory(new Category(1, "Tiền cấp"));
            addCategory(new Category(1, "Tiền thưởng"));
            addCategory(new Category(1, "Khác"));

            addCategory(new Category(2, "Ăn uống"));
            addCategory(new Category(2, "Sức khỏe"));
            addCategory(new Category(2, "Giải trí"));
            addCategory(new Category(2, "Sinh hoạt"));
            addCategory(new Category(2, "Áo quần"));
            addCategory(new Category(2, "Làm đẹp"));
            addCategory(new Category(2, "Giáo dục"));
            addCategory(new Category(2, "Sự kiện"));
            addCategory(new Category(2, "Đi chợ"));
            addCategory(new Category(2, "Khác"));
        }
    }

    public void addForm(Form form){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FORM_NAME, form.getmFormName());
        db.insert(TABLE_FORM,null, values);
        db.close();
    }

    public void addCategory(Category category){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FORM_ID, category.getmFormID());
        values.put(CATEGORY_NAME, category.getmCategoryName());
        db.insert(TABLE_CATEGORY,null, values);
        db.close();
    }

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

        values.put(FORM_ID,revenueExpenditureDetail.getmFormID());
        values.put(CATEGORY_ID,revenueExpenditureDetail.getmCategoryID());
        values.put(ACCOUNT_ID,revenueExpenditureDetail.getmAccountID());
        values.put(REVENUE_EXPENDITURE_MONEY,revenueExpenditureDetail.getmMoney());
        values.put(REVENUE_EXPENDITURE_NOTE,revenueExpenditureDetail.getmNote());
        values.put(REVENUE_EXPENDITURE_PERIODIC,revenueExpenditureDetail.getmPeriodic());
        values.put(REVENUE_EXPENDITURE_DATE,revenueExpenditureDetail.getmDate());
        db.insert(TABLE_REVENUE_EXPENDITURE_DETAIL,null,values);
        db.close();
    }

    public List<Form> getAllForm(){
        List<Form> listForm = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+ TABLE_FORM;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst())
        {
            do {
                Form form = new Form();
                form.setmFormID(cursor.getInt(0));
                form.setmFormName(cursor.getString(1));
                listForm.add(form);

            }while (cursor.moveToNext());
        }
        db.close();
        return listForm;
    }

    public List<Category> getAllCategory(){
        List<Category> listCategory = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+ TABLE_CATEGORY;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst())
        {
            do {
                Category category = new Category();
                category.setmCategoryID(cursor.getInt(0));
                category.setmFormID(cursor.getInt(1));
                category.setmCategoryName(cursor.getString(2));
                listCategory.add(category);

            }while (cursor.moveToNext());
        }
        db.close();
        return listCategory;
    }

    public List<Category> getExpenditureCategories(){
        List<Category> expenditureCategories = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+ TABLE_CATEGORY + " WHERE " + FORM_ID + "=2";
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst())
        {
            do {
                Category category = new Category();
                category.setmCategoryID(cursor.getInt(0));
                category.setmFormID(cursor.getInt(1));
                category.setmCategoryName(cursor.getString(2));
                expenditureCategories.add(category);

            }while (cursor.moveToNext());
        }
        db.close();
        return expenditureCategories;
    }

    public List<Category> getRevenueCategories(){
        List<Category> revenueCategories = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+ TABLE_CATEGORY + " WHERE " + FORM_ID + "=1";
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst())
        {
            do {
                Category category = new Category();
                category.setmCategoryID(cursor.getInt(0));
                category.setmFormID(cursor.getInt(1));
                category.setmCategoryName(cursor.getString(2));
                revenueCategories.add(category);

            }while (cursor.moveToNext());
        }
        db.close();
        return revenueCategories;
    }

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
        List<RevenueExpenditureDetail> revenueExpenditureDetailList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_REVENUE_EXPENDITURE_DETAIL + " ORDER BY " + REVENUE_EXPENDITURE_DATE + " DESC";

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
                revenueExpenditureDetail.setmDate(cursor.getString(7));

                revenueExpenditureDetailList.add(revenueExpenditureDetail);

            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return revenueExpenditureDetailList;
    }

    public Form getFormById(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FORM, new String[] { FORM_ID,
                        FORM_NAME}, FORM_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Form form = new Form(cursor.getInt(1),cursor.getString(2));
        cursor.close();
        db.close();
        return form;
    }

    public Category getCategoryById(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CATEGORY, new String[] { CATEGORY_ID,
                        FORM_ID,CATEGORY_NAME}, CATEGORY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Category category = new Category(cursor.getInt(1),cursor.getInt(2),cursor.getString(3));
        cursor.close();
        db.close();
        return category;
    }

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
                cursor.getInt(6),
                cursor.getString(7)
        );
        cursor.close();
        db.close();
        return revenueExpenditureDetail;
    }

    public int updateForm(Form form){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FORM_NAME,form.getmFormName());
        return db.update(TABLE_FORM,contentValues,FORM_ID+"=?",new String[]{String.valueOf(form.getmFormID())});
    }
    public int updateCategory(Category category){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FORM_ID,category.getmFormID());
        contentValues.put(CATEGORY_NAME,category.getmCategoryName());
        return db.update(TABLE_CATEGORY,contentValues,CATEGORY_ID+"=?",new String[]{String.valueOf(category.getmCategoryID())});
    }

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
        values.put(REVENUE_EXPENDITURE_DATE, revenueExpenditureDetail.getmDate());

        return db.update(TABLE_REVENUE_EXPENDITURE_DETAIL,values, REVENUE_EXPENDITURE_ID + " =?", new String[]{String.valueOf(revenueExpenditureDetail.getmRevenueExpenditureID())});
    }

    public int deleteForm(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_FORM,FORM_ID+"=?",new String[]{String.valueOf(id)});
    }

    public int deleteCategory(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CATEGORY,CATEGORY_ID+"=?",new String[]{String.valueOf(id)});
    }

    public int deleteAccount(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_ACCOUNT,ACCOUNT_ID+" =?", new String[]{String.valueOf(id)});
    }

    public int deleteRevenueExpenditureDetail(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_REVENUE_EXPENDITURE_DETAIL,REVENUE_EXPENDITURE_ID+" =?", new String[]{String.valueOf(id)});
    }

}
