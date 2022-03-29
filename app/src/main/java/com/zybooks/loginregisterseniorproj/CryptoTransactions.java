//Jordan
package com.zybooks.loginregisterseniorproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class CryptoTransactions extends AppCompatActivity {
public EditText _btcvalueid;
public EditText _recipientID;
public EditText _makeReadOnly;
public EditText _descid;
public EditText _usdvalueid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_transactions);

      _btcvalueid = findViewById(R.id.btcvalueid);
      _recipientID = findViewById(R.id.recipientid);
      _descid = findViewById(R.id.descid);
      _usdvalueid = findViewById(R.id.usdvalueid);
      _makeReadOnly = findViewById(R.id.usdvalueid);
      _makeReadOnly.setFocusable(false);


        _btcvalueid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) // Updates live data
            {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void doSend(View view) {
        Intent currentintent=new Intent(this,CryptoTransactionsList.class);
        Intent intent=new Intent(CryptoTransactions.this,CryptoTransactionsList.class);
        intent.putExtra("BTCVal",_btcvalueid.getText().toString());
        intent.putExtra("Recip",_recipientID.getText().toString());
        intent.putExtra("DescID",_descid.getText().toString());
        intent.putExtra("UsdID",_usdvalueid.getText().toString());
        startActivity(currentintent);
    }


}