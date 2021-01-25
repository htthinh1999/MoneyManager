package com.keycodemon.moneymanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.keycodemon.moneymanager.R;
import com.keycodemon.moneymanager.model.Account;

import java.util.List;

public class AccountListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Account> AccountList;


    public AccountListAdapter(Context context, int layout, List<Account> accountList) {
        this.context = context;
        this.layout = layout;
        this.AccountList = accountList;
    }

    @Override
    public int getCount() {
        return AccountList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout,null);
        TextView tvAccountName = convertView.findViewById(R.id.tvAccountName);
        TextView tvTotalMoney = convertView.findViewById(R.id.tvTotalMoney);

        Account account= AccountList.get(position);
        tvAccountName.setText(account.getmAccountName());
        tvTotalMoney.setText((int) account.getmBalance());

        return convertView;
    }
}
