package com.zybooks.loginregisterseniorproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
//Eddie
public class FamilyMemberMainMenu extends AppCompatActivity {
    TextView welcomeText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_member_main_menu);


        welcomeText=findViewById(R.id.welcomeTextTextView);

        StringBuilder sb = new StringBuilder();
        sb.append("welcome: ");
        sb.append(GetCurrentFamilyUserName());

        welcomeText.setText(sb.toString());
    }



    public String GetCurrentFamilyUserName()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("sharedPrefs",MODE_PRIVATE);
        String currentUserName=sharedPreferences.getString("familyUser","");
        return currentUserName;
    }

    public void FamilyUserLogOutOnClick(View view) {
        setSavedFamiyUser("");
        Intent n = new Intent(this, UserMainMenu.class);
        startActivity(n);
    }

    private void setSavedFamiyUser(String familyUserName)
    {
        // SharedPreferences sharedPreferences =getSharedPreferences("sharedPreferencs",MODE_PRIVATE);
        SharedPreferences sharedPreferences=getSharedPreferences("sharedPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("familyUser",familyUserName);
        editor.apply();
    }

    public void FamilyIncomeOnClick(View view) {
        Intent n = new Intent(this, FamilyUserIncome.class);
        startActivity(n);
    }

    public void FamilyExpenseOnClick(View view) {
        Intent n = new Intent(this, FamilyUserExpense.class);
        startActivity(n);
    }
}