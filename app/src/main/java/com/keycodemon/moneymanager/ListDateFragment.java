package com.keycodemon.moneymanager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.keycodemon.moneymanager.adapter.CustomExpandableListAdapter;
import com.keycodemon.moneymanager.data.ExpandableGetData;
import com.keycodemon.moneymanager.viewmodel.DayData;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListDateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListDateFragment extends Fragment {

    CustomExpandableListAdapter customExpandableListAdapter;
    ExpandableListView expandableListView;
    List<DayData> dayDataList;
    ExpandableGetData expandableGetData;

    private static final String MONTHOFYEAR = "monthOfYear";

    private String monthOfYear;

    public ListDateFragment() {
        // Required empty public constructor
    }

    public static ListDateFragment newInstance(String monthOfYear) {
        ListDateFragment fragment = new ListDateFragment();
        Bundle args = new Bundle();
        args.putString(MONTHOFYEAR, monthOfYear);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            monthOfYear = getArguments().getString(MONTHOFYEAR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_date, container, false);
        init(view);
        return view;
    }

    private void init(View view){
        initWidget(view);
        initData();
    }

    private void initWidget(View view){
        expandableListView = view.findViewById(R.id.expandable_listview);
    }

    public void initData(){
        expandableGetData = new ExpandableGetData(getActivity());
        dayDataList = expandableGetData.getDataOfMonth(monthOfYear);
        customExpandableListAdapter = new CustomExpandableListAdapter(getActivity(), dayDataList);
        expandableListView.setAdapter(customExpandableListAdapter);
        expandableGetData.detailListViewItem(expandableListView, customExpandableListAdapter, getActivity());
    }

}