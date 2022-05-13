package com.zybooks.loginregisterseniorproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
//Eddie
public class FamilyUserExpense extends AppCompatActivity {
    EditText description, amountLost;
    TextView expenseText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_user_expense);

        description=findViewById(R.id.FamilyExpenseDescriptionEditText);
        amountLost =findViewById(R.id.FamilyExpenseAmountGainedEditText);
        expenseText=findViewById(R.id.FamilyExpenseReportTextView);
        DisplayExpense();
    }


    private void DisplayExpense() {
        // 1) Grab Saved Data
        SQLTableManager anotherTable = new SQLTableManager(this);
        //create temp data holder
        Cursor someOtherTable = anotherTable.GetTableData("Account_User_Family_User_Expense");
        //create a list to store all the data from the table
        ArrayList<FamilyUserExpenseInformation> familyUsers = new ArrayList<>();
        //make sure table is not empty
        if (someOtherTable.getCount() == 0) {
            expenseText.setVisibility(View.INVISIBLE);
        } else {
            //if not empty try
            expenseText.setVisibility(View.VISIBLE);
            try {
                //reads all data from the database and compares to what we are looking for
                while (someOtherTable.moveToNext()) {
                    //constant integer for column number
                    //starts from 0
                    String userName = (someOtherTable.getString(0));

                    if (userName.equals(GetCurrentFamilyUserName())) {
                        //add data to our list
                        familyUsers.add(new FamilyUserExpenseInformation(GetCurrentFamilyUserName(), someOtherTable.getString(1),
                                someOtherTable.getFloat(2), someOtherTable.getString(3)));
                    }
                }
                //toss error if nothing was found in the data search
            } catch (Exception e) {
                Toast.makeText(this, "error, couldn't show user data", Toast.LENGTH_SHORT).show();
            }
        }
        StringBuilder familyExpenseInformation = new StringBuilder();
        //for each float in the all lost revenue table
        for (FamilyUserExpenseInformation currentUser :
                familyUsers) {
            familyExpenseInformation.append(currentUser.toString());
        }
        expenseText.setText(familyExpenseInformation.toString());
    }

    public void FamilyExpenseReport(View view) {
        Date currentTime = Calendar.getInstance().getTime();

        SQLTableManager SQLTableManager = new SQLTableManager(this);

        Float amountGainedFloat= Float.parseFloat(amountLost.getText().toString());

        boolean familyUserIncomeSuccess=SQLTableManager.InsertFamilyUserExpense(GetCurrentFamilyUserName(),description.getText().toString(),
                amountGainedFloat,currentTime.toString());

        if(familyUserIncomeSuccess)
        {
//            Intent n = new Intent(this, FamilyMemberMainMenu.class);
//            startActivity(n);
            DisplayExpense();
        }
    }

    public String GetCurrentFamilyUserName()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("sharedPrefs",MODE_PRIVATE);
        String currentUserName=sharedPreferences.getString("familyUser","");
        return currentUserName;
    }



    private class FamilyUserExpenseInformation {
        String userName;
        String description;
        float lostRevenue;
        String date;

        public FamilyUserExpenseInformation(String userName, String description, float lostRevenue, String date) {
            this.userName = userName;
            this.description = description;
            this.lostRevenue = lostRevenue;
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

        public float getLostRevenue() {
            return lostRevenue;
        }


        @Override
        public String toString() {
            return "-$" + lostRevenue + "\n" + description + "\n" + date + "\n";
        }
    }
}