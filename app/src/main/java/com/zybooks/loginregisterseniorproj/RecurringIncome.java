package com.zybooks.loginregisterseniorproj;

public class RecurringIncome {
    private String userName;
    private String description;
    private float lostRevenue;
    private String nextPaymentDate;
    private String recurringType;

    public RecurringIncome(String _userName, String _description, float _lostRevenue,String _nextPaymentDate,String _recurringType)
    {
        userName=_userName;
        description=_description;
        lostRevenue=_lostRevenue;
        nextPaymentDate=_nextPaymentDate;
        recurringType=_recurringType;
    }

    public String getDescription() {
        return description;
    }

    public String getUserName() {
        return userName;
    }

    public float getLostRevenue() {
        return lostRevenue;
    }

    public String getNextPaymentDate() {
        return nextPaymentDate;
    }

    public String getRecurringType() {
        return recurringType;
    }
}

