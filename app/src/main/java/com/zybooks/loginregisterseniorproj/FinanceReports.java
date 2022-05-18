package com.zybooks.loginregisterseniorproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
//Tino
public class FinanceReports extends AppCompatActivity {

    TextView incomeFinanceReportTV;
    EditText incomeFinanceReport;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_reports);

        incomeFinanceReport = findViewById(R.id.incomeFinanceReport);
        incomeFinanceReportTV = findViewById(R.id.incomeFinanceReviewTV);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        AdView adView = new AdView(this);

        adView.setAdSize(AdSize.BANNER);

        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
// TODO: Add adView to your view hierarchy.

    }

    private String GetCurrentUserName() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        String currentUserName = sharedPreferences.getString("text", "");
        return currentUserName;
    }

    private void getIncomesAndExpense(SQLTableManager anotherTable) {
        //create temp data holder
        Cursor expenseTable = anotherTable.GetTableData("Account_User_Expense");
        //create a list to store all the data from the table
        ArrayList<UserExpense> expenses = new ArrayList<>();
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
                        expenses.add(new UserExpense(GetCurrentUserName(), expenseTable.getString(1),
                                expenseTable.getFloat(2), expenseTable.getString(3)));
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
        ArrayList<UserIncome> incomes = new ArrayList<>();
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
                    String incomeTables = (incomeTable.getString(0));

                    if (incomeTables.equals(GetCurrentUserName())) {
                        final int lostRevenueColumnNumber = 2;
                        //add data to our list
                        incomes.add(new UserIncome(GetCurrentUserName(), incomeTable.getString(1),
                                incomeTable.getFloat(2), incomeTable.getString(3)));
                    }

                }
                //toss error if nothing was found in the data search
            } catch (Exception e) {
                Toast.makeText(this, "error, couldn't show user data", Toast.LENGTH_SHORT).show();
            }
        }
        Date today = Calendar.getInstance().getTime();
        String todayDateString = today.toString();
        String todayDateDay = todayDateString.substring(8, 10);
        String todayDateMonth = todayDateString.substring(4, 7);
        String todayDateYear = todayDateString.substring(24);
        ArrayList<UserExpense> userExpenses = new ArrayList<>();
        ArrayList<UserIncome> userIncomes = new ArrayList<>();
        //for each float in the all lost revenue table
        for (UserExpense expense :
                expenses) {
            try {
                String expenseDayDate = expense.getDate().substring(8, 10);
                String expenseMonthDate = expense.getDate().substring(4, 7);
                String expenseYearDate = expense.getDate().substring(24);
                StringBuilder stringbuild = new StringBuilder();
                stringbuild.append(expenseDayDate + " " + expenseMonthDate + " " + expenseYearDate);

            if (stringbuild.toString().equals (incomeFinanceReport.getText().toString())){
                userExpenses.add(expense);

            }
            }
            catch (Exception e)
            {

            }
        }
        for (UserIncome income :
                incomes) {
            try {
                String incomeDayDate = income.getDate().substring(8, 10);
                String incomeMonthDate = income.getDate().substring(4, 7);
                String incomeYearDate = income.getDate().substring(24);
                StringBuilder stringbuild = new StringBuilder();
                stringbuild.append(incomeDayDate + " " + incomeMonthDate + " " + incomeYearDate);
                if (stringbuild.toString().equals(incomeFinanceReport.getText().toString())) {
                    userIncomes.add(income);

                }
            }
            catch(Exception e)
            {

            }
        }
        StringBuilder stringbuild2 = new StringBuilder();
        StringBuilder stringbuild3 = new StringBuilder();

        for (UserIncome income :
                userIncomes) {
            stringbuild3.append(income.toString());
        }
        for (UserExpense expense :
                userExpenses){
            stringbuild2.append(expense.toString());
        }
        incomeFinanceReportTV.setText(stringbuild2.toString() + "\n" + stringbuild3.toString());
    }

    public void financeButton(View view) {
        SQLTableManager incomeExpenseTable = new SQLTableManager(this);
        getIncomesAndExpense(incomeExpenseTable);
    }

    private class UserIncome {
        private String userName;
        private String description;
        private float gainedRevenue;
        private String date;

        public UserIncome(String _userName, String _description, float _gainedRevenue, String _date) {
            userName = _userName;
            description = _description;
            gainedRevenue = _gainedRevenue;
            date = _date;
        }

        @Override
        public String toString() {
            return "$" + gainedRevenue + "\n" + description + "\n" + date + "\n\n";
        }

        public String getDate() {
            return date;
        }
    }


    private class UserExpense {
        private String userName;
        private String description;
        private float lostRevenue;
        private String date;

        public UserExpense(String _userName, String _description, float _lostRevenue, String _date) {
            userName = _userName;
            description = _description;
            lostRevenue = _lostRevenue;
            date = _date;
        }

        @Override
        public String toString() {
            return "-$" + lostRevenue + "\n" + description + "\n" + date + "\n\n";
        }

        public String getDate() {
            return date;
        }
    }
}
