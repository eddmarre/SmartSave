package com.zybooks.loginregisterseniorproj;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

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
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntries.add(new BarEntry(1f, recInt.getFloatExtra("j",p)));
        barEntries.add(new BarEntry(2f, recInt.getFloatExtra("b",p2)));
        barEntries.add(new BarEntry(3f, recInt.getFloatExtra("d",p4)));
        barEntries.add(new BarEntry(4f, recInt.getFloatExtra("f",p6)));
        barEntries.add(new BarEntry(5f, recInt.getFloatExtra("h",p8)));
        barEntries.add(new BarEntry(6f, recInt.getFloatExtra("k",p10)));
        barEntries.add(new BarEntry(7f, recInt.getFloatExtra("m",p12)));
        return barEntries;
    }

    // array list for second set.
    private ArrayList<BarEntry> getBarEntriesTwo() {
        Intent recInt= getIntent();
        // creating a new array list
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntries.add(new BarEntry(1f, recInt.getFloatExtra("a",p1)));
        barEntries.add(new BarEntry(2f, recInt.getFloatExtra("c",p3)));
        barEntries.add(new BarEntry(3f, recInt.getFloatExtra("e",p5)));
        barEntries.add(new BarEntry(4f, recInt.getFloatExtra("g",p7)));
        barEntries.add(new BarEntry(5f, recInt.getFloatExtra("i",p9)));
        barEntries.add(new BarEntry(6f, recInt.getFloatExtra("l",p11)));
        barEntries.add(new BarEntry(7f, recInt.getFloatExtra("n",p13)));

        return barEntries;
    }
}
