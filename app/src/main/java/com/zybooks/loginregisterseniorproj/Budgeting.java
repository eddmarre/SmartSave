package com.zybooks.loginregisterseniorproj;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
//Jordan
public class Budgeting extends AppCompatActivity {
    float budget;
    float amtLeft;
    float usedAmt;
    float fiftypct, seventyfivepct, twentyfivepct; // Number that will notify user that they have surpassed a percentage of their budget
    TextView budgetAmount;
    TextView usedAmount;
    TextView amountLeft;
    private ProgressBar budgetBar;
    Button checkbudgetbutton;

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
        checkbudgetbutton = findViewById(R.id.checkbudgetbutton);

    }

    private void GrabTableData() {

        // 1) Grab Saved Data
        SQLTableManager anotherTable = new SQLTableManager(this);
        //create temp data holder
        Cursor someOtherTable = anotherTable.GetTableData("Account_User_Expense");
        //create a list to store all the data from the table
        ArrayList<Float> allLostRevenues = new ArrayList<>();
        //make sure table is not empty
        if (someOtherTable.getCount() == 0) {
        } else {
            //if not empty try
            try {
                //reads all data from the database and compares to what we are looking for
                while (someOtherTable.moveToNext()) {
                    //constant integer for column number
                    //starts from 0
                    String userName = (someOtherTable.getString(0));

                    if(userName.equals(GetCurrentUserName()))
                    {
                        final int lostRevenueColumnNumber = 2;
                        //add data to our list
                        allLostRevenues.add(someOtherTable.getFloat(lostRevenueColumnNumber));
                    }

                }
                //toss error if nothing was found in the data search
            } catch (Exception e) {
            }
        }
        //for each float in the all lost revenue table
        for (Float currentFloat :
                allLostRevenues) {
            usedAmt += currentFloat;
        }

        budget = GetBudget();
        // 3) Save the data again

        SaveData(usedAmt);
        StringBuilder NotifcationDebug = new StringBuilder();
        fiftypct = (budget/(2f)); // %50 of budget
        seventyfivepct = (budget/(4f/3f)); // 75% of budget
        twentyfivepct = (budget/4f); // %25 of budget used
        String debugString = String.valueOf(seventyfivepct);
        NotifcationDebug.append(fiftypct +"\n");
        NotifcationDebug.append(seventyfivepct +"\n");
        NotifcationDebug.append(twentyfivepct +"\n");


        float budgetAmountString = GetBudget();
        String budgetStringFloat = String.format("%.02f",budgetAmountString);
        budgetAmount.setText(budgetStringFloat);

        float usedAmountString = getSavedData();
        String usedAmountStringFloat = String.format("%.02f",usedAmountString);
        usedAmount.setText(usedAmountStringFloat);

        float amountLeftString = budgetAmountString - usedAmountString;
        String amountLeftStringFloat = String.format("%.02f", amountLeftString);
        amountLeft.setText(amountLeftStringFloat);

        // Put notification/toast code here

        Float progressPercent;
        progressPercent = (amtLeft/budget) * 100;
        int progressPercentInt = (int)Math.round(progressPercent);

        budgetBar.setProgress(progressPercentInt); // Sets progress bar
        if (amountLeftString < 0)
        {
            amountLeft.setText("0");
            Toast.makeText(this,"Beyond Limit",Toast.LENGTH_LONG)
                    .show();
        }
      else if (usedAmountStringFloat.equals(budgetStringFloat)) // 100%
        {
            Toast.makeText(this,"You reached your budget",Toast.LENGTH_LONG)
                    .show();
        }
        else if (usedAmountString > 0 && usedAmountString < twentyfivepct+1) // 75% of  budget left
        {
            Toast.makeText(this,"You have 75% of your budget remaining",Toast.LENGTH_LONG)
                    .show();
        }

         else if (usedAmountString > twentyfivepct +1 && usedAmountString < fiftypct +1) // Half of budget remaining
         {
             Toast.makeText(this,"You have half of your budget remaining",Toast.LENGTH_LONG)
                     .show();
         }

       else if (usedAmountString > seventyfivepct +1 && usedAmountString < fiftypct +1 ) // 25 % of budget used
        {
            Toast.makeText(this,"You have a quarter of your budget left",Toast.LENGTH_LONG)
                    .show();
        }
        else if (usedAmountString < budget && usedAmountString > seventyfivepct +1 ) // 75 percent and on
        {
            Toast.makeText(this,"You've nearly exceeded your budget!",Toast.LENGTH_LONG)
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

    public Float GetBudget()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("sharedPrefs2",MODE_PRIVATE);
        Float currentUserName=sharedPreferences.getFloat("getBudgetKeyName",0);
        return currentUserName;
    }

    public void check(View view)
    {
        GrabTableData();
        checkbudgetbutton.setVisibility(View.INVISIBLE);

    }




}