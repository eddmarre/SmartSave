package com.zybooks.loginregisterseniorproj;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Budgeting extends AppCompatActivity {
    float budget;
    float amtLeft;
    float usedAmt;
    //usedAmt = budget - amtLeft;
    float budgetNoti1, budgetNoti2, budgetNoti3; // Number that will notify user that they have surpassed a percentage of their budget
    TextView budgetAmount;
    TextView usedAmount;
    TextView amountLeft;

    private ProgressBar budgetBar;
    private TextView budgetBarText;
    private int budgetBarStatus = 0;
    private Handler nHandler = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budgeting);
        budgetAmount = findViewById(R.id.budgetamt);
        usedAmount = findViewById(R.id.usedamt);
        amountLeft = findViewById(R.id.amountleftamt);
        budgetAmount.setText("0");
        usedAmount.setText("0");
        amountLeft.setText("0");

        budgetBar = (ProgressBar) findViewById(R.id.budgetbar);
        budgetBarText = (TextView) findViewById(R.id.budgetBarTextView);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(budgetBarStatus<100){
                    budgetBarStatus++;
                    android.os.SystemClock.sleep(50);
                    nHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            budgetBar.setProgress(budgetBarStatus);
                        }
                    });
                }
                nHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        budgetBarText.setVisibility(View.VISIBLE);
                    }
                });
            }
        }).start();
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
            budget -= currentFloat;
            amtLeft += currentFloat;
        }
        //budget -= amtLeft;
        // 3) Save the data again

        SaveData(budget);

        budgetNoti1 = (budget/(2f)); // %50 of budget
        budgetNoti2 = (budget/(4f/3f)); // 75% of budget
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

        if (budget > budgetNoti1) // More than half of budget is available
        {
            Toast.makeText(getApplicationContext(),"You have more than half of your budget remaining!",Toast.LENGTH_LONG)
                    .show();
        }
        else if (budget >= budgetNoti1) // 50%
        {
            Toast.makeText(getApplicationContext(),"You have passed the 50% mark of your budget.",Toast.LENGTH_LONG)
                    .show();
        }
        else if (budget >= budgetNoti2) // 75%
        {
            Toast.makeText(getApplicationContext(),"You have passed the 75% mark of your budget.",Toast.LENGTH_LONG)
                    .show();
        }
        else if (budget == budgetNoti3) // 100%
        {
            Toast.makeText(getApplicationContext(),"You have reached your budget!",Toast.LENGTH_LONG)
                    .show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG)
                    .show();
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
        String currentUserName=sharedPreferences.getString("KeyNm","");
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


