package com.zybooks.loginregisterseniorproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
//Eddie
public class CryptoMarket extends AppCompatActivity {
    EditText enteredText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_market);
        getSupportActionBar().setTitle("Crypto Market");
        enteredText=findViewById(R.id.enterCryptoSymbolText);
    }

    public void BrowseCryptoCurrencies(View view) {
    Intent n = new Intent(this,ShowCryptoCurrencies.class);
    startActivity(n);
    }

    public void SearchCrypto(View view) {
        Intent intent=new Intent(this,CryptoCurrencyInformation.class);
        intent.putExtra("SYMBOL",enteredText.getText().toString());
        startActivity(intent);
    }
}