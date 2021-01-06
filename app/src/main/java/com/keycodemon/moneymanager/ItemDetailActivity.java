package com.keycodemon.moneymanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.app.DatePickerDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.keycodemon.moneymanager.data.DBManager;
import com.keycodemon.moneymanager.model.Account;
import com.keycodemon.moneymanager.model.Category;
import com.keycodemon.moneymanager.model.RevenueExpenditureDetail;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemDetailActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener, View.OnLongClickListener {

    DBManager dbManager;

    Button btnRevenue;
    Button btnExpenditure;
    Button btnSave;
    Button btnCancel;
    EditText etDate;
    EditText etAccount;
    EditText etCategory;
    EditText etMoney;
    EditText etNote;
    TableLayout tableLayout;

    String[] accounts;
    String[] revenueCategories;
    String[] expenditureCategories;
    String[] categories;
    String[] numbers = new String[]{"7", "8", "9", "4", "5", "6", "1", "2", "3", "00 000", "0 000", "000", "0", "←"};

    int formID = 2;
    Map<String, Integer> accountID = new HashMap<>();
    Map<String, Integer> categoryID = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        init();
    }

    private void init(){
        initData();
        initWidget();
        initInAppKeyboard();
    }

    private void initData(){
        dbManager = new DBManager(this);

        // Get account data
        List<Account> accountList = dbManager.getAllAccount();
        List<String> accountNames = new ArrayList<>();
        for(Account acc: accountList){
            accountNames.add(acc.getmAccountName());
            accountID.put(acc.getmAccountName(), acc.getmAccountID());
        }
        accounts = new String[accountNames.size()];
        accounts = accountNames.toArray(accounts);

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
    }

    private void initWidget(){
        btnRevenue = findViewById(R.id.btnRevenue);
        btnExpenditure = findViewById(R.id.btnExpenditure);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        etDate = findViewById(R.id.etDate);
        etAccount = findViewById(R.id.etAccount);
        etCategory = findViewById(R.id.etCategory);
        etMoney = findViewById(R.id.etMoney);
        etNote = findViewById(R.id.etNote);
        tableLayout = findViewById(R.id.tableLayout);

        etDate.setInputType(InputType.TYPE_NULL);
        etAccount.setInputType(InputType.TYPE_NULL);
        etCategory.setInputType(InputType.TYPE_NULL);
        etMoney.setInputType(InputType.TYPE_NULL);

        btnRevenue.setOnClickListener(this);
        btnExpenditure.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        etDate.setOnTouchListener(this);
        etAccount.setOnTouchListener(this);
        etCategory.setOnTouchListener(this);
        etMoney.setOnTouchListener(this);
        etNote.setOnTouchListener(this);
    }

    private void changeButtonsColor(String buttonName){
        if(buttonName.equals("THU")){
            int colorPos = ContextCompat.getColor(this, R.color.colorRevenue);
            btnRevenue.setTextColor(colorPos);
            btnRevenue.setBackground(ContextCompat.getDrawable(this,R.drawable.btn_revenue));
            btnExpenditure.setTextColor(Color.DKGRAY);
            btnExpenditure.setBackground(ContextCompat.getDrawable(this,R.drawable.btn_no_select));
            btnSave.setBackground(ContextCompat.getDrawable(this,R.drawable.btn_fill_revenue));
            DrawableCompat.setTint(etDate.getBackground(), colorPos);
            DrawableCompat.setTint(etAccount.getBackground(), colorPos);
            DrawableCompat.setTint(etCategory.getBackground(), colorPos);
            DrawableCompat.setTint(etMoney.getBackground(), colorPos);
            DrawableCompat.setTint(etNote.getBackground(), colorPos);
        }else{
            int colorPos = ContextCompat.getColor(this, R.color.colorExpenditure);
            btnExpenditure.setTextColor(colorPos);
            btnExpenditure.setBackground(ContextCompat.getDrawable(this,R.drawable.btn_expenditure));
            btnRevenue.setTextColor(Color.DKGRAY);
            btnRevenue.setBackground(ContextCompat.getDrawable(this,R.drawable.btn_no_select));
            btnSave.setBackground(ContextCompat.getDrawable(this,R.drawable.btn_fill_expenditure));
            DrawableCompat.setTint(etDate.getBackground(), colorPos);
            DrawableCompat.setTint(etAccount.getBackground(), colorPos);
            DrawableCompat.setTint(etCategory.getBackground(), colorPos);
            DrawableCompat.setTint(etMoney.getBackground(), colorPos);
            DrawableCompat.setTint(etNote.getBackground(), colorPos);
        }
    }

    private void showDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
