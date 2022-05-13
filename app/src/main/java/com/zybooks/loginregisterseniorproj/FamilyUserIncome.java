package com.zybooks.loginregisterseniorproj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
//Eddie
public class FamilyUserIncome extends AppCompatActivity {
    EditText description, amountGained;
    TextView incomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_user_income);

        description = findViewById(R.id.FamilyIncomeDescriptionEditText);
        amountGained = findViewById(R.id.FamilyIncomeAmountGainedEditText);
        incomeText = findViewById(R.id.FamilyIncomeReportTextView);
        DisplayIncome();
    }


    private void DisplayIncome() {
        // 1) Grab Saved Data
        SQLTableManager anotherTable = new SQLTableManager(this);
        //create temp data holder
        Cursor someOtherTable = anotherTable.GetTableData("Account_User_Family_User_Income");
        //create a list to store all the data from the table
        ArrayList<FamilyUserIncomeInformation> familyUsers = new ArrayList<>();
        //make sure table is not empty
        if (someOtherTable.getCount() == 0) {
            incomeText.setVisibility(View.INVISIBLE);
        } else {
            incomeText.setVisibility(View.VISIBLE);
            //if not empty try
            try {
                //reads all data from the database and compares to what we are looking for
                while (someOtherTable.moveToNext()) {
                    //constant integer for column number
                    //starts from 0
                    String userName = (someOtherTable.getString(0));

                    if (userName.equals(GetCurrentFamilyUserName())) {
                        //add data to our list
                        familyUsers.add(new FamilyUserIncomeInformation(GetCurrentFamilyUserName(), someOtherTable.getString(1),
                                someOtherTable.getFloat(2), someOtherTable.getString(3)));
                    }
                }
                //toss error if nothing was found in the data search
            } catch (Exception e) {

            }
        }
        StringBuilder familyIncomeInformation = new StringBuilder();
        //for each float in the all lost revenue table
        for (FamilyUserIncomeInformation currentUser :
                familyUsers) {
            familyIncomeInformation.append(currentUser.toString());
        }
        incomeText.setText(familyIncomeInformation.toString());
    }


    public void FamilyIncomeReport(View view) {
        Date currentTime = Calendar.getInstance().getTime();

        SQLTableManager SQLTableManager = new SQLTableManager(this);

        Float amountGainedFloat = Float.parseFloat(amountGained.getText().toString());

        boolean familyUserIncomeSuccess = SQLTableManager.InsertFamilyUserIncome(GetCurrentFamilyUserName(), description.getText().toString(),
                amountGainedFloat, currentTime.toString());

        if (familyUserIncomeSuccess) {
//            Intent n = new Intent(this, FamilyMemberMainMenu.class);
//            startActivity(n);

            DisplayIncome();
        }


    }

    public String GetCurrentFamilyUserName() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        String currentUserName = sharedPreferences.getString("familyUser", "");
        return currentUserName;
    }

    private class FamilyUserIncomeInformation {
        String userName;
        String description;
        float gainedRevenue;
        String date;

        public FamilyUserIncomeInformation(String userName, String description, float gainedRevenue, String date) {
            this.userName = userName;
            this.description = description;
            this.gainedRevenue = gainedRevenue;
            this.date = date;
        }

        public String getUserName() {
            return userName;
        }

        public String getDescription() {
            return description;
        }

        public String getDate() {
            return date;
        }

        public float getGainedRevenue() {
            return gainedRevenue;
        }


        @Override
        public String toString() {
            return "$" + gainedRevenue + "\n" + description + "\n" + date + "\n";
        }
    }
}

