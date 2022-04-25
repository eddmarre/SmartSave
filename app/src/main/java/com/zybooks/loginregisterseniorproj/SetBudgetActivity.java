package com.zybooks.loginregisterseniorproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SetBudgetActivity extends AppCompatActivity {
EditText userBudgetText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_budget);
        userBudgetText = findViewById(R.id.userBudget);
    }

    public void SaveBudget(float data)
    {
        getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs2", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("getBudgetKeyName", data);
        editor.apply();

    }

    public void setBudgetClick(View view) {
        Float budget = Float.parseFloat(userBudgetText.getText().toString());
        SaveBudget(budget);
        Toast.makeText(this,"Your budget has been set!",Toast.LENGTH_LONG)
                .show();
    }

}