//            etDate.setText(dayOfMonth + "-" + (monthOfYear+1) + "-" + year);
            etDate.setText(String.format("%02d",dayOfMonth) + "-" + String.format("%02d",monthOfYear+1) + "-" + year);
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, Calendar.getInstance().get(Calendar.YEAR),
                                    Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void initInAppKeyboard(){
        etAccount.requestFocus();
        setUpKeyboard(accounts);
    }

    private void setUpKeyboard(String[] keyboardItems){
        tableLayout.removeAllViews();

        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        for(int i=0; i<keyboardItems.length/3; i++){
            TableRow row = new TableRow(this);
            params.weight = 3;
            row.setLayoutParams(params);
            params.weight = 1;
            for(int j=0; j<3; j++){
                Button btn = new Button(this);
                btn.setText(keyboardItems[3*i+j]);
                btn.setLayoutParams(params);
                btn.setOnClickListener(this);
                row.addView(btn);
            }
            tableLayout.addView(row);
        }
        if(keyboardItems.length%3!=0){
            TableRow row = new TableRow(this);
            params.weight = 3;
            row.setLayoutParams(params);
            params.weight = 1;
            for(int i=0; i<keyboardItems.length%3; i++){
                Button btn = new Button(this);
                btn.setText(keyboardItems[keyboardItems.length-keyboardItems.length%3+i]);
                btn.setLayoutParams(params);
                btn.setOnClickListener(this);
                if(btn.getText().equals("←")){
                    btn.setOnLongClickListener(this);
                }
                row.addView(btn);
            }
            tableLayout.addView(row);
        }
    }

    private void hideSoftKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if(inputMethodManager.isActive(getCurrentFocus()))
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    private String separatorNumber(long number){
        return String.format("%,d", number).replace(",", " ").replace(".", " ");
    }

    private void removeLastInputChar(){
        String moneyStr = etMoney.getText().toString().replace(" ", "");   // get string money without white spaces
        if(moneyStr.length() == 1){
            etMoney.setText("0");
        }else{
            long moneyOut = Long.parseLong(moneyStr.substring(0, moneyStr.length()-1));
            etMoney.setText(separatorNumber(moneyOut));
        }
    }

    private void inputNumber(String numberInput){
        try{
            String moneyStr = etMoney.getText().toString().replace(" ", "");   // get string money without white spaces
            long money = moneyStr.equals("")?0:Long.parseLong(moneyStr);  // money input = 0 when string = ""
            numberInput = numberInput.replace(" ", ""); // get input without white spaces
            long moneyOut;
            switch (numberInput){
                case "00000": case "0000": case "000": case "0":
                    moneyOut = Long.parseLong(moneyStr + numberInput);
                    etMoney.setText(separatorNumber(moneyOut));
                    break;
                case "1": case "2": case "3": case "4": case "5":
                case "6": case "7": case "8": case "9":
                    if(money == 0){
                        etMoney.setText(numberInput);
                    }else{
                        moneyOut = Long.parseLong(moneyStr + numberInput);
                        etMoney.setText(separatorNumber(moneyOut));
                    }
                    break;
                case "←":
                    removeLastInputChar();
                    break;
            }
        }catch (Exception ex){
            Toast.makeText(this, "Số bạn nhập quá lớn", Toast.LENGTH_LONG).show();
        }
    }

    private void saveData(){
        String date = etDate.getText().toString();
        String category = etCategory.getText().toString();
        String account = etAccount.getText().toString();
        float money = Integer.valueOf(etMoney.getText().toString().replace(" ", ""));
        String note = etNote.getText().toString();

        RevenueExpenditureDetail revenueExpenditureDetail = new RevenueExpenditureDetail(formID, categoryID.get(category), accountID.get(account), money, note, 0, date);
        dbManager.addRevenueExpenditureDetail(revenueExpenditureDetail);
    }

    @Override
    public void onClick(View view) {
        String buttonText = ((Button) view).getText().toString();
        switch (view.getId()){
            case R.id.btnRevenue:
                changeButtonsColor(buttonText);
                categories = revenueCategories;
                etCategory.setText("");
                etCategory.requestFocus();
                formID = 1;
                break;
            case R.id.btnExpenditure:
                changeButtonsColor(buttonText);
                categories = expenditureCategories;
                etCategory.setText("");
                etCategory.requestFocus();
                formID = 2;
                break;
            case R.id.btnSave:
                saveData();
                finish();
                break;
            case R.id.btnCancel:
                finish();
                break;
            default:

                if(buttonText.trim().matches("[1-9]") || buttonText.contains("0") || buttonText.contains("←")){
                    inputNumber(buttonText);
                }else{
                    EditText et = (EditText)ItemDetailActivity.this.getCurrentFocus();
                    et.setText(buttonText);
                }

                break;
        }
        View focusView = ItemDetailActivity.this.getCurrentFocus();
        if(focusView != null && focusView == etCategory){
            setUpKeyboard(categories);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            switch (view.getId()){
                case R.id.etDate:
                    showDatePicker();
                    setUpKeyboard(new String[]{});
                    hideSoftKeyboard();
                    break;
                case R.id.etAccount:
                    setUpKeyboard(accounts);
                    hideSoftKeyboard();
                    break;
                case R.id.etCategory:
                    setUpKeyboard(categories);
                    hideSoftKeyboard();
                    break;
                case R.id.etMoney:
                    setUpKeyboard(numbers);
                    hideSoftKeyboard();
                    break;
                case R.id.etNote:
                    setUpKeyboard(new String[]{});
                    break;
            }
        }

        return false;
    }

    @Override
    public boolean onLongClick(View view) {
        etMoney.setText("0");
        return false;
    }
}