package com.zybooks.loginregisterseniorproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GetExpenseData extends AppCompatActivity {
    TextView testName, testDescription, testDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_expense_data);
    }

    private void GetExpenseData() {
        // 1) Grab Saved Data
        SQLTableManager anotherTable = new SQLTableManager(this);
        // 2) create temp data holder for everything in the dataBase
        Cursor someOtherTable = anotherTable.GetTableData("Account_User_Expense");
        // 3) A place where you can store data
        ArrayList<ExpenseRetrieval> allUserExpenses = new ArrayList<>();
        //Make sure the database isn't empty
        if (someOtherTable.getCount() == 0) {
            Toast.makeText(this, "error, database is empty", Toast.LENGTH_SHORT).show();
        } else {
            //if not empty try
            try {
                //reads all data from the database and compares to what we are looking for
                while (someOtherTable.moveToNext()) {
                    //Find the data from each column in the database
                    String userName = (someOtherTable.getString(0));
                    String date = (someOtherTable.getString(3));


                    //if date equals this than add it
                    if (date.equals("10/2/2000")) {
                        //Get the user's data from the database and store it in class
                        allUserExpenses.add(new ExpenseRetrieval(GetCurrentUserName(),
                                someOtherTable.getString(1), someOtherTable.getFloat(2),
                                someOtherTable.getString(3)));
                    }
                    //if is our user add it
                    if (userName.equals(GetCurrentUserName())) {
                        //Get the user's data from the database and store it in class
                        allUserExpenses.add(new ExpenseRetrieval(GetCurrentUserName(),
                                someOtherTable.getString(1), someOtherTable.getFloat(2),
                                someOtherTable.getString(3)));
                    }
                }
                //toss error if nothing was found in the data search
            } catch (Exception e) {
                Toast.makeText(this, "error, couldn't show user data", Toast.LENGTH_SHORT).show();
            }
        }

        //for each entry in the database Get the individual data here
        for (ExpenseRetrieval userExpense :
                allUserExpenses) {
            testDescription.setText(userExpense.getDescription());
            testName.setText(userExpense.getUserName());
            testDate.setText(userExpense.getDate());
        }

    }



    //Public access to get our saved userNames
    public String GetCurrentUserName() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        String currentUserName = sharedPreferences.getString("text", "");
        return currentUserName;
    }
}