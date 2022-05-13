package com.zybooks.loginregisterseniorproj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;
//Eddie
public class ViewCurrentFamilyMembers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_current_family_members);

        CreateButtons();
    }

    //Eddie
    private void CreateButtons() {

        // 1) Grab Saved Data
        SQLTableManager anotherTable = new SQLTableManager(this);
        //create temp data holder
        Cursor someOtherTable = anotherTable.GetTableData("Account_User_Family_User");
        //create a list to store all the data from the table

        ArrayList<FamilyUser> users = new ArrayList<>();
        //make sure table is not empty
        if (someOtherTable.getCount() == 0) {
            Toast.makeText(this, "error, database is empty", Toast.LENGTH_SHORT).show();
        } else {
            //if not empty try
            try {
                //reads all data from the database and compares to what we are looking for
                while (someOtherTable.moveToNext()) {
                    users.add(new FamilyUser(someOtherTable.getString(1), someOtherTable.getString(2), someOtherTable.getString(3), someOtherTable.getString(4),
                            someOtherTable.getString(5),someOtherTable.getString(6)));
                }
                //toss error if nothing was found in the data search
            } catch (Exception e) {
                Toast.makeText(this, "error, couldn't show user data", Toast.LENGTH_SHORT).show();
            }
        }

        setContentView(R.layout.activity_view_current_family_members);
        ScrollView scrollView = new ScrollView(this);
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < users.size(); i++) {
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);
            ll.setTag(i);

            Button b = new Button(this);
            ;
            b.setTag(i);
            b.setText(users.get(i).getFirstName());
            b.setWidth(2000);
            b.setTextColor(Color.BLACK);
            //created custom class that will take in index for parameters
            b.setOnClickListener(new CustomOnClickListener(i) {
                @Override
                public void onClick(View view) {
                    setSavedFamiyUser(users.get(index).getUserId());
                        Intent n = new Intent(ViewCurrentFamilyMembers.this, FamilyMemberMainMenu.class);
                        startActivity(n);
//
                }
            });
            ll.setBackgroundColor(Color.TRANSPARENT);
            ll.addView(b);
            mainLayout.addView(ll);
        }
        mainLayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.gradient, null));
        scrollView.addView(mainLayout);
        scrollView.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.gradient, null));
        setContentView(scrollView);
    }

    private void setSavedFamiyUser(String familyUserName)
    {
        // SharedPreferences sharedPreferences =getSharedPreferences("sharedPreferencs",MODE_PRIVATE);
        SharedPreferences sharedPreferences=getSharedPreferences("sharedPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("familyUser",familyUserName);
        editor.apply();
    }

}

