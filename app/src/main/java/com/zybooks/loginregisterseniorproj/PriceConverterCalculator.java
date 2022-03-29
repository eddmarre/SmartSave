package com.zybooks.loginregisterseniorproj;
//Eddie
public class PriceConverterCalculator {
    float exchnageAmount;
    float ownedCurrencyAmount;

    public PriceConverterCalculator(float exchangeAmount, float ownedCurrencyAmount) {
        this.exchnageAmount = exchangeAmount;
        this.ownedCurrencyAmount = ownedCurrencyAmount;
    }

    public Float calculatePrice() {
        return ownedCurrencyAmount * exchnageAmount;
    }
}
