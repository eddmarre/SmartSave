package com.zybooks.loginregisterseniorproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//Burhan
public class WeeklyInExp extends AppCompatActivity {
    TextView text01, income, expense, d1, d2, d3, d4, d5, d6, d7;
    EditText eva, eva1, eva2, eva3, eva4, eva5, eva6, eva7, eva8, eva9, eva10, eva11, eva12, eva13;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_in_exp);
        text01 =(TextView) findViewById(R.id.text01);
        income =(TextView) findViewById(R.id.income);
        expense=(TextView) findViewById(R.id.expense);
        d1=(TextView) findViewById(R.id.d1);
        d2=(TextView) findViewById(R.id.d2);
        d3=(TextView) findViewById(R.id.d3);
        d4=(TextView) findViewById(R.id.d4);
        d5=(TextView) findViewById(R.id.d5);
        d6=(TextView) findViewById(R.id.d6);
        d7=(TextView) findViewById(R.id.d7);

        eva = findViewById(R.id.eva);
        eva1 = findViewById(R.id.eva1);
        eva2 = findViewById(R.id.eva2);
        eva3 = findViewById(R.id.eva3);
        eva4 = findViewById(R.id.eva4);
        eva5 = findViewById(R.id.eva5);
        eva6 = findViewById(R.id.eva6);
        eva7 = findViewById(R.id.eva7);
        eva8 = findViewById(R.id.eva8);
        eva9 = findViewById(R.id.eva9);
        eva10 = findViewById(R.id.eva10);
        eva11 = findViewById(R.id.eva11);
        eva12 = findViewById(R.id.eva12);
        eva13 = findViewById(R.id.eva13);

    }

    public void Compute(View view) {
        if(
                eva13.getText().toString().isEmpty() ||
                        eva12.getText().toString().isEmpty() ||
                        eva11.getText().toString().isEmpty() ||
                        eva10.getText().toString().isEmpty() ||
                        eva.getText().toString().isEmpty() ||//check if fields are empty then tells you to fill them in if they are
                        eva1.getText().toString().isEmpty() ||
                        eva2.getText().toString().isEmpty() ||
                        eva3.getText().toString().isEmpty() ||
                        eva4.getText().toString().isEmpty() ||
                        eva5.getText().toString().isEmpty() ||
                        eva6.getText().toString().isEmpty() ||
                        eva7.getText().toString().isEmpty() ||
                        eva8.getText().toString().isEmpty() ||
                        eva9.getText().toString().isEmpty()) {
            Toast.makeText(WeeklyInExp.this, "Fill in all fields",
                    Toast.LENGTH_LONG).show();
        }else {
            float p1, p2, p3, p4, p5, p6, p7, p8, p9, p, p10, p11, p12, p13;
            p = Float.parseFloat(eva.getText().toString());
            p1 = Float.parseFloat(eva1.getText().toString());
            p2 = Float.parseFloat(eva2.getText().toString());
            p3 = Float.parseFloat(eva3.getText().toString());
            p4 = Float.parseFloat(eva4.getText().toString());
            p5 = Float.parseFloat(eva5.getText().toString());
            p6 = Float.parseFloat(eva6.getText().toString());
            p7 = Float.parseFloat(eva7.getText().toString());
            p8 = Float.parseFloat(eva8.getText().toString());
            p9 = Float.parseFloat(eva9.getText().toString());
            p10 = Float.parseFloat(eva10.getText().toString());
            p11 = Float.parseFloat(eva11.getText().toString());
            p12 = Float.parseFloat(eva12.getText().toString());
            p13 = Float.parseFloat(eva13.getText().toString());
            Intent n = new Intent(this, WeeklyBarChart.class);
            n.putExtra("a", p1);
            n.putExtra("b", p2);
            n.putExtra("c", p3);
            n.putExtra("d", p4);
            n.putExtra("e", p5);
            n.putExtra("f", p6);
            n.putExtra("g", p7);
            n.putExtra("h", p8);
            n.putExtra("i", p9);
            n.putExtra("j", p);
            n.putExtra("k", p10);
            n.putExtra("l", p11);
            n.putExtra("m", p12);
            n.putExtra("n", p13);

            startActivity(n);
        }
    }
}

