package com.zybooks.loginregisterseniorproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

//Eddie
public class StockMarket extends AppCompatActivity {
    EditText enteredText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_market);
        getSupportActionBar().setTitle("Stock Market");
        enteredText=findViewById(R.id.enterCryptoSymbolText);
    }

    public void ConnectButton(View view) {
        Intent intent=new Intent(StockMarket.this,CompanyStockInformation.class);
        intent.putExtra("SYMBOL",enteredText.getText().toString());
        startActivity(intent);

    }

    public void ShowCompaniesButton(View view) {
        Intent switchActivity=new Intent(StockMarket.this,ShowCompanies.class);
        startActivity(switchActivity);
    }
}