package com.zybooks.loginregisterseniorproj;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
//Jordan
public class CryptoWallet extends AppCompatActivity {
private Button button6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_wallet);
        getSupportActionBar().setTitle("Wallet Address");

        button6 = (Button) findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCryptoTransactions();
            }
        });
    }
public void openCryptoTransactions() {
  //  Intent intent = new Intent(CryptoWallet.this, CryptoTransactions.class);
   // startActivity(intent);

}


    public void showAlertDialog(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("What's this?");
        alert.setMessage("Your Bitcoin address is a unique address that allows " +
                "users to securely send and receive Bitcoin. " +
                "By scanning the QR code above you can view your address and get to earning some Bitcoin! ");
        alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(CryptoWallet.this, "Don't forget to SaveSmart!",Toast.LENGTH_SHORT).show();
            }
        });
        alert.create().show();
    }
}