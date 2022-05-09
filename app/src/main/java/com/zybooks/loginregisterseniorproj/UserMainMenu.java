package com.zybooks.loginregisterseniorproj;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

//Burhan
public class UserMainMenu extends AppCompatActivity {
    TextView debugHelper;
    float totalBudget = 0;
    float totalIncome = 0;
    float totalExpense = 0;

    ArrayList<AccountUserBudget> userBudgets = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_menu);
        getSupportActionBar().hide();
        debugHelper = findViewById(R.id.debugHelper);
        debugHelper.setVisibility(View.INVISIBLE);
        displayUserBudget();
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayUserBudget();
    }

    private void displayUserBudget() {
        // Grab Saved Data
        SQLTableManager tables = new SQLTableManager(this);

        GetFirstInitialTableData(tables);

        GetFinalTableData(tables);

        setDisplayInformation();
    }

    private void GetFirstInitialTableData(SQLTableManager anotherTable) {
        //create temp data holder
        Cursor expenseTable = anotherTable.GetTableData("Account_User_Expense");
        //create a list to store all the data from the table
        ArrayList<Float> allLostRevenues = new ArrayList<>();
        //make sure table is not empty
        if (expenseTable.getCount() == 0) {
            Toast.makeText(this, "error, database is empty", Toast.LENGTH_SHORT).show();
        } else {
            //if not empty try
            try {
                //reads all data from the database and compares to what we are looking for
                while (expenseTable.moveToNext()) {
                    //constant integer for column number
                    //starts from 0
                    String userName = (expenseTable.getString(0));

                    if (userName.equals(GetCurrentUserName())) {
                        final int lostRevenueColumnNumber = 2;
                        //add data to our list
                        allLostRevenues.add(expenseTable.getFloat(lostRevenueColumnNumber));
                    }

                }
                //toss error if nothing was found in the data search
            } catch (Exception e) {
                Toast.makeText(this, "error, couldn't show user data", Toast.LENGTH_SHORT).show();
            }
        }

        //create temp data holder
        Cursor incomeTable = anotherTable.GetTableData("Account_User_Income");
        //create a list to store all the data from the table
        ArrayList<Float> allGainedRevenues = new ArrayList<>();
        //make sure table is not empty
        if (incomeTable.getCount() == 0) {
            Toast.makeText(this, "error, database is empty", Toast.LENGTH_SHORT).show();
        } else {
            //if not empty try
            try {
                //reads all data from the database and compares to what we are looking for
                while (incomeTable.moveToNext()) {
                    //constant integer for column number
                    //starts from 0
                    String userName = (incomeTable.getString(0));

                    if (userName.equals(GetCurrentUserName())) {
                        final int lostRevenueColumnNumber = 2;
                        //add data to our list
                        allGainedRevenues.add(incomeTable.getFloat(lostRevenueColumnNumber));
                    }

                }
                //toss error if nothing was found in the data search
            } catch (Exception e) {
                Toast.makeText(this, "error, couldn't show user data", Toast.LENGTH_SHORT).show();
            }
        }

        //create temp data holder
        Cursor someOtherTable = anotherTable.GetTableData("Account_User_Budget");
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

        //for each float in the all lost revenue table
        for (Float currentFloat :
                allLostRevenues) {
            totalExpense += currentFloat;
        }
        for (Float currentFloat :
                allGainedRevenues) {
            totalIncome += currentFloat;
        }
        Date date = Calendar.getInstance().getTime();

        if (userBudgets.size() != 0) {
            AccountUserBudget latestBudget = userBudgets.get(userBudgets.size() - 1);

            anotherTable.InsertAccountUserBudget(GetCurrentUserName(), totalIncome, totalExpense, latestBudget.getBudget(), date.toString());
        } else {
            anotherTable.InsertAccountUserBudget(GetCurrentUserName(), totalIncome, totalExpense, 0, date.toString());
        }
    }

    private void GetFinalTableData(SQLTableManager anotherTable) {
        //create temp data holder
        Cursor displayTable = anotherTable.GetTableData("Account_User_Budget");
        //make sure table is not empty
        if (displayTable.getCount() == 0) {
            Toast.makeText(this, "error, database is empty", Toast.LENGTH_SHORT).show();
        } else {
            //if not empty try
            try {
                //reads all data from the database and compares to what we are looking for
                while (displayTable.moveToNext()) {
                    if (displayTable.getString(0).equals(GetCurrentUserName())) {
                        userBudgets.add(new AccountUserBudget(displayTable.getString(0), displayTable.getFloat(1), displayTable.getFloat(2),
                                displayTable.getFloat(3), displayTable.getString(4)));
                    }
                }
                //toss error if nothing was found in the data search
            } catch (Exception e) {
                Toast.makeText(this, "error, couldn't show user data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setDisplayInformation() {
        debugHelper.setVisibility(View.VISIBLE);
        debugHelper.setText(userBudgets.get(userBudgets.size() - 1).toString());
    }

    public void crypto(View view) {
        Intent n = new Intent(this, Crypto.class);
        startActivity(n);
    }

    public void Finances(View view) {
        Intent n = new Intent(this, StockMarket.class);
        startActivity(n);
    }

    public void CryptoWallet(View view) {
        Intent n = new Intent(this, CryptoWallet.class);
        startActivity(n);
    }

    public void CurrencyExchange(View view) {
        Intent n = new Intent(this, ShowCurrencyInformations.class);
        startActivity(n);
    }

    public void ReportIncomeExpense(View view) {
        Intent n = new Intent(this, ReportingMain.class);
        startActivity(n);
    }

    public void CryptoMarket(View view) {
        Intent n = new Intent(this, CryptoMarket.class);
        startActivity(n);
    }

    public void Wallets(View view) {
        Intent n = new Intent(this, Wallets.class);
        startActivity(n);
    }

    public void Calender(View view) {
        Intent n = new Intent(this, Calender.class);
        startActivity(n);
    }

    public void differentBudgetName(View view) {
        Intent n = new Intent(this, Budgeting.class);
        startActivity(n);
    }

    public void SetBudget(View view) {
        Intent n = new Intent(this, SetBudgetActivity.class);
        startActivity(n);
    }

    public void LogOutOnClick(View view) {

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("text", "");
        editor.apply();

        Intent n = new Intent(this, MainActivity.class);
        startActivity(n);
        finish();
    }

    public void AddFamilyMemberButton(View view) {
        Intent n = new Intent(this, AddFamilyMember.class);
        startActivity(n);
    }

    public void ShowFamilyUserButton(View view) {
        Intent n = new Intent(this, ViewCurrentFamilyMembers.class);
        startActivity(n);
    }

    public void CryptoWalletButton(View view) {
        Intent n = new Intent(this, UserCryptoWallet.class);
        startActivity(n);
    }

    public void StockWalletButton(View view) {
        Intent n = new Intent(this, UserStockWallet.class);
        startActivity(n);
    }

    public void wInExp(View view) {
        Intent n = new Intent(this, WeeklyInExp.class);
        startActivity(n);
    }

    public void mexpense(View view) {
        Intent n = new Intent(this, MonthlyPieCalc.class);
        startActivity(n);
    }

    private String GetCurrentUserName() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        String currentUserName = sharedPreferences.getString("text", "");
        return currentUserName;
    }
}