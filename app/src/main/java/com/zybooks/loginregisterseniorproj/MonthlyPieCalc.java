package com.zybooks.loginregisterseniorproj;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//Tino
public class

MonthlyPieCalc extends AppCompatActivity {
    EditText ev, ev1, ev2, ev3, ev4, ev5, ev6, ev7, ev8, ev9, ev10;
    TextView sumOfU;
    TextView sumOfF;
    TextView sumOfM;
    TextView sumOfMe;
    TextView ev11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_pie_calc);
        ev = findViewById(R.id.ev);
        ev1 = findViewById(R.id.ev1);
        ev2 = findViewById(R.id.ev2);
        ev3 = findViewById(R.id.ev3);
        ev4 = findViewById(R.id.ev4);
        ev5 = findViewById(R.id.ev5);
        ev6 = findViewById(R.id.ev6);
        ev7 = findViewById(R.id.ev7);
        ev8 = findViewById(R.id.ev8);
        ev9 = findViewById(R.id.ev9);
        ev10 = findViewById(R.id.ev10);
        ev11 = findViewById(R.id.ev11);
        sumOfU = findViewById(R.id.sumOfU);
        sumOfF = findViewById(R.id.sumOfF);
        sumOfM = findViewById(R.id.sumOfM);
        sumOfMe = findViewById(R.id.sumOfMe);

    }

    public void USum(View view) {
        if (ev2.getText().toString().isEmpty() || //check if fields are empty then tells you to fill them in if they are
                ev3.getText().toString().isEmpty() ||
                ev4.getText().toString().isEmpty()) {
            Toast.makeText(MonthlyPieCalc.this, "Fill in all fields",
                    Toast.LENGTH_LONG).show();
        } else {
            float s1, s2, s3;
            s1 = Float.parseFloat(ev2.getText().toString());
            s2 = Float.parseFloat(ev3.getText().toString());
            s3 = Float.parseFloat(ev4.getText().toString());
            float sumofU2 = (s1 + s2 + s3);
            sumOfU.setText(String.format("%.2f", sumofU2));
        }
    }

    public void FSum(View view) {
        if (ev5.getText().toString().isEmpty() ||
                ev6.getText().toString().isEmpty()) {

            Toast.makeText(MonthlyPieCalc.this, "Fill in all fields",
                    Toast.LENGTH_LONG).show();
        } else {
            float f1, f2;
            f1 = Float.parseFloat(ev5.getText().toString());
            f2 = Float.parseFloat(ev6.getText().toString());
            float sumofF2 = (f1 + f2);
            sumOfF.setText(String.format("%.2f", sumofF2));


        }

    }

    public void MSum(View view) {
        if (ev7.getText().toString().isEmpty() ||
                ev8.getText().toString().isEmpty() ||
                ev9.getText().toString().isEmpty()) {
            Toast.makeText(MonthlyPieCalc.this, "Fill in all fields",
                    Toast.LENGTH_LONG).show();
        } else {
            float f3, f4, f5;
            f3 = Float.parseFloat(ev7.getText().toString());
            f4 = Float.parseFloat(ev8.getText().toString());
            f5 = Float.parseFloat(ev9.getText().toString());
            float sumofM2 = (f3 + f4 + f5);
            sumOfM.setText(String.format("%.2f", sumofM2));


        }

    }

    public void MeSum(View view) {
        if (ev.getText().toString().isEmpty() ||
                ev10.getText().toString().isEmpty()) {
            Toast.makeText(MonthlyPieCalc.this, "Fill in all fields",
                    Toast.LENGTH_LONG).show();
        } else {
            float f6, f7;
            f6 = Float.parseFloat(ev.getText().toString());
            f7 = Float.parseFloat(ev10.getText().toString());
            float sumofMe2 = (f6 + f7);
            sumOfMe.setText(String.format("%.2f", sumofMe2));


        }

    }

    public void ComputeExpenses(View view) {
        if (
                ev10.getText().toString().isEmpty() ||
                        ev.getText().toString().isEmpty() ||//check if fields are empty then tells you to fill them in if they are
                        ev1.getText().toString().isEmpty() ||
                        ev2.getText().toString().isEmpty() ||
                        ev3.getText().toString().isEmpty() ||
                        ev4.getText().toString().isEmpty() ||
                        ev5.getText().toString().isEmpty() ||
                        ev6.getText().toString().isEmpty() ||
                        ev7.getText().toString().isEmpty() ||
                        ev8.getText().toString().isEmpty() ||
                        ev9.getText().toString().isEmpty()) {
            Toast.makeText(MonthlyPieCalc.this, "Fill in all fields",
                    Toast.LENGTH_LONG).show();
        } else {
            float g1, g2, g3, g4, g5, g6, g7, g8, g9, g, g10, sum1, sum2, sum3, sum4, sum5;
            g1 = Float.parseFloat(ev1.getText().toString());
            g2 = Float.parseFloat(ev2.getText().toString());
            g3 = Float.parseFloat(ev3.getText().toString());
            g4 = Float.parseFloat(ev4.getText().toString());
            g5 = Float.parseFloat(ev5.getText().toString());
            g6 = Float.parseFloat(ev6.getText().toString());
            g7 = Float.parseFloat(ev7.getText().toString());
            g8 = Float.parseFloat(ev8.getText().toString());
            g9 = Float.parseFloat(ev9.getText().toString());
            g = Float.parseFloat(ev.getText().toString());
            g10 = Float.parseFloat(ev10.getText().toString());


            sum1 = Float.parseFloat(sumOfU.getText().toString());
            sum2 = Float.parseFloat(sumOfF.getText().toString());
            sum3 = Float.parseFloat(sumOfM.getText().toString());
            sum4 = Float.parseFloat(sumOfMe.getText().toString());
            sum5 = Float.parseFloat(ev11.getText().toString());

            float total = sum5;
            float SumA, SumB, SumC, SumD, SumE;
            SumA = (g1 / total) * 100;
            SumB = (sum1 / total) * 100;
            SumC = (sum2 / total) * 100;
            SumD = (sum3 / total) * 100;
            SumE = (sum4 / total) * 100;


            Intent n = new Intent(this, PieChartExpense.class);
            n.putExtra("1", sum1);
            n.putExtra("2", sum2);
            n.putExtra("3", sum3);
            n.putExtra("4", sum4);
            n.putExtra("5", sum5);

            n.putExtra("6", SumA);
            n.putExtra("7", SumB);
            n.putExtra("8", SumC);
            n.putExtra("9", SumD);
            n.putExtra("10", SumE);

            //intialize
            SQLTableManager thisTable = new SQLTableManager(this);

            //retrieve data
           SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
            String whatever = sharedPreferences.getString("text", "");
            //insert data
            boolean testMe =thisTable.InsertUserExpense(whatever, "TESTDATAOFSUMS", sum1, "10/2/2000");
            boolean testMe2 =thisTable.InsertUserExpense(whatever, "TESTDATAOFSUMS", sum2, "10/2/2000");
            boolean testMe3 =thisTable.InsertUserExpense(whatever, "TESTDATAOFSUMS", sum3, "10/2/2000");
            boolean testMe4 =thisTable.InsertUserExpense(whatever, "TESTDATAOFSUMS", sum4, "10/2/2000");
            //boolean testMe5 =thisTable.InsertUserExpense(whatever, "TESTDATAOFSUMS", sum5, "10/2/2000");
            //test Data
             if(!testMe)
           {
               Toast.makeText(this,"error inserting data",Toast.LENGTH_LONG);
          }
            startActivity(n);
        }
    }

    public void MeSum2(View view) {
        if (ev.getText().toString().isEmpty() ||
                ev10.getText().toString().isEmpty()) {
            Toast.makeText(MonthlyPieCalc.this, "Fill in all fields",
                    Toast.LENGTH_LONG).show();
        } else {
            float t1, t2, t3, t4, t5;
            t1 = Float.parseFloat(ev1.getText().toString());
            t2 = Float.parseFloat(sumOfU.getText().toString());
            t3 = Float.parseFloat(sumOfM.getText().toString());
            t4 = Float.parseFloat(sumOfMe.getText().toString());
            t5 = Float.parseFloat(sumOfF.getText().toString());
            float sumofMe3 = (t1 + t2 + t3 + t4 + t5);
            ev11.setText(String.format("%.2f", sumofMe3));


        }

    }

    public void TextReport2(View view) {
        if (
                ev10.getText().toString().isEmpty() ||
                        ev.getText().toString().isEmpty() ||//check if fields are empty then tells you to fill them in if they are
                        ev1.getText().toString().isEmpty() ||
                        ev2.getText().toString().isEmpty() ||
                        ev3.getText().toString().isEmpty() ||
                        ev4.getText().toString().isEmpty() ||
                        ev5.getText().toString().isEmpty() ||
                        ev6.getText().toString().isEmpty() ||
                        ev7.getText().toString().isEmpty() ||
                        ev8.getText().toString().isEmpty() ||
                        ev9.getText().toString().isEmpty()) {
            Toast.makeText(MonthlyPieCalc.this, "Fill in all fields",
                    Toast.LENGTH_LONG).show();
        } else {
            float g1, sum1, sum2, sum3, sum4, sum5;
            g1 = Float.parseFloat(ev1.getText().toString());
            sum1 = Float.parseFloat(sumOfU.getText().toString());
            sum2 = Float.parseFloat(sumOfF.getText().toString());
            sum3 = Float.parseFloat(sumOfM.getText().toString());
            sum4 = Float.parseFloat(sumOfMe.getText().toString());
            sum5 = Float.parseFloat(ev11.getText().toString());


            Intent n = new Intent(this, MonthlyReport.class);
            n.putExtra("11", g1);
            n.putExtra("12", sum1);
            n.putExtra("13", sum2);
            n.putExtra("14", sum3);
            n.putExtra("15", sum4);
            n.putExtra("16", sum5);

            startActivity(n);
        }
    }
}