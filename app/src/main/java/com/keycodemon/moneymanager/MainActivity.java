package com.keycodemon.moneymanager;

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

public class MainActivity extends AppCompatActivity {

    CustomExpandableListAdapter customExpandableListAdapter;
    ExpandableListView expandableListView;
    List<DayData> dayDataList;
    ExpandableGetData expandableGetData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Move to Item Detail Activity
                Intent intent = new Intent(getApplicationContext(), ItemDetailActivity.class);
                startActivityForResult(intent, 0);
            }
        });
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
        expandableListView = findViewById(R.id.expandable_listview);
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

}