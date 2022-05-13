package com.zybooks.loginregisterseniorproj;
//Eddie
public class AccountUserBudget {
    private String userName;
    private float totalIncome;
    private float totalExpense;
    private float budget;
    private String date;


    public AccountUserBudget(String _userName, float _totalIncome, float _totalExpense, float _budget, String _date)
    {
        userName=_userName;
        totalIncome=_totalIncome;
        totalExpense=_totalExpense;
        budget=_budget;
        date=_date;
    }

    public float getTotalIncome() {
        return totalIncome;
    }

    public float getTotalExpense() {
        return totalExpense;
    }

    public float getBudget() {
        return budget;
    }

    @Override
    public String toString() {
        return "total budget: "+budget+"\ntotal income: " +totalIncome + "\ntotal expense: "+totalExpense;
    }
}
