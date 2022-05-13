package com.zybooks.loginregisterseniorproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

//Eddie
public class AddFamilyMember extends AppCompatActivity {
    EditText familyUserId, familyUserFirstName, familyUserLastName, familyUserDOB, familyUserRelationToOwner,familyUserPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_family_member);

        familyUserId=findViewById(R.id.FamilyMemberUserNameEditText);
        familyUserFirstName=findViewById(R.id.FamilyMemberFirstNameEditText);
        familyUserLastName=findViewById(R.id.FamilyMemberLastNameEditText);
        familyUserDOB=findViewById(R.id.FamilyMemberDOBEditText);
        familyUserRelationToOwner=findViewById(R.id.FamilyMemberRelationShipEditText);
        familyUserPassword=findViewById(R.id.FamilyPasswordText);
    }


    public void AddMemberOnClick(View view) {
        SQLTableManager SQLTableManager = new SQLTableManager(this);
        boolean familyUserSuccess=SQLTableManager.InsertFamilyUser(GetCurrentUserName(),familyUserId.getText().toString(), familyUserFirstName.getText().toString(),
                familyUserLastName.getText().toString(),familyUserDOB.getText().toString(),familyUserRelationToOwner.getText().toString(),familyUserPassword.getText().toString());

        if(familyUserSuccess)
        {
            setSavedFamiyUser(familyUserId.getText().toString());
            Intent n = new Intent(this, FamilyMemberMainMenu.class);
            startActivity(n);
        }
        else
        {
            Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG);
        }
    }


    public String GetCurrentUserName()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("sharedPrefs",MODE_PRIVATE);
        String currentUserName=sharedPreferences.getString("text","");
        return currentUserName;
    }


    private void setSavedFamiyUser(String familyUserName)
    {
        SharedPreferences sharedPreferences=getSharedPreferences("sharedPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("familyUser",familyUserName);
        editor.apply();
    }

}