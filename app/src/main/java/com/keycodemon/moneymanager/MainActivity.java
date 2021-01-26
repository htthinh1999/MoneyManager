package com.keycodemon.moneymanager;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.keycodemon.moneymanager.adapter.CustomExpandableListAdapter;
import com.keycodemon.moneymanager.data.ExpandableGetData;
import com.keycodemon.moneymanager.viewmodel.DayData;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ListMonthFragment listMonthFragment;
    BarChartFragment barChartFragment;
    AccountListFragment accountListFragment;

    View fabBGLayout;
    FloatingActionButton fabMenu, fabRevenueExpenditure, fabSavingDeposit, fabAccount, fabCategory;
    BottomNavigationView bottomNavigationView;

    boolean fabOpened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        listMonthFragment.updateData();
    }

    public void init(){
        initWidget();
    }

    public void initWidget(){
        fabBGLayout = findViewById(R.id.fabBGLayout);
        fabMenu = findViewById(R.id.fabMenu);
        fabRevenueExpenditure = findViewById(R.id.fabRevenueExpenditure);
        fabSavingDeposit = findViewById(R.id.fabSavingDeposit);
        fabAccount = findViewById(R.id.fabAccount);
        fabCategory = findViewById(R.id.fabCategory);
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        fabBGLayout.setOnClickListener(this);
        fabMenu.setOnClickListener(this);
        fabRevenueExpenditure.setOnClickListener(this);
        fabSavingDeposit.setOnClickListener(this);
        fabAccount.setOnClickListener(this);
        fabCategory.setOnClickListener(this);

        listMonthFragment = new ListMonthFragment();
        barChartFragment = new BarChartFragment();
        accountListFragment = new AccountListFragment();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;

                switch (item.getItemId()){
                    case R.id.action_date:
                        listMonthFragment.updateData();
                        fragment = listMonthFragment;
                        break;
                    case R.id.action_report:
                        fragment = barChartFragment;
                        break;
                    case R.id.action_account:
                        fragment = accountListFragment;
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();

                return true;
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, listMonthFragment).commit();
    }

    private void openFabMenu(){
        fabOpened = true;
        fabRevenueExpenditure.setVisibility(View.VISIBLE);
        fabSavingDeposit.setVisibility(View.VISIBLE);
        fabBGLayout.setVisibility(View.VISIBLE);
        fabAccount.setVisibility(View.VISIBLE);
        fabCategory.setVisibility(View.VISIBLE);

        fabMenu.animate().rotationBy(225);
        fabRevenueExpenditure.animate().translationY(-getResources().getDimension(R.dimen.fab_revenue_expenditure_margin));
        fabSavingDeposit.animate().translationY(-getResources().getDimension(R.dimen.fab_saving_deposit_margin));
        fabAccount.animate().translationY(-getResources().getDimension(R.dimen.fab_account));
        fabCategory.animate().translationY(-getResources().getDimension(R.dimen.fab_category));

    }

    private void closeFabMenu(){
        fabOpened = false;
        fabBGLayout.setVisibility(View.GONE);
        fabMenu.animate().rotation(0);
        fabRevenueExpenditure.animate().translationY(0);
        fabSavingDeposit.animate().translationY(0);
        fabAccount.animate().translationY(0);
        fabCategory.animate().translationY(0);

        fabSavingDeposit.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!fabOpened) {
                    fabRevenueExpenditure.setVisibility(View.GONE);
                    fabSavingDeposit.setVisibility(View.GONE);
                    fabAccount.setVisibility(View.GONE);
                    fabCategory.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fabBGLayout:
                closeFabMenu();
                break;
            case R.id.fabMenu:
                if(fabOpened){
                    closeFabMenu();
                }else{
                    openFabMenu();
                }
                break;
            case R.id.fabRevenueExpenditure:
                closeFabMenu();
                // Move to Item Detail Activity
                Intent intent = new Intent(getApplicationContext(), ItemDetailActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.fabCategory:
                closeFabMenu();
                // Move to Item Detail Activity
                intent = new Intent(getApplicationContext(), CategoryActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.fabSavingDeposit:
                closeFabMenu();
                // Move to Saving Deposit Activity
                intent = new Intent(getApplicationContext(), SavingDepositActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.fabAccount:
                closeFabMenu();
                // Move to Account Activity
                intent = new Intent(getApplicationContext(), Account_Activitiy.class);
                startActivityForResult(intent, 0);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        if (fabOpened) {
            closeFabMenu();
        } else {
            super.onBackPressed();
        }
    }
}