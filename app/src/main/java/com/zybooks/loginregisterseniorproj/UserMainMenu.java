package com.zybooks.loginregisterseniorproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

//Burhan
public class UserMainMenu extends AppCompatActivity {
    TextView debugHelper;
    float totalBudget = 0;
    float totalIncome = 0;
    float totalExpense = 0;
    Intent expenseIntent, incomeIntent;
    // variable for our bar chart
    BarChart barChart;

    // variable for our bar data set.
    BarDataSet barDataSet1, barDataSet2;

    String[] financials = new String[]{"Budget", "Income", "Expense"};
    String day, month;
    SQLTableManager table=new SQLTableManager(this);

    ArrayList<AccountUserBudget> userBudgets = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_menu);
        getSupportActionBar().hide();
//        debugHelper = findViewById(R.id.debugHelper);
//        debugHelper.setVisibility(View.INVISIBLE);
        SaveLastIncome(0);
        SaveLastExpense(0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        expenseIntent =new Intent(this, AccountUserExpense.class);
        incomeIntent=new Intent(this, AccountUserIncome.class);
        displayUserBudget();
        checkForRecurringPayments();
    }
    private void checkForRecurringPayments()
    {

        //create temp data holder
        Cursor recurringExpenseTable = table.GetTableData("Account_User_Recurring_Expense");
        //create a list to store all the data from the table
        ArrayList<RecurringExpense> allRecurringExpenses = new ArrayList<>();
        //make sure table is not empty
        if (recurringExpenseTable.getCount() == 0) {
           // Toast.makeText(this, "error, database is empty", Toast.LENGTH_SHORT).show();
        } else {
            //if not empty try
            try {
                //reads all data from the database and compares to what we are looking for
                while (recurringExpenseTable.moveToNext()) {
                    //constant integer for column number
                    //starts from 0
                    String userName = (recurringExpenseTable.getString(0));

                    if (userName.equals(GetCurrentUserName())) {
                        final int lostRevenueColumnNumber = 2;
                        //add data to our list
                        allRecurringExpenses.add(new RecurringExpense(GetCurrentUserName(),
                                recurringExpenseTable.getString(1), recurringExpenseTable.getFloat(2),
                                recurringExpenseTable.getString(3),recurringExpenseTable.getString(4)));
                    }

                }
                //toss error if nothing was found in the data search
            } catch (Exception e) {
                Toast.makeText(this, "error, couldn't show user data", Toast.LENGTH_SHORT).show();
            }
        }

        for(RecurringExpense expense:allRecurringExpenses)
        {
            Date today = Calendar.getInstance().getTime();

            String todayDateString = today.toString();
            String todayDateDay = todayDateString.substring(8, 10);
            String todayDateMonth = todayDateString.substring(4, 7);
            String todayDateYear = todayDateString.substring(24);

            StringBuilder todaySB = new StringBuilder();
            todaySB.append(todayDateDay + " " + todayDateMonth);

            if(todaySB.toString().equals(expense.getNextPaymentDate()))
            {
                table.InsertUserExpense(GetCurrentUserName(),expense.getDescription(),expense.getLostRevenue(),todaySB.toString());
                day=todayDateDay;
                month=todayDateMonth;
                int dayNumber=Integer.parseInt(todayDateDay);
                if(expense.getRecurringType().equals("daily"))
                {
                    dayNumber+=1;
                }
                if(expense.getRecurringType().equals("weekly"))
                {
                    dayNumber+=7;
                }
                CheckMonthMaxDay(month,dayNumber);
                if( expense.getRecurringType().equals("monthly"))
                {
                    CheckNextMonth(month);
                }
                String nextPaymentDate=day +" "+month;
                try {
                    table.UpdateRecurringExpenseDate(GetCurrentUserName(),expense.getDescription(),expense.getLostRevenue(),nextPaymentDate,expense.getRecurringType(),expense.getDescription());
                }
                catch (Exception e)
                {
                    Toast.makeText(this, "can't update table", Toast.LENGTH_SHORT).show();
                }

            }
        }



        //create temp data holder
        Cursor recurringIncomeTable = table.GetTableData("Account_User_Recurring_Income");
        //create a list to store all the data from the table
        ArrayList<RecurringIncome> allRecurringIncomes = new ArrayList<>();
        //make sure table is not empty
        if (recurringIncomeTable.getCount() == 0) {
            //Toast.makeText(this, "error, database is empty", Toast.LENGTH_SHORT).show();
        } else {
            //if not empty try
            try {
                //reads all data from the database and compares to what we are looking for
                while (recurringIncomeTable.moveToNext()) {
                    //constant integer for column number
                    //starts from 0
                    String userName = (recurringIncomeTable.getString(0));

                    if (userName.equals(GetCurrentUserName())) {
                        final int lostRevenueColumnNumber = 2;
                        //add data to our list
                        allRecurringIncomes.add(new RecurringIncome(GetCurrentUserName(),
                                recurringIncomeTable.getString(1), recurringIncomeTable.getFloat(2),
                                recurringIncomeTable.getString(3),recurringIncomeTable.getString(4)));
                    }

                }
                //toss error if nothing was found in the data search
            } catch (Exception e) {
                Toast.makeText(this, "error, couldn't show user data", Toast.LENGTH_SHORT).show();
            }
        }

        for(RecurringIncome incomes:allRecurringIncomes)
        {
            Date today = Calendar.getInstance().getTime();

            String todayDateString = today.toString();
            String todayDateDay = todayDateString.substring(8, 10);
            String todayDateMonth = todayDateString.substring(4, 7);
            String todayDateYear = todayDateString.substring(24);

            StringBuilder todaySB = new StringBuilder();
            todaySB.append(todayDateDay + " " + todayDateMonth);

            if(todaySB.toString().equals(incomes.getNextPaymentDate()))
            {
                table.InsertUserIncome(GetCurrentUserName(),incomes.getDescription(),incomes.getLostRevenue(),todaySB.toString());
                day=todayDateDay;
                month=todayDateMonth;
                int dayNumber=Integer.parseInt(todayDateDay);
                if(incomes.getRecurringType().equals("daily"))
                {
                    dayNumber+=1;
                }
                if(incomes.getRecurringType().equals("weekly"))
                {
                    dayNumber+=7;
                }
                CheckMonthMaxDay(month,dayNumber);
                if( incomes.getRecurringType().equals("monthly"))
                {
                    CheckNextMonth(month);
                }
                String nextPaymentDate=day +" "+month;
                try {
                    table.UpdateRecurringIncomeDate(GetCurrentUserName(),incomes.getDescription(),incomes.getLostRevenue(),nextPaymentDate,incomes.getRecurringType(),incomes.getDescription());
                }
                catch (Exception e)
                {
                    Toast.makeText(this, "can't update table", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    private void CheckMonthMaxDay(String currentMonth,int currentDateDay)
    {
        for(int i=1;i<13;i++)
        {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH, i);
            Date date = calendar.getTime();
            String futureDateMonth = date.toString().substring(4, 7);
            if(futureDateMonth.equals(currentMonth))
            {
                int lastDayOfMonth =calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                if(currentDateDay>=lastDayOfMonth)
                {
                    int nextMonthDateLeftOver=lastDayOfMonth-currentDateDay;
                    if(nextMonthDateLeftOver==0)
                    {
                        nextMonthDateLeftOver=1;
                    }

                    SetNextMonthDayDateAfterCheck(Math.abs(nextMonthDateLeftOver));
                    CheckNextMonth(currentMonth);
                }
            }
            else
            {
                day=String.format("%02d",currentDateDay);
            }
        }
    }

    private void SetNextMonthDayDateAfterCheck(int dateNumber)
    {
        String dateString=String.format("%02d",dateNumber);
        day =dateString;
    }


    private void CheckNextMonth(String _month)
    {
        for(int i=1;i<13;i++)
        {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH, i);
            Date date = calendar.getTime();
            String futureDateMonth = date.toString().substring(4, 7);
            if(futureDateMonth.equals(_month))
            {
                int nextMonth=i;
                if(i==12)
                {
                    nextMonth=1;
                }
                else
                {
                    nextMonth+=1;
                }
                calendar.set(Calendar.MONTH,nextMonth);
                date=calendar.getTime();
                futureDateMonth = date.toString().substring(4, 7);
                SetNextMonthAfterCheck(futureDateMonth);
            }
        }
    }

    private void SetNextMonthAfterCheck(String _nextMonth)
    {
        month=_nextMonth;
    }
    private void displayUserBudget() {
        // Grab Saved Data
        SQLTableManager tables = new SQLTableManager(this);

        GetFirstInitialTableData(tables);

        GetFinalTableData(tables);

        setDebugDisplayInformation();
    }

    private void GetFirstInitialTableData(SQLTableManager anotherTable) {
        //create temp data holder
        Cursor expenseTable = anotherTable.GetTableData("Account_User_Expense");
        //create a list to store all the data from the table
        ArrayList<Float> allLostRevenues = new ArrayList<>();
        //make sure table is not empty
        if (expenseTable.getCount() == 0) {

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
            if(GetLastExpense()==0)
            {
                totalExpense += currentFloat;
                SaveLastExpense(totalExpense);
            }
            else if(userBudgets.get(userBudgets.size()-1).getTotalExpense()==GetLastExpense())
            {
                totalExpense=userBudgets.get(userBudgets.size()-1).getTotalExpense();
            }
           else {
                 totalExpense += currentFloat;
                 SaveLastExpense(totalExpense);
            }
        }
        for (Float currentFloat :
                allGainedRevenues) {
            if(GetLastIncome()==0)
            {
                totalIncome += currentFloat;
                SaveLastIncome(totalIncome);
            }
            else if(userBudgets.get(userBudgets.size()-1).getTotalIncome()==GetLastIncome())
            {
                totalIncome=userBudgets.get(userBudgets.size()-1).getTotalIncome();
            }
            else {
                totalIncome += currentFloat;
               SaveLastIncome(totalIncome);
           }
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
    private void createChart(){
        // initializing variable for bar chart.
        barChart = findViewById(R.id.idBarChart2);

        // creating a new bar data set.
        barDataSet1 = new BarDataSet(getBarEntriesOne(), " ");
        barDataSet1.setColor(Color.GREEN);
        barDataSet2 = new BarDataSet(getBarEntriesTwo(), " ");
        barDataSet2.setColor(Color.CYAN);

        // below line is to add bar data set to our bar data.
        BarData data = new BarData(barDataSet1, barDataSet2);

        // after adding data to our bar data we
        // are setting that data to our bar chart.
        barChart.setData(data);

        // below line is to remove description
        // label of our bar chart.
        barChart.getDescription().setEnabled(false);

        // below line is to get x axis
        // of our bar chart.
        XAxis xAxis = barChart.getXAxis();

        // below line is to set value formatter to our x-axis and
        // we are adding our days to our x axis.
        xAxis.setValueFormatter(new IndexAxisValueFormatter(financials));
        // below line is to set center axis
        // labels to our bar chart.
        xAxis.setCenterAxisLabels(true);


        // below line is to set position
        // to our x-axis to bottom.
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        // below line is to set granularity
        // to our x axis labels.
        xAxis.setGranularity(1);

        // below line is to enable
        // granularity to our x axis.
        xAxis.setGranularityEnabled(true);

        // below line is to make our
        // bar chart as draggable.
        barChart.setDragEnabled(true);

        // below line is to make visible
        // range for our bar chart.
        barChart.setVisibleXRangeMaximum(7);

        // below line is to add bar
        // space to our chart.
        float barSpace = 0.1f;

        // below line is use to add group
        // spacing to our bar chart.
        float groupSpace = 0.40f;

        // we are setting width of
        // bar in below line.
        data.setBarWidth(0.2f);

        // below line is to set minimum
        // axis to our chart.
        barChart.getXAxis().setAxisMinimum(0);

        // below line is to
        // animate our chart.
        barChart.animateY(2000);

        // below line is to group bars
        // and add spacing to it.
        barChart.groupBars(0, groupSpace, barSpace);

        // below line is to invalidate
        // our bar chart.
        barChart.invalidate();
    }

    private ArrayList<BarEntry> getBarEntriesOne() {

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.

        ArrayList<BarEntry> entries = new ArrayList<>();
        //insert into chart
        barEntries.add(new BarEntry(1f, userBudgets.get(userBudgets.size() - 1).getBudget()));
        barEntries.add(new BarEntry(2f, userBudgets.get(userBudgets.size() - 1).getTotalIncome()));
        barEntries.add(new BarEntry(3f, userBudgets.get(userBudgets.size() - 1).getTotalExpense()));


//        barEntries.add(new BarEntry(1f, recInt.getFloatExtra("j",p)));
//        barEntries.add(new BarEntry(2f, recInt.getFloatExtra("b",p2)));
//        barEntries.add(new BarEntry(3f, recInt.getFloatExtra("d",p4)));
//        barEntries.add(new BarEntry(4f, recInt.getFloatExtra("f",p6)));
//        barEntries.add(new BarEntry(5f, recInt.getFloatExtra("h",p8)));
//        barEntries.add(new BarEntry(6f, recInt.getFloatExtra("k",p10)));
//        barEntries.add(new BarEntry(7f, recInt.getFloatExtra("m",p12)));

        return barEntries;
    }

    private ArrayList<BarEntry> getBarEntriesTwo() {


        ArrayList<BarEntry> barEntries = new ArrayList<>();
        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.

        ArrayList<BarEntry> entries = new ArrayList<>();
        //insert into chart
        barEntries.add(new BarEntry(1f, 0));
        barEntries.add(new BarEntry(2f, 0));
        barEntries.add(new BarEntry(3f, 0));

        return barEntries;
    }
    private void setDebugDisplayInformation() {
        //debugHelper.setVisibility(View.VISIBLE);
//        debugHelper.setText(userBudgets.get(userBudgets.size() - 1).toString());

        createChart();

        Date today = Calendar.getInstance().getTime();

        String todayDateString = today.toString();
        String todayDateDay = todayDateString.substring(8, 10);
        String todayDateMonth = todayDateString.substring(4, 7);
        String todayDateYear = todayDateString.substring(24);
        try {
            expenseIntent.putExtra("day", todayDateDay);
            expenseIntent.putExtra("month", todayDateMonth);
            incomeIntent.putExtra("day", todayDateDay);
            incomeIntent.putExtra("month", todayDateMonth);
        }
        catch(Exception e)
        {
            //Toast.makeText(this, "couldn't save", Toast.LENGTH_SHORT).show();
        }
        StringBuilder todaySB = new StringBuilder();
        todaySB.append(todayDateDay + " " + todayDateMonth + " " + todayDateYear);


        if (GetCalendarDate().equals("")) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
            Date lastDayOfMonth = cal.getTime();

            String lastDateString = lastDayOfMonth.toString();
            String lastDateDay = lastDateString.substring(8, 10);
            String lastDateMonth = lastDateString.substring(4, 7);
            String lastDateYear = lastDateString.substring(24);


            StringBuilder lastSB = new StringBuilder();


            lastSB.append(lastDateDay + " " + lastDateMonth + " " + lastDateYear);

            if (todaySB.toString().equals(lastSB.toString())) {

                Toast.makeText(this, "your budget has been reset", Toast.LENGTH_SHORT).show();
            } else
            {

            }
               // debugHelper.setText(todaySB.toString() + "\n" + lastSB.toString());
        }
        else
        {
            if(todaySB.toString().equals(GetCalendarDate()))
            {
                Toast.makeText(this, "your budget's income and expense have been reset, " +
                        "pick a new date for a budget reset or wait till end of month", Toast.LENGTH_LONG).show();
                Date date = Calendar.getInstance().getTime();
                table.InsertAccountUserBudget(GetCurrentUserName(), 0, 0, 0, date.toString());
                getSharedPreferences("sharedPrefs", MODE_PRIVATE);
                SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs2", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putFloat("getBudgetKeyName", 0);
                editor.apply();
            }
            //debugHelper.setText(todaySB.toString()+ "\n" + GetCalendarDate());
        }

    }

    private void resetBudget()
    {
        Date today = Calendar.getInstance().getTime();

        String todayDateString = today.toString();
        String todayDateDay = todayDateString.substring(8, 10);
        String todayDateMonth = todayDateString.substring(4, 7);
        String todayDateYear = todayDateString.substring(24);
        StringBuilder todaySB = new StringBuilder();
        todaySB.append(todayDateDay + " " + todayDateMonth + " " + todayDateYear);
        expenseIntent.putExtra("day",todayDateDay);
        expenseIntent.putExtra("month",todayDateMonth);
        incomeIntent.putExtra("day", todayDateDay);
        incomeIntent.putExtra("month", todayDateMonth);
        if (GetCalendarDate().equals("")) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
            Date lastDayOfMonth = cal.getTime();

            String lastDateString = lastDayOfMonth.toString();
            String lastDateDay = lastDateString.substring(8, 10);
            String lastDateMonth = lastDateString.substring(4, 7);
            String lastDateYear = lastDateString.substring(24);


            StringBuilder lastSB = new StringBuilder();


            lastSB.append(lastDateDay + " " + lastDateMonth + " " + lastDateYear);

            if (todaySB.toString().equals(lastSB.toString())) {
                Toast.makeText(this, "your budget's income and expense have been reset", Toast.LENGTH_SHORT).show();
            } else
            {

            }
               // debugHelper.setText(todaySB.toString() + "\n" + lastSB.toString());
        }
        else
        {
            if(todaySB.toString().equals(GetCalendarDate()))
            {
                Toast.makeText(this, "your budget's income and expense have been reset, " +
                        "pick a new date for a budget reset or wait till end of month", Toast.LENGTH_LONG).show();
            }
          //  debugHelper.setText(todaySB.toString()+ "\n" + GetCalendarDate());
        }
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
    public void Income(View view) {
        startActivity(incomeIntent);
    }
    public void Expense(View view) {
       // expenseIntent = new Intent(this, AccountUserExpense.class);
        startActivity(expenseIntent);
    }

    public void differentBudgetName2(View view) {
        Intent n = new Intent(this, FinanceReports.class);
        startActivity(n);
    }

    private String GetCurrentUserName() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        String currentUserName = sharedPreferences.getString("text", "");
        return currentUserName;
    }

    private String GetCalendarDate() {
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
        String currentUserName = sharedPreferences.getString("selectedDate", "");
        return currentUserName;
    }
    private Float GetLastIncome() {
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
        float currentUserName = sharedPreferences.getFloat("lastIncome", 0);
        return currentUserName;
    }
    private Float GetLastExpense() {
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
        float currentUserName = sharedPreferences.getFloat("lastExpense", 0);
        return currentUserName;
    }
    private void SaveLastIncome(float userName) {
        // SharedPreferences sharedPreferences =getSharedPreferences("sharedPreferencs",MODE_PRIVATE);
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("lastIncome", userName);
        editor.apply();
    }
    private void SaveLastExpense(float userName) {
        // SharedPreferences sharedPreferences =getSharedPreferences("sharedPreferencs",MODE_PRIVATE);
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("lastExpense", userName);
        editor.apply();
    }
}