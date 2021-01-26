package com.keycodemon.moneymanager.data;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.keycodemon.moneymanager.ItemDetailActivity;
import com.keycodemon.moneymanager.adapter.CustomExpandableListAdapter;
import com.keycodemon.moneymanager.model.RevenueExpenditureDetail;
import com.keycodemon.moneymanager.viewmodel.DayData;
import com.keycodemon.moneymanager.viewmodel.ItemDetailData;

import java.util.ArrayList;
import java.util.List;

public class ExpandableGetData {

    private ViewDataManager viewDataManager;

    private List<RevenueExpenditureDetail> revenueExpenditureDetailList;
    private List<DayData> dayDataList;

    public ExpandableGetData(Context context){
        viewDataManager = new ViewDataManager(context);
    }

    public List<DayData> getData(){

        dayDataList = viewDataManager.getAllDayData();

        for(DayData dayData: dayDataList){
            List<ItemDetailData> itemDetailDataList = viewDataManager.getItemDetailDateListByDate(dayData.getDate());
            dayData.setItemDetailDataList(itemDetailDataList);
        }

        return dayDataList;
    }

    public List<DayData> getDataOfMonth(String monthOfYear){

        dayDataList = viewDataManager.getAllDayDataOfMonth(monthOfYear);

        for(DayData dayData: dayDataList){
            List<ItemDetailData> itemDetailDataList = viewDataManager.getItemDetailDateListByDate(dayData.getDate());
            dayData.setItemDetailDataList(itemDetailDataList);
        }

        return dayDataList;
    }

    public void detailListViewItem(ExpandableListView expandableListView, final CustomExpandableListAdapter customExpandableListAdapter, final FragmentActivity context){
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int grouppos, int childpos, long l) {

                DayData dayData = (DayData) customExpandableListAdapter.getGroup(grouppos);
                ItemDetailData itemDetailData = (ItemDetailData) customExpandableListAdapter.getChild(grouppos,childpos);

                Intent intent = new Intent(context, ItemDetailActivity.class);

                intent.putExtra("id", itemDetailData.getID());
                intent.putExtra("formID", itemDetailData.getFormID());
                intent.putExtra("date", dayData.getDate());
                intent.putExtra("account", itemDetailData.getAccount());
                intent.putExtra("category", itemDetailData.getCategory());
                intent.putExtra("money", itemDetailData.getMoney());
                intent.putExtra("note", itemDetailData.getNote());

                context.startActivityForResult(intent, 0);

                return false;
            }
        });
    }
}
