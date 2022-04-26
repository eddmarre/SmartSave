package com.zybooks.loginregisterseniorproj;
//Data storage class
public class ExpenseRetrieval {
    String userName;
    String description;
    Float lostRevenue;
    String date;

    //public constructor to set the data
    public ExpenseRetrieval(String _userName, String _description, Float _lostRevenue,String _date)
    {
        userName=_userName;
        description=_description;
        lostRevenue=_lostRevenue;
        date=_date;
    }
    //Get your data
    public Float getLostRevenue() {
        return lostRevenue;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getUserName() {
        return userName;
    }
}
