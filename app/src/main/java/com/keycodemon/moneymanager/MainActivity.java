package com.keycodemon.moneymanager;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.keycodemon.moneymanager.adapter.CustomExpandableListAdapter;
import com.keycodemon.moneymanager.data.DBManager;
import com.keycodemon.moneymanager.model.RevenueExpenditureDetail;
import com.keycodemon.moneymanager.data.ExpandableGetData;
import com.keycodemon.moneymanager.viewmodel.DayData;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Debug;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    CustomExpandableListAdapter customExpandableListAdapter;
    ExpandableListView expandableListView;
    List<DayData> dayDataList;

    View fabBGLayout;
    FloatingActionButton fabMenu, fabRevenueExpenditure, fabSavingDeposit;
    ExpandableGetData expandableGetData;

    boolean fabOpened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        dayDataList = expandableGetData.getData();
        customExpandableListAdapter = new CustomExpandableListAdapter(this, dayDataList);
        expandableListView.setAdapter(customExpandableListAdapter);
        expandableGetData.detailListViewItem(expandableListView, customExpandableListAdapter, this);

    }

    public void init(){
        initWidget();
        initData();
    }

    public void initWidget(){
        fabBGLayout = findViewById(R.id.fabBGLayout);
        fabMenu = findViewById(R.id.fabMenu);
        fabRevenueExpenditure = findViewById(R.id.fabRevenueExpenditure);
        fabSavingDeposit = findViewById(R.id.fabSavingDeposit);

        expandableListView = findViewById(R.id.expandable_listview);

        fabBGLayout.setOnClickListener(this);
        fabMenu.setOnClickListener(this);
        fabRevenueExpenditure.setOnClickListener(this);
        fabSavingDeposit.setOnClickListener(this);

    }

    public void initData(){

        expandableGetData = new ExpandableGetData(this);
        dayDataList = expandableGetData.getData();
        customExpandableListAdapter = new CustomExpandableListAdapter(this, dayDataList);
        expandableListView.setAdapter(customExpandableListAdapter);
        expandableGetData.detailListViewItem(expandableListView, customExpandableListAdapter, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openFabMenu(){
        fabOpened = true;
        fabRevenueExpenditure.setVisibility(View.VISIBLE);
        fabSavingDeposit.setVisibility(View.VISIBLE);
        fabBGLayout.setVisibility(View.VISIBLE);

        fabMenu.animate().rotationBy(225);
        fabRevenueExpenditure.animate().translationY(-getResources().getDimension(R.dimen.fab_revenue_expenditure_margin));
        fabSavingDeposit.animate().translationY(-getResources().getDimension(R.dimen.fab_saving_deposit_margin));
    }

    private void closeFabMenu(){
        fabOpened = false;
        fabBGLayout.setVisibility(View.GONE);
        fabMenu.animate().rotation(0);
        fabRevenueExpenditure.animate().translationY(0);
        fabSavingDeposit.animate().translationY(0);

        fabSavingDeposit.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!fabOpened) {
                    fabRevenueExpenditure.setVisibility(View.GONE);
                    fabSavingDeposit.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fabBGLayout:
                closeFabMenu();
                break;
            case R.id.fabMenu:
                if(fabOpened){
                    closeFabMenu();
                }else{
                    openFabMenu();
                }
                break;
            case R.id.fabRevenueExpenditure:
                closeFabMenu();
                // Move to Item Detail Activity
                Intent intent = new Intent(getApplicationContext(), ItemDetailActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.fabSavingDeposit:
                closeFabMenu();
                // Move to Saving Deposit Activity
                intent = new Intent(getApplicationContext(), SavingDepositActivity.class);
                startActivityForResult(intent, 0);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        if (fabOpened) {
            closeFabMenu();
        } else {
            super.onBackPressed();
        }
    }
}