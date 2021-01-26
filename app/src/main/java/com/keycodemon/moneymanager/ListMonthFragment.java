package com.keycodemon.moneymanager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.keycodemon.moneymanager.data.ViewDataManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListMonthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListMonthFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewDataManager viewDataManager;
    FragmentManager fragmentManager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListMonthFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListMonthFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListMonthFragment newInstance(String param1, String param2) {
        ListMonthFragment fragment = new ListMonthFragment();
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
        View view = inflater.inflate(R.layout.fragment_list_month, container, false);

        init(view);
        return view;
    }

    void init(View view){
        initData();
        initWidget(view);
    }

    void initData(){
        viewDataManager = new ViewDataManager(getActivity());
        fragmentManager = getActivity().getSupportFragmentManager();
    }

    void initWidget(View view){
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);

        ArrayList<String> arrayList = viewDataManager.getAllMonthExpenditureRevenue();

        prepareViewPager(viewPager, arrayList);

        tabLayout.setupWithViewPager(viewPager);
    }

    private void prepareViewPager(ViewPager viewPager, ArrayList<String> arrayList) {
        MainAdapter adapter = new MainAdapter(fragmentManager);

        ListDateFragment fragment = new ListDateFragment();
        for(int i=0; i<arrayList.size(); i++){
            Bundle bundle = new Bundle();
            bundle.putString("monthOfYear", arrayList.get(i));
            fragment.setArguments(bundle);
            adapter.addFragment(fragment, arrayList.get(i));
            fragment = new ListDateFragment();
        }

        viewPager.setAdapter(adapter);

    }

    public void updateData(){
        ArrayList<String> arrayList = viewDataManager.getAllMonthExpenditureRevenue();

        prepareViewPager(viewPager, arrayList);

        tabLayout.setupWithViewPager(viewPager);
    }

    private class MainAdapter extends FragmentPagerAdapter {

        ArrayList<String> arrayList = new ArrayList<>();
        List<Fragment> fragmentList = new ArrayList<>();

        public MainAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title){
            arrayList.add(title);
            fragmentList.add(fragment);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return arrayList.get(position);
        }


    }
}