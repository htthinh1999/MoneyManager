package com.keycodemon.moneymanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import com.keycodemon.moneymanager.data.DBManager;
import com.keycodemon.moneymanager.model.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener, View.OnLongClickListener, AdapterView.OnItemClickListener {

    DBManager dbManager;

    Button btnRevenue;
    Button btnExpenditure;
    Button btnSave;
    Button btnDelete;
    Button btnCancel;

    AutoCompleteTextView etCategoryName;
    TableLayout tableLayout;

    String[] revenueCategories;
    String[] expenditureCategories;
    String[] categories;

    int formID = 2;
    int idOfCategory = 0;

    Map<String, Integer> categoryID = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        init();
    }


    private void init(){
        initWidget();
        initData();
        initInAppKeyboard();
    }

    private void initWidget(){
        // Hide action bar
        if(getSupportActionBar().isShowing()){
            getSupportActionBar().hide();
        }

        btnRevenue = findViewById(R.id.btnRevenue);
        btnExpenditure = findViewById(R.id.btnExpenditure);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);

        etCategoryName = findViewById(R.id.etCategoryName);

        tableLayout = findViewById(R.id.tableLayout);

        btnRevenue.setOnClickListener(this);
        btnExpenditure.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        etCategoryName.setOnTouchListener(this);
        etCategoryName.setOnItemClickListener(this);

        // Show data to view when item was clicked
        if(getIntent().hasExtra("formID")){

            if(getIntent().getIntExtra("formID", 2) == 1){
                changeButtonsColor("THU");
                categories = revenueCategories;
                formID = 1;
            }

//            etTitle.setText(getIntent().getStringExtra("note"));

//            btnDelete.setVisibility(View.VISIBLE);
        }
    }

    private void initData(){
        dbManager = new DBManager(this);

        // Get revenue category data
        List<Category> revenueCategoryList = dbManager.getRevenueCategories();
        List<String> revenueCategoryNames = new ArrayList<>();
        for(Category category: revenueCategoryList){
            revenueCategoryNames.add(category.getmCategoryName());
        }
        revenueCategories = new String[revenueCategoryNames.size()];
        revenueCategories = revenueCategoryNames.toArray(revenueCategories);

        // Get expenditure category data
        List<Category> expenditureCategoryList = dbManager.getExpenditureCategories();
        List<String> expenditureCategoryNames = new ArrayList<>();
        for(Category category: expenditureCategoryList){
            expenditureCategoryNames.add(category.getmCategoryName());
        }
        expenditureCategories = new String[expenditureCategoryNames.size()];
        expenditureCategories = expenditureCategoryNames.toArray(expenditureCategories);

        // Get all category data
        List<Category> categoryList = dbManager.getAllCategory();
        for(Category category: categoryList){
            categoryID.put(category.getmCategoryName(), category.getmCategoryID());
        }
        // First categories
        categories = expenditureCategories;

        setItemAutoTextView();

    }

    private void changeButtonsColor(String buttonName){
        if(buttonName.equals("THU")){
            int colorPos = ContextCompat.getColor(this, R.color.colorRevenue);
            btnRevenue.setTextColor(colorPos);
            btnRevenue.setBackground(ContextCompat.getDrawable(this,R.drawable.btn_revenue));
            btnExpenditure.setTextColor(Color.DKGRAY);
            btnExpenditure.setBackground(ContextCompat.getDrawable(this,R.drawable.btn_no_select));
            btnSave.setBackground(ContextCompat.getDrawable(this,R.drawable.btn_fill_revenue));
            DrawableCompat.setTint(etCategoryName.getBackground(), colorPos);

        }else{
            int colorPos = ContextCompat.getColor(this, R.color.colorExpenditure);
            btnExpenditure.setTextColor(colorPos);
            btnExpenditure.setBackground(ContextCompat.getDrawable(this,R.drawable.btn_expenditure));
            btnRevenue.setTextColor(Color.DKGRAY);
            btnRevenue.setBackground(ContextCompat.getDrawable(this,R.drawable.btn_no_select));
            btnSave.setBackground(ContextCompat.getDrawable(this,R.drawable.btn_fill_expenditure));
            DrawableCompat.setTint(etCategoryName.getBackground(), colorPos);
        }
    }

    private void initInAppKeyboard(){
        etCategoryName.requestFocus();
//        setUpKeyboard(accounts);
    }

    private void setItemAutoTextView(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, categories);
        etCategoryName.setAdapter(adapter);
    }

    private void saveData(){
        String categoryName = etCategoryName.getText().toString();
        if( idOfCategory == 0){
            Category category = new Category(formID, categoryName);
            dbManager.addCategory(category);
        }else
        {
            Category category = new Category( idOfCategory , formID, categoryName);
            dbManager.updateCategory(category);
        }
    }

    private void deleteData() {
//        int id = getIntent().getIntExtra("id", 0);
        dbManager.deleteCategory(idOfCategory);
    }

    private boolean validInput(){
        if(String.valueOf(etCategoryName.getText()).isEmpty()){
            return false;
        }
        return true;
    }

    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Xóa")
                .setMessage("Bạn muốn xóa thể loại này?")
//                .setIcon(R.drawable.delete)

                .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        deleteData();
                        setResult(RESULT_OK);
                        finish();
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;
    }

    @Override
    public void onClick(View v) {
        String buttonText = ((Button) v).getText().toString();
        switch (v.getId()){
            case R.id.btnRevenue:
                changeButtonsColor(buttonText);
                categories = revenueCategories;
                etCategoryName.setText("");
                etCategoryName.requestFocus();
                formID = 1;
                setItemAutoTextView();
                break;
            case R.id.btnExpenditure:
                changeButtonsColor(buttonText);
                categories = expenditureCategories;
                etCategoryName.setText("");
                etCategoryName.requestFocus();
                formID = 2;
                setItemAutoTextView();
                break;
            case R.id.btnSave:
                if(validInput()){
                    saveData();
                    setResult(RESULT_OK);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Bạn cần phải nhập đầy đủ thông tin!", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnDelete:
                if(validInput()) {
                    AlertDialog diaBox = AskOption();
                    diaBox.show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Không có gì để xóa!", Toast.LENGTH_LONG).show();
                }
//                setResult(RESULT_OK);
//                finish();
//                deleteData();

                break;
            case R.id.btnCancel:
                setResult(RESULT_OK);
                finish();
                break;
            default:

                    EditText et = (EditText) CategoryActivity.this.getCurrentFocus();
                    et.setText(buttonText);


                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            switch (v.getId()){
//                case R.id.etAccount:
//                   setUpKeyboard(accounts);
//                   hideSoftKeyboard();
//                    break;
                case R.id.etCategoryName:
                    etCategoryName.showDropDown();
//                    setUpKeyboard(new String[]{});
                    break;
            }
        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String itemName = categories[position];
//        etCategoryName.setText( categories[position] );
//        Toast.makeText(this, accounts[position], Toast.LENGTH_SHORT).show();

        idOfCategory = categoryID.get(itemName);
    }

}