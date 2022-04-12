package com.zybooks.loginregisterseniorproj;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
//Tino
public class WeeklyBarChart extends AppCompatActivity {

    // variable for our bar chart
    BarChart barChart;

    // variable for our bar data set.
    BarDataSet barDataSet1, barDataSet2;

    // array list for storing entries.
    ArrayList barEntries;

    // creating a string array for displaying days.
    String[] days = new String[]{"Mon", "Tues", "Wed", "Thu", "Fri", "Sat", "Sun"};

    float p, p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_bar_chart);

        // initializing variable for bar chart.
        barChart = findViewById(R.id.idBarChart);

        // creating a new bar data set.
        barDataSet1 = new BarDataSet(getBarEntriesOne(), "Income");
        barDataSet1.setColor(Color.GREEN);
        barDataSet2 = new BarDataSet(getBarEntriesTwo(), "Expense");
        barDataSet2.setColor(Color.RED);

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
        xAxis.setValueFormatter(new IndexAxisValueFormatter(days));
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

    // array list for first set
    private ArrayList<BarEntry> getBarEntriesOne() {
        Intent recInt= getIntent();
        float finalPrintedFloat = 0f;
        float finalPrintedFloat2 = 0f;
        float finalPrintedFloat3 = 0f;
        float finalPrintedFloat4 = 0f;
        float finalPrintedFloat5 = 0f;
        float finalPrintedFloat6 = 0f;
        float finalPrintedFloat7 = 0f;

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.

        SQLTableManager anotherTable = new SQLTableManager(this);
        //create temp data holder
        Cursor someOtherTable = anotherTable.GetTableData("Account_User_Expense");
        //create a list to store all the data from the table
        ArrayList<Float> allLostRevenues = new ArrayList<>();
        //make sure table isnt empty
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

        for (Float currentFloat :
                allLostRevenues) {
            //if the data in the database matches the current revenue
            if (currentFloat == recInt.getFloatExtra("j", p)) {
                //store the current float to display later
                finalPrintedFloat = currentFloat;
            }
        }

        for (Float currentFloat2 :
                allLostRevenues) {
            //if the data in the database matches the current revenue
            if (currentFloat2 == recInt.getFloatExtra("b", p2)) {
                //store the current float to display later
                finalPrintedFloat2 = currentFloat2;
            }
        }

        for (Float currentFloat3 :
                allLostRevenues) {
            //if the data in the database matches the current revenue
            if (currentFloat3 == recInt.getFloatExtra("d", p4)) {
                //store the current float to display later
                finalPrintedFloat3 = currentFloat3;
            }
        }

        for (Float currentFloat4 :
                allLostRevenues) {
            //if the data in the database matches the current revenue
            if (currentFloat4 == recInt.getFloatExtra("f", p6)) {
                //store the current float to display later
                finalPrintedFloat4 = currentFloat4;
            }
        }

        for (Float currentFloat5 :
                allLostRevenues) {
            //if the data in the database matches the current revenue
            if (currentFloat5 == recInt.getFloatExtra("h", p8)) {
                //store the current float to display later
                finalPrintedFloat5 = currentFloat5;
            }
        }

        for (Float currentFloat6 :
                allLostRevenues) {
            //if the data in the database matches the current revenue
            if (currentFloat6 == recInt.getFloatExtra("k", p10)) {
                //store the current float to display later
                finalPrintedFloat6 = currentFloat6;
            }
        }

        for (Float currentFloat7 :
                allLostRevenues) {
            //if the data in the database matches the current revenue
            if (currentFloat7 == recInt.getFloatExtra("m", p12)) {
                //store the current float to display later
                finalPrintedFloat7 = currentFloat7;
            }
        }


        ArrayList<BarEntry> entries = new ArrayList<>();
        //insert into chart
        barEntries.add(new BarEntry(1f, finalPrintedFloat));
        barEntries.add(new BarEntry(2f, finalPrintedFloat2));
        barEntries.add(new BarEntry(3f, finalPrintedFloat3));
        barEntries.add(new BarEntry(4f, finalPrintedFloat4));
        barEntries.add(new BarEntry(5f, finalPrintedFloat5));
        barEntries.add(new BarEntry(6f, finalPrintedFloat6));
        barEntries.add(new BarEntry(7f, finalPrintedFloat7));




//        barEntries.add(new BarEntry(1f, recInt.getFloatExtra("j",p)));
//        barEntries.add(new BarEntry(2f, recInt.getFloatExtra("b",p2)));
//        barEntries.add(new BarEntry(3f, recInt.getFloatExtra("d",p4)));
//        barEntries.add(new BarEntry(4f, recInt.getFloatExtra("f",p6)));
//        barEntries.add(new BarEntry(5f, recInt.getFloatExtra("h",p8)));
//        barEntries.add(new BarEntry(6f, recInt.getFloatExtra("k",p10)));
//        barEntries.add(new BarEntry(7f, recInt.getFloatExtra("m",p12)));

        return barEntries;
    }

    // array list for second set.
    private ArrayList<BarEntry> getBarEntriesTwo() {
        Intent recInt= getIntent();
        // creating a new array list

        float finalPrintedFloat8 = 0f;
        float finalPrintedFloat9 = 0f;
        float finalPrintedFloat10 = 0f;
        float finalPrintedFloat11 = 0f;
        float finalPrintedFloat12 = 0f;
        float finalPrintedFloat13 = 0f;
        float finalPrintedFloat14 = 0f;

        ArrayList<BarEntry> barEntries = new ArrayList<>();

        SQLTableManager anotherTable = new SQLTableManager(this);
        //create temp data holder
        Cursor someOtherTable = anotherTable.GetTableData("Account_User_Expense");
        //create a list to store all the data from the table
        ArrayList<Float> allLostRevenues = new ArrayList<>();
        //make sure table isnt empty
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

        for (Float currentFloat8 :
                allLostRevenues) {
            //if the data in the database matches the current revenue
            if (currentFloat8 == recInt.getFloatExtra("a", p1)) {
                //store the current float to display later
                finalPrintedFloat8 = currentFloat8;
            }
        }

        for (Float currentFloat9 :
                allLostRevenues) {
            //if the data in the database matches the current revenue
            if (currentFloat9 == recInt.getFloatExtra("c", p3)) {
                //store the current float to display later
                finalPrintedFloat9 = currentFloat9;
            }
        }

        for (Float currentFloat10 :
                allLostRevenues) {
            //if the data in the database matches the current revenue
            if (currentFloat10 == recInt.getFloatExtra("e", p5)) {
                //store the current float to display later
                finalPrintedFloat10 = currentFloat10;
            }
        }

        for (Float currentFloat11 :
                allLostRevenues) {
            //if the data in the database matches the current revenue
            if (currentFloat11 == recInt.getFloatExtra("g", p7)) {
                //store the current float to display later
                finalPrintedFloat11 = currentFloat11;
            }
        }

        for (Float currentFloat12 :
                allLostRevenues) {
            //if the data in the database matches the current revenue
            if (currentFloat12 == recInt.getFloatExtra("i", p9)) {
                //store the current float to display later
                finalPrintedFloat12 = currentFloat12;
            }
        }

        for (Float currentFloat13 :
                allLostRevenues) {
            //if the data in the database matches the current revenue
            if (currentFloat13 == recInt.getFloatExtra("l", p11)) {
                //store the current float to display later
                finalPrintedFloat13 = currentFloat13;
            }
        }

        for (Float currentFloat14 :
                allLostRevenues) {
            //if the data in the database matches the current revenue
            if (currentFloat14 == recInt.getFloatExtra("n", p13)) {
                //store the current float to display later
                finalPrintedFloat14 = currentFloat14;
            }
        }


        ArrayList<BarEntry> entries = new ArrayList<>();
        //insert into chart
        barEntries.add(new BarEntry(1f, finalPrintedFloat8));
        barEntries.add(new BarEntry(2f, finalPrintedFloat9));
        barEntries.add(new BarEntry(3f, finalPrintedFloat10));
        barEntries.add(new BarEntry(4f, finalPrintedFloat11));
        barEntries.add(new BarEntry(5f, finalPrintedFloat12));
        barEntries.add(new BarEntry(6f, finalPrintedFloat13));
        barEntries.add(new BarEntry(7f, finalPrintedFloat14));

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
//        barEntries.add(new BarEntry(1f, recInt.getFloatExtra("a",p1)));
//        barEntries.add(new BarEntry(2f, recInt.getFloatExtra("c",p3)));
//        barEntries.add(new BarEntry(3f, recInt.getFloatExtra("e",p5)));
//        barEntries.add(new BarEntry(4f, recInt.getFloatExtra("g",p7)));
//        barEntries.add(new BarEntry(5f, recInt.getFloatExtra("i",p9)));
//        barEntries.add(new BarEntry(6f, recInt.getFloatExtra("l",p11)));
//        barEntries.add(new BarEntry(7f, recInt.getFloatExtra("n",p13)));

        return barEntries;
    }
}
