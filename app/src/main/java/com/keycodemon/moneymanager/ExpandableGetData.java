package com.keycodemon.moneymanager;

import java.util.ArrayList;
import java.util.List;

public class ExpandableGetData {
    private List<ExpandableListGroupData> expandableListGroupDataList;
    private  ExpandableListItemData expandableListItemData;
    private List<ExpandableListItemData> expandableListItemDataList;
    private ExpandableListGroupData expandableListGroupData;
    public List<ExpandableListGroupData> getData(){
        expandableListGroupDataList = new ArrayList<ExpandableListGroupData>();

        expandableListItemData = new ExpandableListItemData("Ăn uống","Ăn sáng","Tiền mặt","-20000");
        expandableListItemDataList = new ArrayList<ExpandableListItemData>();
        expandableListItemDataList.add(expandableListItemData);
        expandableListItemData = new ExpandableListItemData("Ăn uống","Ăn sáng","Tiền mặt","-20000");
        expandableListItemDataList.add(expandableListItemData);
        expandableListItemData = new ExpandableListItemData("Ăn uống","Ăn sáng","Tiền mặt","-20000");
        expandableListItemDataList.add(expandableListItemData);
        expandableListGroupData = new ExpandableListGroupData("12","10.2020","Th2","1000000","-1000000", expandableListItemDataList);
        expandableListGroupDataList.add(expandableListGroupData);
        expandableListGroupData = new ExpandableListGroupData("12","10.2020","Th2","1000000","-1000000", expandableListItemDataList);
        expandableListGroupDataList.add(expandableListGroupData);
        return  expandableListGroupDataList;
    }
}
