package com.zybooks.loginregisterseniorproj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class CryptoTransactionsList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_transactions_list);

        TextView _RECIP;
        _RECIP = findViewById(R.id.showReciptextview);
        _RECIP.setText("");

        TextView _BTCVALUE;
        _BTCVALUE = findViewById(R.id.showBTCValue);
        _BTCVALUE.setText("");

        TextView _DESCID;
        _DESCID = findViewById(R.id.showDescTxtView);
        _DESCID.setText("");

        TextView _USDID;
        _USDID = findViewById(R.id.showUSDValue);
        _USDID.setText("");

        Bundle extras=getIntent().getExtras();
        String BTCValue="";
        String Recip="";
        String Desc="";
        String USDValue="";
        if(extras!=null)
        {
            BTCValue=extras.getString("BTCVal");
            _BTCVALUE.setText(BTCValue);

            Recip=extras.getString("Recip");
            _RECIP.setText(Recip);

            Desc=extras.getString("DescID");
            _DESCID.setText(Desc);

            USDValue=extras.getString("UsdID");
            _USDID.setText(USDValue);
        }



    }
}