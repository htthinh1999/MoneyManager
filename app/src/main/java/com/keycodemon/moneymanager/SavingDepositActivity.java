package com.keycodemon.moneymanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SavingDepositActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener, View.OnLongClickListener {

    DBManager dbManager;

    EditText etDate;
    EditText etMoney;
    EditText etInterestRate;
    EditText etSchedule;
    EditText etSourceAccount;
    EditText etDestinationAccount;
    EditText etTitle;
    TableLayout tableLayout;
    Button btnSave;
    Button btnDelete;
    Button btnCancel;

    String[] accountSources, accountDestinations;
    String[] schedules = new String[]{"6 tháng", "12 tháng", " tháng", "24 tháng", "36 tháng", "60 tháng"};
    String[] numbers = new String[]{"7", "8", "9", "4", "5", "6", "1", "2", "3", "00 000", "0 000", "000", "0", "←"};

    Map<String, Integer> accountID = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_deposit);

        init();
    }

    private void init(){
        initData();
        initWidget();
        initInAppKeyboard();
    }

    private void initWidget(){
        // Hide Action Bar
        if(getSupportActionBar().isShowing()){
            getSupportActionBar().hide();
        }


        etDate = findViewById(R.id.etDate);
        etMoney = findViewById(R.id.etMoney);
        etInterestRate = findViewById(R.id.etInterestRate);
        etSchedule = findViewById(R.id.etSchedule);
        etSourceAccount = findViewById(R.id.etSourceAccount);
        etDestinationAccount = findViewById(R.id.etDestinationAccount);
        etTitle = findViewById(R.id.etTitle);
        tableLayout = findViewById(R.id.tableLayout);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);

        etDate.setInputType(InputType.TYPE_NULL);
        etMoney.setInputType(InputType.TYPE_NULL);
        etSchedule.setInputType(InputType.TYPE_NULL);
        etSourceAccount.setInputType(InputType.TYPE_NULL);
        etDestinationAccount.setInputType(InputType.TYPE_NULL);

        etDate.setOnTouchListener(this);
        etMoney.setOnTouchListener(this);
        etInterestRate.setOnTouchListener(this);
        etSchedule.setOnTouchListener(this);
        etSourceAccount.setOnTouchListener(this);
        etDestinationAccount.setOnTouchListener(this);

        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnCancel.setOnClickListener(this);


        // Set current date
        etDate.setText(getCurrentDate());
    }

    private void initData(){
        dbManager = new DBManager(this);

        // Get account data
        List<Account> accountList = dbManager.getAllAccount();
        List<String> accountSourceNames = new ArrayList<>();
        List<String> accountDestinationNames = new ArrayList<>();
        for(Account acc: accountList){
            accountSourceNames.add(acc.getmAccountName());
            accountDestinationNames.add(acc.getmAccountName());
            accountID.put(acc.getmAccountName(), acc.getmAccountID());
        }
        accountSources = new String[accountSourceNames.size()];
        accountDestinations = new String[accountDestinationNames.size()];

        accountSources = accountSourceNames.toArray(accountSources);
        accountDestinations = accountDestinationNames.toArray(accountDestinations);
    }

    private void initInAppKeyboard(){
        etMoney.requestFocus();
        setUpKeyboard(numbers);
    }

    private String getCurrentDate(){
        int dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        return String.format("%02d",dayOfMonth) + "-" + String.format("%02d",month+1) + "-" + year;
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
                Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
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

    private boolean validInput(){
        if(String.valueOf(etDate.getText()).isEmpty() || String.valueOf(etMoney.getText()).isEmpty() ||
                String.valueOf(etMoney.getText()).equals("0") || String.valueOf(etInterestRate.getText()).equals("0") ||
                String.valueOf(etInterestRate.getText()).isEmpty() || String.valueOf(etSchedule.getText()).isEmpty() ||
                String.valueOf(etSourceAccount.getText()).isEmpty() || String.valueOf(etSourceAccount.getText()).isEmpty() ||
                String.valueOf(etTitle.getText()).isEmpty()){
            return false;
        }
        return true;
    }

    private void saveData(){

    }

    private void deleteData(){
        
    }

    @Override
    public void onClick(View view) {
        String buttonText = ((Button) view).getText().toString();
        switch (view.getId()){
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
                deleteData();
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.btnCancel:
                setResult(RESULT_OK);
                finish();
                break;
            default:

                if(buttonText.trim().matches("[1-9]") || buttonText.contains("0") || buttonText.contains("←")){
                    inputNumber(buttonText);
                }else{
                    EditText et = (EditText)SavingDepositActivity.this.getCurrentFocus();
                    et.setText(buttonText);
                }
                break;
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
                case R.id.etMoney:
                    setUpKeyboard(numbers);
                    hideSoftKeyboard();
                    break;
                case R.id.etSchedule:
                    setUpKeyboard(schedules);
                    hideSoftKeyboard();
                    break;
                case R.id.etSourceAccount:
                    setUpKeyboard(accountSources);
                    hideSoftKeyboard();
                    break;
                case R.id.etDestinationAccount:
                    setUpKeyboard(accountDestinations);
                    hideSoftKeyboard();
                    break;
                case R.id.etInterestRate: case R.id.etTitle:
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