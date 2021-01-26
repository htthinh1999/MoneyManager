package com.keycodemon.moneymanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.keycodemon.moneymanager.R;
import com.keycodemon.moneymanager.viewmodel.DayData;
import com.keycodemon.moneymanager.viewmodel.ItemDetailData;

import java.util.List;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<DayData> dayDataList;


    public CustomExpandableListAdapter(Context context, List<DayData> dayDataList) {
        this.context = context;
        this.dayDataList = dayDataList;
    }

    @Override
    public int getGroupCount() {
        return dayDataList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return dayDataList.get(groupPosition).itemCount();
    }

    @Override
    public Object getGroup(int groupPosition) {

        return dayDataList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return dayDataList.get(groupPosition).getItemDetailDataList().get(childPosition);
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

    private String separatorNumber(Long number){
        return String.format("%,d", number).replace(",", " ").replace(".", " ") + " VNĐ";
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        DayData dayData = (DayData) getGroup(groupPosition);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_day,null);
        TextView tvDayOfMonth = convertView.findViewById(R.id.tvDayOfMonth);
        tvDayOfMonth.setText(dayData.getDayOfMonth());
        TextView tvMonthYear = convertView.findViewById(R.id.tvMonthYear);
        tvMonthYear.setText(dayData.getMonthYear());
        TextView tvDayOfWeek = convertView.findViewById(R.id.tvDayOfWeek);
        tvDayOfWeek.setText(dayData.getDayofWeek());
        TextView tvRevenue = convertView.findViewById(R.id.tvRevenue);
        tvRevenue.setText(separatorNumber(dayData.getRevenue()));
        TextView tvExpenditure = convertView.findViewById(R.id.tvExpenditure);
        tvExpenditure.setText(separatorNumber(dayData.getExpenditure()));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ItemDetailData itemDetailData = (ItemDetailData) getChild(groupPosition,childPosition);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_item,null);
        TextView tvgroupitem = convertView.findViewById(R.id.tvGroupItem);
        tvgroupitem.setText(itemDetailData.getCategory());
        TextView tvitem = convertView.findViewById(R.id.tvItem);
        tvitem.setText(itemDetailData.getNote());
        TextView tvwallet = convertView.findViewById(R.id.tvWallet);
        tvwallet.setText(itemDetailData.getAccount());
        TextView tvMoney = convertView.findViewById(R.id.tvMoney);
        tvMoney.setText(separatorNumber(itemDetailData.getMoney()));

        if(itemDetailData.getFormID() == 1){
            tvMoney.setTextColor(ContextCompat.getColor(context, R.color.colorRevenue));
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
