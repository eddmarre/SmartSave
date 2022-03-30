package com.zybooks.loginregisterseniorproj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ReportingMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporting_main);
    }

    public void wInExp(View view) {
        Intent n = new Intent(this, WeeklyInExp.class);
        startActivity(n);
    }

    public void mexpense(View view) {
        Intent n = new Intent(this, MonthlyPieCalc.class);
        startActivity(n);
    }
}