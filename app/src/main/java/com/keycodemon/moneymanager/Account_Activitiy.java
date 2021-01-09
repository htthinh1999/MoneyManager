package com.keycodemon.moneymanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.keycodemon.moneymanager.data.DBManager;
import com.keycodemon.moneymanager.model.Account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Account_Activitiy extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener, View.OnLongClickListener, AdapterView.OnItemClickListener {

    DBManager dbManager;

    Button btnSave;
    Button btnEdit;
    Button btnDelete;
    Button btnCancel;


    AutoCompleteTextView etAccountName;
    EditText etBalance;

    TableLayout tableLayout;

    int formID = 2;
    int idOfAccount = 0 ;

    String[] accounts;
    String[] categories;
    String[] accountBalances;
    String[] numbers = new String[]{"7", "8", "9", "4", "5", "6", "1", "2", "3", "00 000", "0 000", "000", "0", "←"};


    Map<String, Integer> accountID = new HashMap<>();
    Map<String, Integer> categoryID = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account__activities);

        init();
    }

    private void init(){
        initWidget();
        initData();
        initInAppKeyboard();
    }

    private void initData()
    {
        dbManager = new DBManager(this);

        // Get account data
        List<Account> accountList = dbManager.getAllAccount();
        List<String> accountNames = new ArrayList<>();
        List<String> accountBalance = new ArrayList<>();
        for(Account acc: accountList){
            accountNames.add(acc.getmAccountName());
            accountID.put(acc.getmAccountName(), acc.getmAccountID());
        }
        for(Account acc: accountList){
            accountBalance.add(String.valueOf(acc.getmBalance()));
        }
        accounts = new String[accountNames.size()];
        accounts = accountNames.toArray(accounts);

        accountBalances = new String[accountBalance.size()];
        accountBalances = accountBalance.toArray(accountBalances);
//        Log.d( "account123", String.valueOf(accounts));
//        Log.d( "account1234", accounts[1] );
//        Log.d( "balance123", accountBalances[1] );

        // Get all acoount for auto complete textview
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, accounts);
//        Log.d("adapter1", String.valueOf(adapter));
        etAccountName.setAdapter(adapter);


    }


    private void initWidget()
    {
        // Hide action bar
        if(getSupportActionBar().isShowing()){
            getSupportActionBar().hide();
        }

        btnSave = findViewById(R.id.btnSave);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        etAccountName = (AutoCompleteTextView) findViewById(R.id.etAccountName);
        etBalance = findViewById(R.id.etBalance);

        tableLayout = findViewById(R.id.tableLayout);

//        etAccountName.setInputType(InputType.TYPE_NULL);
        etBalance.setInputType(InputType.TYPE_NULL);

        etAccountName.setOnTouchListener(this);
        etBalance.setOnTouchListener(this);
        etAccountName.setOnItemClickListener(this);

    }

    private void initInAppKeyboard(){
        etAccountName.requestFocus();
//        setUpKeyboard(accounts);
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
        String moneyStr = etBalance.getText().toString().replace(" ", "");   // get string money without white spaces
        if(moneyStr.length() == 1){
            etBalance.setText("0");
        }else{
            long moneyOut = Long.parseLong(moneyStr.substring(0, moneyStr.length()-1));
            etBalance.setText(separatorNumber(moneyOut));
        }
    }

    private void inputNumber(String numberInput){
        try{
            String moneyStr = etBalance.getText().toString().replace(" ", "");   // get string money without white spaces
            long money = moneyStr.equals("")?0:Long.parseLong(moneyStr);  // money input = 0 when string = ""
            numberInput = numberInput.replace(" ", ""); // get input without white spaces
            long moneyOut;
            switch (numberInput){
                case "00000": case "0000": case "000": case "0":
                    moneyOut = Long.parseLong(moneyStr + numberInput);
                    etBalance.setText(separatorNumber(moneyOut));
                    break;
                case "1": case "2": case "3": case "4": case "5":
                case "6": case "7": case "8": case "9":
                    if(money == 0){
                        etBalance.setText(numberInput);
                    }else{
                        moneyOut = Long.parseLong(moneyStr + numberInput);
                        etBalance.setText(separatorNumber(moneyOut));
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
        String accountName = etAccountName.getText().toString();
        float balance = Float.valueOf(etBalance.getText().toString().replace(" ", ""));

        if( idOfAccount == 0){
            Account account = new Account(accountName,balance);
            dbManager.addAccount(account);
        }else
        {
//            int id = getIntent().getIntExtra("id", 0);
            Account account = new Account(idOfAccount,accountName,balance);
            dbManager.updateAccount(account);
        }
    }

    private void deleteData() {
//        int id = getIntent().getIntExtra("id", 0);
        dbManager.deleteAccount(idOfAccount);
    }

    private boolean validInput(){
        if(String.valueOf(etAccountName.getText()).isEmpty() || String.valueOf(etBalance.getText()).isEmpty() ||
                String.valueOf(etBalance.getText()).equals("0")){
            return false;
        }
        return true;
    }

    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Xóa")
                .setMessage("Bạn muốn xóa tài khoản này?")
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
//                deleteData();
//                setResult(RESULT_OK);
//                finish();
                break;
            case R.id.btnCancel:
                setResult(RESULT_OK);
                finish();
                break;
            default:

                if(buttonText.trim().matches("[1-9]") || buttonText.contains("0") || buttonText.contains("←")){
                    inputNumber(buttonText);
                }else{
                    EditText et = (EditText)Account_Activitiy.this.getCurrentFocus();
                    et.setText(buttonText);
                }

                break;
        }
//        View focusView = Account_Activitiy.this.getCurrentFocus();
//        if(focusView != null && focusView == etAccountName){
//            setUpKeyboard(accounts);
//        }
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
                case R.id.etBalance:
                    setUpKeyboard(numbers);
                    hideSoftKeyboard();
                    break;
                case R.id.etAccountName:
                    etAccountName.showDropDown();
//                    setUpKeyboard(new String[]{});
                    break;
            }
        }
        return false;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String itemName = accounts[position];
        etBalance.setText( accountBalances[position] );
//        Toast.makeText(this, accounts[position], Toast.LENGTH_SHORT).show();

         idOfAccount = accountID.get(itemName);
    }
}