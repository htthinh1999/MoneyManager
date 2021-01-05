package com.keycodemon.moneymanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.keycodemon.moneymanager.R;
import com.keycodemon.moneymanager.viewmodel.ExpandableListGroupData;
import com.keycodemon.moneymanager.viewmodel.ExpandableListItemData;

import java.util.List;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<ExpandableListGroupData> expandableListGroupDataList;


    public CustomExpandableListAdapter(Context context, List<ExpandableListGroupData> expandableListGroupDataList) {
        this.context = context;
        this.expandableListGroupDataList = expandableListGroupDataList;
    }

    @Override
    public int getGroupCount() {
        return expandableListGroupDataList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return expandableListGroupDataList.get(groupPosition).itemCount();
    }

    @Override
    public Object getGroup(int groupPosition) {

        return expandableListGroupDataList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return expandableListGroupDataList.get(groupPosition).getExpandableListItemDataList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {

        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {

        return childPosition;
    }

    @Override
    public boolean hasStableIds() {

        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ExpandableListGroupData expandableListGroupData = (ExpandableListGroupData) getGroup(groupPosition);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_day,null);
        TextView tvDayOfMonth = convertView.findViewById(R.id.tvDayOfMonth);
        tvDayOfMonth.setText(expandableListGroupData.getDayOfMonth());
        TextView tvMonthYear = convertView.findViewById(R.id.tvMonthYear);
        tvMonthYear.setText(expandableListGroupData.getMonthYear());
        TextView tvDayOfWeek = convertView.findViewById(R.id.tvDayOfWeek);
        tvDayOfWeek.setText(expandableListGroupData.getDayofWeek());
        TextView tvRevenue = convertView.findViewById(R.id.tvRevenue);
        tvRevenue.setText(expandableListGroupData.getRevenue());
        TextView tvExpenditure = convertView.findViewById(R.id.tvExpenditure);
        tvExpenditure.setText(expandableListGroupData.getExpenditure());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ExpandableListItemData expandableListItemData = (ExpandableListItemData) getChild(groupPosition,childPosition);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_item,null);
        TextView tvgroupitem = convertView.findViewById(R.id.tvGroupItem);
        tvgroupitem.setText(expandableListItemData.getGroupItem());
        TextView tvitem = convertView.findViewById(R.id.tvItem);
        tvitem.setText(expandableListItemData.getItem());
        TextView tvwallet = convertView.findViewById(R.id.tvWallet);
        tvwallet.setText(expandableListItemData.getWallet());
        TextView tvExpenditure = convertView.findViewById(R.id.tvExpenditure);
        tvExpenditure.setText(expandableListItemData.getExpenditure());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
