package com.zybooks.loginregisterseniorproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.w3c.dom.Text;

import java.util.ArrayList;

//Tino
public class PieChartExpense extends AppCompatActivity {
    private PieChart pieChart;
    float SumA;
    float SumB;
    float SumC;
    float SumD;
    float SumE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart_expense);
        pieChart = findViewById(R.id.activity_main_piechart);
        setupPieChart();
        loadPieChartData();
    }

    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(10);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Categorized Spending");
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void loadPieChartData() {
        //get data from table
        Intent recInt = getIntent();
       float finalPrintedFloat = 0f;
       float finalPrintedFloat2 = 0f;
       float finalPrintedFloat3 = 0f;
       float finalPrintedFloat4 = 0f;
       float finalPrintedFloat5 = 0f;

       //initialize
        SQLTableManager anotherTable = new SQLTableManager(this);
        //create temp data holder
        Cursor someOtherTable = anotherTable.GetTableData("Account_User_Expense");
        //create a list to store all the data from the table
        ArrayList<Float> allLostRevenues = new ArrayList<>();
        //make sure table isnt empty
       if (someOtherTable.getCount() == 0) {

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

          }
      }
       //for each float in the all lost revenue table
      for (Float currentFloat :
               allLostRevenues) {
           //if the data in the database matches the current revenue
           if (currentFloat == recInt.getFloatExtra("1", SumA)) {
               //store the current float to display later
               finalPrintedFloat = currentFloat;
           }
       }
        for (Float currentFloat2 :
                allLostRevenues) {
            //if the data in the database matches the current revenue
            if (currentFloat2 == recInt.getFloatExtra("2", SumB)) {
                //store the current float to display later
                finalPrintedFloat2 = currentFloat2;
            }
        }

        for (Float currentFloat3 :
                allLostRevenues) {
            //if the data in the database matches the current revenue
            if (currentFloat3 == recInt.getFloatExtra("3", SumC)) {
                //store the current float to display later
                finalPrintedFloat3 = currentFloat3;
            }
        }

        for (Float currentFloat4 :
                allLostRevenues) {
            //if the data in the database matches the current revenue
            if (currentFloat4 == recInt.getFloatExtra("4", SumD)) {
                //store the current float to display later
                finalPrintedFloat4 = currentFloat4;
            }
        }

//        for (Float currentFloat5 :
//                allLostRevenues) {
//            //if the data in the database matches the current revenue
//            if (currentFloat5 == recInt.getFloatExtra("5", SumE)) {
//                //store the current float to display later
//                finalPrintedFloat5 = currentFloat5;
//            }
//        }

        ArrayList<PieEntry> entries = new ArrayList<>();
        //insert into chart
        entries.add(new PieEntry(finalPrintedFloat, "Food & Dining"));
        entries.add(new PieEntry(finalPrintedFloat2, "Medical"));
        entries.add(new PieEntry(finalPrintedFloat3, "Entertainment"));
        entries.add(new PieEntry(finalPrintedFloat4, "Electricity/Gas"));
       // entries.add(new PieEntry(finalPrintedFloat5, "Housing"));




//        entries.add(new PieEntry(recInt.getFloatExtra("1", SumA), "Food & Dining"));
//        entries.add(new PieEntry(recInt.getFloatExtra("2", SumB), "Medical"));
//        entries.add(new PieEntry(recInt.getFloatExtra("3", SumC), "Entertainment"));
//        entries.add(new PieEntry(recInt.getFloatExtra("4", SumD), "Electricity/Gas"));
//        entries.add(new PieEntry(recInt.getFloatExtra("5", SumE), "Housing"));

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color : ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color : ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "Expenses");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();

        pieChart.animateY(1400, Easing.EaseInOutQuad);

    }
}