package com.zybooks.loginregisterseniorproj;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AccountUserExpense extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView incomeReport;
    EditText description, lostRevenue;
    boolean isDaily = false, isWeekly = false, isMonthly = false, isOneTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_user_expense);



        Switch reoccurningExpenseSwitch = findViewById(R.id.ReoccuringExpenseSwitch);
        Spinner spinner = findViewById(R.id.ReocurringExpenseSpinner);
        incomeReport = findViewById(R.id.AccountUserExpenseText);
        description = findViewById(R.id.ExpenseDescriptionText);
        lostRevenue = findViewById(R.id.ExpenseLostRevenueText);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.howOften, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        spinner.setVisibility(View.INVISIBLE);

        reoccurningExpenseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    spinner.setVisibility(View.VISIBLE);
                    isOneTime = false;
                } else {
                    spinner.setVisibility(View.INVISIBLE);
                    isOneTime = true;
                }
            }
        });

        DisplayIncome();
    }

    private void DisplayIncome() {
        // 1) Grab Saved Data
        SQLTableManager anotherTable = new SQLTableManager(this);
        //create temp data holder
        Cursor someOtherTable = anotherTable.GetTableData("Account_User_Expense");
        //create a list to store all the data from the table
        ArrayList<UserExpense> expenses = new ArrayList<>();
        //make sure table is not empty
        if (someOtherTable.getCount() == 0) {
            incomeReport.setVisibility(View.INVISIBLE);
        } else {
            incomeReport.setVisibility(View.VISIBLE);
            //if not empty try
            try {
                //reads all data from the database and compares to what we are looking for
                while (someOtherTable.moveToNext()) {
                    //constant integer for column number
                    //starts from 0
                    String userName = (someOtherTable.getString(0));

                    if (userName.equals(getUserName())) {
                        //add data to our list
                        expenses.add(new UserExpense(getUserName(), someOtherTable.getString(1),
                                someOtherTable.getFloat(2), someOtherTable.getString(3)));
                    }
                }
                //toss error if nothing was found in the data search
            } catch (Exception e) {
                Toast.makeText(this, "error, couldn't show user data", Toast.LENGTH_SHORT).show();
            }
        }
        StringBuilder userExpenseInformation = new StringBuilder();
        //for each float in the all lost revenue table
        for (UserExpense currentUser :
                expenses) {
            userExpenseInformation.append(currentUser.toString());
        }
        incomeReport.setText(userExpenseInformation.toString());
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String currentOption = adapterView.getItemAtPosition(i).toString();
        if (currentOption.equals("Daily")) {
            isDaily = true;
            isWeekly = false;
            isMonthly = false;
        } else if (currentOption.equals("Weekly")) {
            isDaily = false;
            isWeekly = true;
            isMonthly = false;
        } else if (currentOption.equals("Monthly")) {
            isDaily = false;
            isWeekly = false;
            isMonthly = true;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private String getUserName() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        String currentUserName = sharedPreferences.getString("text", "");
        return currentUserName;
    }

    public void EnterExpense(View view) {
        SQLTableManager tableManager = new SQLTableManager(this);

        String _lostRevenue = lostRevenue.getText().toString();
        float lostRevenueFloat = Float.parseFloat(_lostRevenue);

        Date currentTime = Calendar.getInstance().getTime();

        tableManager.InsertUserExpense(getUserName(), description.getText().toString(), lostRevenueFloat, currentTime.toString());

        DisplayIncome();
        if (isOneTime) {
            return;
        }
        else
        {
            Intent getDateIntent=getIntent();
            String day,month;

            day=getDateIntent.getStringExtra("day");
            month=getDateIntent.getStringExtra("month");


            Toast.makeText(this, day+" "+month, Toast.LENGTH_SHORT).show();
        }


    }

    private void createNotificationChannel()
    {
        NotificationChannel channel= new NotificationChannel("notifyMe", "ReminderChannel", NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("channel for reminder");

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
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
            return "$" + lostRevenue + "\n" + description + "\n" + date + "\n\n";
        }
    }
}