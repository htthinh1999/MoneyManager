package com.keycodemon.moneymanager;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    CustomExpandableListAdapter customExpandableListAdapter;
    ExpandableListView expandableListView;
    List<ExpandableListGroupData> expandableListGroupDataList;
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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
//    public List<ExpandableListGroupData> getData(){
//        List<ExpandableListGroupData> expandableListGroupDataList = new ArrayList<ExpandableListGroupData>();
//
//        ExpandableListItemData expandableListItemData = new ExpandableListItemData("Ăn uống","Ăn sáng","Tiền mặt","-20000");
//        List<ExpandableListItemData> expandableListItemDataList = new ArrayList<ExpandableListItemData>();
//        expandableListItemDataList.add(expandableListItemData);
//        expandableListItemData = new ExpandableListItemData("Ăn uống","Ăn sáng","Tiền mặt","-20000");
//        expandableListItemDataList.add(expandableListItemData);
//        expandableListItemData = new ExpandableListItemData("Ăn uống","Ăn sáng","Tiền mặt","-20000");
//        expandableListItemDataList.add(expandableListItemData);
//        ExpandableListGroupData expandableListGroupData = new ExpandableListGroupData("12","10.2020","Th2","1000000","-1000000", expandableListItemDataList);
//        expandableListGroupDataList.add(expandableListGroupData);
//        expandableListGroupData = new ExpandableListGroupData("12","10.2020","Th2","1000000","-1000000", expandableListItemDataList);
//        expandableListGroupDataList.add(expandableListGroupData);
//        return  expandableListGroupDataList;
//    }
    public void init(){
        expandableListView = (ExpandableListView) findViewById(R.id.expndable_listview);
        expandableGetData = new ExpandableGetData();
        expandableListGroupDataList =  expandableGetData.getData();
        customExpandableListAdapter = new CustomExpandableListAdapter(this,expandableListGroupDataList);
        expandableListView.setAdapter(customExpandableListAdapter);
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