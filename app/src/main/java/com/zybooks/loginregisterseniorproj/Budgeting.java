package com.zybooks.loginregisterseniorproj;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Budgeting extends AppCompatActivity {
    float budget = 100;
    float amtLeft = 100;
    float usedAmt = 100;
    float budgetNoti1, budgetNoti2, budgetNoti3; // Number that will notify user that they have surpassed a percentage of their budget
    TextView budgetAmount;
    TextView usedAmount;
    TextView amountLeft;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budgeting);
        budgetAmount = findViewById(R.id.budgetamt);
        usedAmount = findViewById(R.id.usedamt);
        amountLeft = findViewById(R.id.amountleftamt);
        budgetAmount.setText("500");
        usedAmount.setText("600");
        amountLeft.setText("700");
    }


    private void GrabTableData() {
        // 1) Save Data
        SaveData(0);
        // 2) Grab Saved Data
        SQLTableManager anotherTable = new SQLTableManager(this);
        //create temp data holder
        Cursor someOtherTable = anotherTable.GetTableData("Account_User_Expense");
        //create a list to store all the data from the table
        ArrayList<Float> allLostRevenues = new ArrayList<>();
        //make sure table is not empty
        if (someOtherTable.getCount() == 0) {
            Toast.makeText(this, "error, database is empty", Toast.LENGTH_SHORT).show();
        } else {
            //if not empty try
            try {
                //reads all data from the database and compares to what we are looking for
                while (someOtherTable.moveToNext()) {
                    //constant integer for column number
                    //starts from 0
                    if(someOtherTable.getString(0).equals(GetCurrentUserName()))
                    {
                        final int lostRevenueColumnNumber = 2;
                        //add data to our list
                        allLostRevenues.add(someOtherTable.getFloat(lostRevenueColumnNumber));
                    }

                }
                //toss error if nothing was found in the data search
            } catch (Exception e) {
                Toast.makeText(this, "error, couldn't show user data", Toast.LENGTH_SHORT).show();
            }
        }
        //for each float in the all lost revenue table
        for (Float currentFloat :
                allLostRevenues) {
            //budget -= currentFloat;
            amtLeft += currentFloat;
        }
        budget -= amtLeft;
        // 3) Save the data again

        SaveData(budget);
        budgetNoti1 = budget/(2f); // %50 of budget
        budgetNoti2 = budget/(4f/3f); // 75% of budget
        budgetNoti3 = budget; // %100 of budget used

        float budgetAmountString = getSavedData();
        String budgetStringFloat = String.format("%.02f",budgetAmountString);
        budgetAmount.setText(budgetStringFloat);

        float usedAmountString = getSavedData();
        String usedAmountStringFloat = String.format("%.02f",usedAmountString);
        usedAmount.setText(usedAmountStringFloat);

        float amountLeftString = getSavedData();
        String amountLeftStringFloat = String.format("%.02f", amountLeftString);
        amountLeft.setText(amountLeftStringFloat);

        // Put notification/toast code here

        if (budget < budgetNoti1) // 50%
        {

        }
        else if (budget < budgetNoti2) // 75%
        {

        }
        else if (budget < budgetNoti3) // 100%
        {

        }

    }
        // 4) Grab saved data again
        private float getSavedData ()
        {
            SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
            float variableName = sharedPreferences.getFloat("keyNm", 0);
            return variableName;
        }
    public String GetCurrentUserName()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("sharedPrefs",MODE_PRIVATE);
        String currentUserName=sharedPreferences.getString("text","");
        return currentUserName;
    }

    private void SaveData(float data)
    {
        getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("keyNm", data);
        editor.apply();

    }

    public void check(View view)
    {
        GrabTableData();
    }
}

