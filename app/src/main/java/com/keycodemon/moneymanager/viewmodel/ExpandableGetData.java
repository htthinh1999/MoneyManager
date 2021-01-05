package com.keycodemon.moneymanager.viewmodel;

import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.keycodemon.moneymanager.adapter.CustomExpandableListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ExpandableGetData {
    private List<ExpandableListGroupData> expandableListGroupDataList;
    private ExpandableListItemData expandableListItemData;
    private List<ExpandableListItemData> expandableListItemDataList;
    private ExpandableListGroupData expandableListGroupData;

    public List<ExpandableListGroupData> getData(){
        expandableListGroupDataList = new ArrayList<ExpandableListGroupData>();

        expandableListItemData = new ExpandableListItemData("Ăn uống","Ăn sáng","Tiền mặt", "0", "-20000");
        expandableListItemDataList = new ArrayList<ExpandableListItemData>();
        expandableListItemDataList.add(expandableListItemData);
        expandableListItemData = new ExpandableListItemData("Ăn uống","Ăn trưa","Tiền mặt", "0", "-50000");
        expandableListItemDataList.add(expandableListItemData);
        expandableListItemData = new ExpandableListItemData("Ăn uống","Ăn tối","Tiền mặt", "0", "-30000");
        expandableListItemDataList.add(expandableListItemData);
        expandableListGroupData = new ExpandableListGroupData("12","10.2020","Th2","1000000","-1000000", expandableListItemDataList);
        expandableListGroupDataList.add(expandableListGroupData);
        expandableListGroupData = new ExpandableListGroupData("13","10.2020","Th2","1000000","-1000000", expandableListItemDataList);
        expandableListGroupDataList.add(expandableListGroupData);
        return  expandableListGroupDataList;
    }

    public void detailListViewItem(ExpandableListView expandableListView, final CustomExpandableListAdapter customExpandableListAdapter){
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int grouppos, int childpos, long l) {
                expandableListItemData = (ExpandableListItemData) customExpandableListAdapter.getChild(grouppos,childpos);
                expandableListGroupData = (ExpandableListGroupData) customExpandableListAdapter.getGroup(grouppos);
                Toast.makeText(view.getContext(),"Thời gian: "+ expandableListGroupData.getDayofWeek()+" "+ expandableListGroupData.getDayOfMonth()
                        + "."+expandableListGroupData.getMonthYear() +" \n Mục đích: "+expandableListItemData.getGroupItem()+" \n Cụ thể: "+expandableListItemData.getItem()
                        +"\n Giá trị: "+expandableListItemData.getExpenditure(),Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }
}
