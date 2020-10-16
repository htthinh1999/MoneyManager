package com.keycodemon.moneymanager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.keycodemon.moneymanager.model.Category;
import com.keycodemon.moneymanager.model.Form;

import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {

    private static final String DATEBASE_NAME="Quan_Ly_Thu_Chi";
    private static final String TABLE_FORM = "HINHTHUC";
    private static final String TABLE_CATEGORY = "THELOAI";
    private static final String FORM_ID = "IDHinhThuc";
    private static final String CATEGORY_ID = "IDTheLoai";
    private static final String FORM_NAME ="TenHinhThuc";
    private static final String CATEGORY_NAME = "TenTheLoai";
    private static int VERSION = 1;

    private Context context;

    private String Create_table_form = "CREATE TABLE "+TABLE_FORM+" ("+
            FORM_ID +" integer primary key, "+
            FORM_NAME +" TEXT) ";

    private  String Create_table_category = "CREATE TABLE "+TABLE_CATEGORY+" ("+
            CATEGORY_ID +" integer primary key, "+
            FORM_ID +" integer, "+
            CATEGORY_NAME +" TEXT, FOREIGN KEY (IDHinhThuc) references HINHTHUC(IDHinhThuc)) ";

    public DBManager(@Nullable Context context) {
        super(context, DATEBASE_NAME, null, VERSION);
        this.context=context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Create_table_form);
        sqLiteDatabase.execSQL(Create_table_category);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addForm(Form form){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FORM_NAME, form.getmFormName());
        db.insert(TABLE_FORM,null, values);
        db.close();
    }

    public void addCategory(Form form){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FORM_ID, form.getmFormID());
        values.put(CATEGORY_NAME, form.getmFormName());
        db.insert(TABLE_CATEGORY,null, values);
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
    public int deleteForm(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_FORM,FORM_ID+"=?",new String[]{String.valueOf(id)});
    }

    public int deleteCategory(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CATEGORY,CATEGORY_ID+"=?",new String[]{String.valueOf(id)});
    }



}
