package com.keycodemon.moneymanager.data;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

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

    public void detailListViewItem(ExpandableListView expandableListView, final CustomExpandableListAdapter customExpandableListAdapter){
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int grouppos, int childpos, long l) {

                ItemDetailData itemDetailData = (ItemDetailData) customExpandableListAdapter.getChild(grouppos,childpos);
                DayData dayData = (DayData) customExpandableListAdapter.getGroup(grouppos);

                

                return false;
            }
        });
    }
}
