package com.keycodemon.moneymanager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.keycodemon.moneymanager.adapter.CustomExpandableListAdapter;
import com.keycodemon.moneymanager.data.DBManager;
import com.keycodemon.moneymanager.model.Account;
import com.keycodemon.moneymanager.viewmodel.ExpandableGetData;
import com.keycodemon.moneymanager.viewmodel.ExpandableListGroupData;
import com.keycodemon.moneymanager.viewmodel.ExpandableListItemData;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    DBManager dbManager;

    CustomExpandableListAdapter customExpandableListAdapter;
    ExpandableListView expandableListView;
    List<ExpandableListGroupData> expandableListGroupDataList;
    ExpandableGetData expandableGetData;
    ExpandableListItemData expandableListItemData;
    ExpandableListGroupData expandableListGroupData;
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
                startActivity(intent);
            }
        });
    }
    public void init(){
        dbManager = new DBManager(this);
        dbManager.getAllAccount();

        expandableListView = (ExpandableListView) findViewById(R.id.expndable_listview);
        expandableGetData = new ExpandableGetData();
        expandableListGroupDataList = expandableGetData.getData();
        customExpandableListAdapter = new CustomExpandableListAdapter(this,expandableListGroupDataList);
        expandableListView.setAdapter(customExpandableListAdapter);
        expandableGetData.detailListViewItem(expandableListView,customExpandableListAdapter);
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