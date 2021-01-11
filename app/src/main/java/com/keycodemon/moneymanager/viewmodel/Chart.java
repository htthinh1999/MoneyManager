package com.keycodemon.moneymanager.viewmodel;

public class Chart {
    private Long Revenue;
    private Long Expenditure;

    public Chart(Long revenue, Long expenditure) {
        Revenue = revenue;
        Expenditure = expenditure;
    }

    public Long getRevenue() {
        return Revenue;
    }

    public Long getExpenditure() {
        return Expenditure;
    }
}
