package com.zybooks.loginregisterseniorproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
//Eddie
public class Wallets extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallets);
        getSupportActionBar().setTitle("Wallets");
    }

    public void CryptoWalletButton(View view) {
        Intent n = new Intent(this, UserCryptoWallet.class);
        startActivity(n);
    }

    public void StockWalletButton(View view) {
        Intent n = new Intent(this,UserStockWallet.class);
        startActivity(n);
    }
}