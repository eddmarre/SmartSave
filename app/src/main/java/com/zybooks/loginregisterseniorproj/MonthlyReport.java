package com.zybooks.loginregisterseniorproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MonthlyReport extends AppCompatActivity {
    TextView HousingTextView,UtilitiesTextView,FoodTextView,MiscTextView,MedicalTextView,TotalTextView;
    float sum1, sum2, sum3, sum4, g1, sum5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_report);
        HousingTextView = (TextView) findViewById(R.id.HousingTextView);
        UtilitiesTextView = (TextView) findViewById(R.id.  UtilitiesTextView);
        FoodTextView = (TextView) findViewById(R.id.FoodTextView);
        MiscTextView = (TextView) findViewById(R.id.MiscTextView);
        MedicalTextView = (TextView) findViewById(R.id.  MedicalTextView);
        TotalTextView = (TextView) findViewById(R.id.TotalTextView);

        Intent recInt = getIntent();

        HousingTextView.setText("$" + recInt.getFloatExtra("11", g1));
        UtilitiesTextView.setText("$" + recInt.getFloatExtra("12", sum1));
        FoodTextView.setText("$" + recInt.getFloatExtra("13", sum2));
        MiscTextView.setText("$" + recInt.getFloatExtra("14", sum3));
        MedicalTextView.setText("$" + recInt.getFloatExtra("15", sum4));
        TotalTextView.setText("$" + recInt.getFloatExtra("16", sum5));
    }
}





