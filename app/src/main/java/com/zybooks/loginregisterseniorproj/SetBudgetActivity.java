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
public class SetBudgetActivity extends AppCompatActivity {
EditText userBudgetText;
TextView showProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_budget);
        userBudgetText = findViewById(R.id.userBudget);
        showProfile=findViewById(R.id.ProfileInformationTextView);
        GrabTableData();
    }

    private void GrabTableData() {

        // 1) Grab Saved Data
        SQLTableManager anotherTable = new SQLTableManager(this);
        //create temp data holder
        Cursor someOtherTable = anotherTable.GetTableData("Account_User");
        //create a list to store all the data from the table
        ArrayList<AccountUserInformation> userInformations = new ArrayList<>();
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
                    String userName = (someOtherTable.getString(4));

                    if (userName.equals(GetCurrentUserName())) {
                        userInformations.add(new AccountUserInformation(someOtherTable.getString(1),
                                someOtherTable.getString(2), someOtherTable.getString(3), GetCurrentUserName(),
                                someOtherTable.getString(5)));
                    }

                }
                //toss error if nothing was found in the data search
            } catch (Exception e) {
                Toast.makeText(this, "error, couldn't show user data", Toast.LENGTH_SHORT).show();
            }
        }


        for (AccountUserInformation user :
                userInformations) {
                showProfile.setText(user.toString());
        }
    }

    public String GetCurrentUserName()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("sharedPrefs",MODE_PRIVATE);
        String currentUserName=sharedPreferences.getString("text","");
        return currentUserName;
    }

    public void SaveBudget(float data)
    {
        SQLTableManager tableManager=new SQLTableManager(this);

        // 1) Grab Saved Data
        SQLTableManager anotherTable = new SQLTableManager(this);
        //create temp data holder
        Cursor someOtherTable = anotherTable.GetTableData("Account_User_Budget");
        //create a list to store all the data from the table

        ArrayList<AccountUserBudget> userBudgets = new ArrayList<>();

        float totalBudget;
        float totalIncome;
        float totalExpense;
        //make sure table is not empty
        if (someOtherTable.getCount() == 0) {
            Toast.makeText(this, "error, database is empty", Toast.LENGTH_SHORT).show();
        } else {
            //if not empty try
            try {
                //reads all data from the database and compares to what we are looking for

                    while (someOtherTable.moveToNext()) {
                        if (someOtherTable.getString(0).equals(GetCurrentUserName())) {
                            userBudgets.add(new AccountUserBudget(someOtherTable.getString(0), someOtherTable.getFloat(1), someOtherTable.getFloat(2),
                                    someOtherTable.getFloat(3), someOtherTable.getString(4)));
                        }
                    }

                //toss error if nothing was found in the data search
            } catch (Exception e) {
                Toast.makeText(this, "error, couldn't show user data", Toast.LENGTH_SHORT).show();
            }
        }
        if(userBudgets.size()!=0)
        {
            totalExpense=userBudgets.get(userBudgets.size()-1).getTotalExpense();
            totalIncome=userBudgets.get(userBudgets.size()-1).getTotalIncome();
        }
        else
        {
            totalIncome=0;
            totalExpense=0;
        }

        Date date = Calendar.getInstance().getTime();
        tableManager.InsertAccountUserBudget(GetCurrentUserName(),totalIncome,totalExpense,data,date.toString());


        getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs2", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("getBudgetKeyName", data);
        editor.apply();

    }

    public void setBudgetClick(View view) {
        Float budget = Float.parseFloat(userBudgetText.getText().toString());
        SaveBudget(budget);
        Toast.makeText(this,"Your budget has been set!",Toast.LENGTH_LONG)
                .show();
    }

    private class AccountUserInformation
    {
        String firstName;
        String lastName;
        String DOB;
        String UserName;
        String phoneNumber;

        public AccountUserInformation(String firstName, String lastName, String DOB, String UserName, String phoneNumber)
        {
            this.firstName=firstName;
            this.lastName=lastName;
            this.DOB=DOB;
            this.UserName=UserName;
            this.phoneNumber=phoneNumber;
        }

        public String getUserName() {
            return UserName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getDOB() {
            return DOB;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        @Override
        public String toString() {
          return "User:\t\t"+UserName+"\n\nFirst Name:\t\t"+firstName+"\n\nLast Name:\t\t"+lastName+"\n\nDOB:\t\t"+DOB+"\n\nPhone Number:\t\t"+phoneNumber+"\n";
        }
    }

}