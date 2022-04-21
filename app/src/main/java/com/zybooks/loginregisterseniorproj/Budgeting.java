package com.zybooks.loginregisterseniorproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Budgeting extends AppCompatActivity {
    float budget = 100;
    float budgetNoti; // Number that will notify user that they have surpassed a percentage of their budget
    TextView budgetAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budgeting);
        budgetAmount = findViewById(R.id.budgetamt);
        GrabTableData();

    }


    private void GrabTableData() {
        // 1) Save Data
        SaveData(budget);
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
                    final int lostRevenueColumnNumber = 2;
                    //add data to our list
                    allLostRevenues.add(someOtherTable.getFloat(lostRevenueColumnNumber));
                }
                //toss error if nothing was found in the data search
            } catch (Exception e) {
                Toast.makeText(this, "error, couldn't show user data", Toast.LENGTH_SHORT).show();
            }
        }
        //for each float in the all lost revenue table
        for (Float currentFloat :
                allLostRevenues) {
            budget -= currentFloat;
        }

        // 3) Save the data again

        SaveData(budget);
        budgetNoti = budget/(2f); // %50 of budget
        budgetNoti = budget/(4f/3f); // 75% of budget
        budgetNoti = budget; // %100 of budget used
        float budgetAmountString = getSavedData();
        String budgetString = String.valueOf(budgetAmountString);
        budgetAmount.setText(budgetString);

    }
        // 4) Grab saved data again
        private float getSavedData ()
        {
            SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
            float variableName = sharedPreferences.getFloat("budgetKeyName", 0);
            return variableName;
        }
    private void SaveData(float data)
    {
        getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("budgetKeyName", data);
        editor.apply();
    }
    }

