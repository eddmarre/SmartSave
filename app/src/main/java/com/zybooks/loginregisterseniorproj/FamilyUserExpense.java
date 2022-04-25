package com.zybooks.loginregisterseniorproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

public class FamilyUserExpense extends AppCompatActivity {
    EditText description, amountLost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_user_expense);

        description=findViewById(R.id.FamilyExpenseDescriptionEditText);
        amountLost =findViewById(R.id.FamilyExpenseAmountGainedEditText);
    }

    public void FamilyExpenseReport(View view) {
        Date currentTime = Calendar.getInstance().getTime();

        SQLTableManager SQLTableManager = new SQLTableManager(this);

        Float amountGainedFloat= Float.parseFloat(amountLost.getText().toString());

        boolean familyUserIncomeSuccess=SQLTableManager.InsertFamilyUserExpense(GetCurrentFamilyUserName(),description.getText().toString(),
                amountGainedFloat,currentTime.toString());

        if(familyUserIncomeSuccess)
        {
            Intent n = new Intent(this, FamilyMemberMainMenu.class);
            startActivity(n);
        }
    }

    public String GetCurrentFamilyUserName()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("sharedPrefs",MODE_PRIVATE);
        String currentUserName=sharedPreferences.getString("familyUser","");
        return currentUserName;
    }
}