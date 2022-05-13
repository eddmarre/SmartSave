package com.zybooks.loginregisterseniorproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
//Tino
public class TextReport extends AppCompatActivity {
    TextView MonITextView,TueITextView,WedITextView,ThursITextView,FriITextView,SatITextView,SunITextView,MonETextView,TueETextView,
            WedETextView,ThursETextView,FriETextView,SatETextView,SunETextView;
    float p1, p2, p3, p4, p5, p6, p7, p8, p9, p, p10, p11, p12, p13;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_report);
        MonITextView=(TextView) findViewById(R.id.MonITextView);
        MonETextView=(TextView) findViewById(R.id.MonETextView);
        TueITextView=(TextView) findViewById(R.id.TueITextView);
        TueETextView=(TextView) findViewById(R.id.TueETextView);
        WedITextView=(TextView) findViewById(R.id.WedITextView);
        WedETextView=(TextView) findViewById(R.id.WedETextView);
        ThursITextView=(TextView) findViewById(R.id.ThursITextView);
        ThursETextView=(TextView) findViewById(R.id.ThursETextView);
        FriITextView=(TextView) findViewById(R.id.FriITextView);
        FriETextView=(TextView) findViewById(R.id.FriETextView);
        SatITextView=(TextView) findViewById(R.id.SatITextView);
        SatETextView=(TextView) findViewById(R.id.SatETextView);
        SunITextView=(TextView) findViewById(R.id.SunITextView);
        SunETextView=(TextView) findViewById(R.id.SunETextView);
        Intent recInt=getIntent();

        MonITextView.setText("$"+recInt.getFloatExtra("0",p));
        MonETextView.setText("$"+recInt.getFloatExtra("1",p1));
        TueITextView.setText("$"+recInt.getFloatExtra("2",p2));
        TueETextView.setText("$"+recInt.getFloatExtra("3",p3));
        WedITextView.setText("$"+recInt.getFloatExtra("4",p4));
        WedETextView.setText("$"+recInt.getFloatExtra("5",p5));
        ThursITextView.setText("$"+recInt.getFloatExtra("6",p6));
        ThursETextView.setText("$"+recInt.getFloatExtra("7",p7));
        FriITextView.setText("$"+recInt.getFloatExtra("8",p8));
        FriETextView.setText("$"+recInt.getFloatExtra("9",p9));
        SatITextView.setText("$"+recInt.getFloatExtra("10",p10));
        SatETextView.setText("$"+recInt.getFloatExtra("11",p11));
        SunITextView.setText("$"+recInt.getFloatExtra("12",p12));
        SunETextView.setText("$"+recInt.getFloatExtra("13",p13));

    }
}

