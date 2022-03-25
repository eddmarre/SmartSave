package com.zybooks.loginregisterseniorproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
//Burhan
public class UserMainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_menu);
        getSupportActionBar().hide();
    }

    public void crypto(View view) {
        Intent n= new Intent(this, Crypto.class);
        startActivity(n);
    }

    public void Finances(View view) {
        Intent n=new Intent(this,StockMarket.class);
        startActivity(n);
    }

    public void CryptoWallet(View view) {
        Intent n=new Intent(this,CryptoWallet.class);
        startActivity(n);
    }
}