package com.zybooks.loginregisterseniorproj;

import java.util.ArrayList;
//Eddie
public class Company {

    private String companySymbol;
    private ArrayList<StockData> companyStockPrices;

    public Company(String companySymbol, ArrayList<StockData> companyStockPrices) {
        this.companySymbol = companySymbol;
        this.companyStockPrices = companyStockPrices;
    }
    public String getCompanySymbol()
    {
        return companySymbol;
    }
    public ArrayList<StockData> getCompanyStockPrices()
    {
        return companyStockPrices;
    }
    public float getLastStockPrice()
    {

        return companyStockPrices.get(companyStockPrices.size()-1).getOpen();
    }
}
