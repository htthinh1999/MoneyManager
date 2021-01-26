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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListDateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListDateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListDateFragment newInstance(String param1, String param2) {
        ListDateFragment fragment = new ListDateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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
        dayDataList = expandableGetData.getData();
        customExpandableListAdapter = new CustomExpandableListAdapter(getActivity(), dayDataList);
        expandableListView.setAdapter(customExpandableListAdapter);
        expandableGetData.detailListViewItem(expandableListView, customExpandableListAdapter, getActivity());
    }

}