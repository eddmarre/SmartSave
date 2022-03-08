package com.zybooks.loginregisterseniorproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    Button Complete;
    EditText entry1, entry2, entry3, entry4,entry5,entry6;
    String emailValidate = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.+[a-zA-Z]+"; //using regular expression (or regex),
    // the first part should be any alphanumerical character(s), dot, underscore or dash, either capitalized or not, followed by an @ which
    // is followed by any alphanumerical character(s), dot, underscore or dash, which is followed by a period or dot and that is followed
    // by alphanumerical characters. This sequence wants a format of xxxxx@xxxx.xxx and it gets checked by
    // the if statement later and if it doesn't follow this sequence, it will tell you to fill in a valid address

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Complete = (Button) findViewById(R.id.Complete);

        entry1 = (EditText) findViewById(R.id.entry1);
        entry2 = (EditText) findViewById(R.id.entry2);
        entry3 = (EditText) findViewById(R.id.entry3);
        entry4 = (EditText) findViewById(R.id.entry4);
        entry5 = (EditText) findViewById(R.id.entry5);
        entry6 = (EditText) findViewById(R.id.entry6);
    }

    public void Complete(View v) { //before moving back to login, makes sure all fields are valid in some way
        ChekUserInputInformation();
    }

    private void ChekUserInputInformation() {
        //check if fields are empty then tells you to fill them in if they are
        if (entry1.getText().toString().isEmpty() || entry2.getText().toString().isEmpty() ||
                entry3.getText().toString().isEmpty() || entry4.getText().toString().isEmpty() ||
                entry5.getText().toString().isEmpty() || entry6.getText().toString().isEmpty())
        {
            GiveUserDialoguge("Fill in all fields");
        }
        //makes sure first name and last name are within 3 to 30 chars
        else if(entry1.length() < 3 || entry1.length() > 30 || entry2.length() < 3 || entry2.length() > 30)
        {
            GiveUserDialoguge("First name and last name must be between 3 and 30 characters");
        }
        else if(!entry3.getText().toString().trim().matches(emailValidate)) { //uses the emailValidate sequence to confirm correct email format
            GiveUserDialoguge("Invalid email address");
        }
        else if(entry4.length() < 7) //makes sure password isn't too short
        {
            GiveUserDialoguge("Good passwords should be at least 8 characters or longer");
        }
        else if(entry5.length()!=10)
        {
            GiveUserDialoguge("Phone number invalid");
        }
        else if(entry6.length()!=10)
        {
            GiveUserDialoguge("Invalid DOB");
        }
        else{ //if everything passes the checks, go back to login
            RegisterUserInDatabase();
            ChangeScene();
        }
    }

    private void GiveUserDialoguge(String message) {
        Toast.makeText(Register.this, message,Toast.LENGTH_LONG).show();
    }

    private void ChangeScene() {
        Intent n = new Intent(this, MainActivity.class);
        startActivity(n);
    }

    private void RegisterUserInDatabase()
    {
        AccountUserTable accountUserTable = new AccountUserTable(this);

        String phoneNumberString= entry5.getText().toString();
        int phoneNumber=Integer.parseInt(phoneNumberString);

        boolean registerNewUser=accountUserTable.InsertAccountUser(entry1.getText().toString(),entry2.getText().toString(),
                entry6.getText().toString(), entry3.getText().toString(),phoneNumber,entry4.getText().toString());

        if(registerNewUser)
        {
           GiveUserDialoguge("successfully registered new user in database");
        }
        else
        {
            GiveUserDialoguge("error registering user, email already has existing account");
        }
    }
}