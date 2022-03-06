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
    EditText entry1, entry2, entry3, entry4;
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
    }

    public void Complete(View v) { //before moving back to login, makes sure all fields are valid in some way
        if (entry1.getText().toString().isEmpty() || //check if fields are empty then tells you to fill them in if they are
                entry2.getText().toString().isEmpty() ||
                entry3.getText().toString().isEmpty() ||
                entry4.getText().toString().isEmpty()) {
            Toast.makeText(Register.this, "Fill in all fields",
                    Toast.LENGTH_LONG).show();
        }
        else if(entry1.length() < 3 || entry1.length() > 30 || //makes sure first name and last name are within 3 to 30 chars
                entry2.length() < 3 || entry2.length() > 30)
        {
            Toast.makeText(Register.this, "First name and last name must be between 3 and 30 characters",
                    Toast.LENGTH_LONG).show();
        }
        else if(!entry3.getText().toString().trim().matches(emailValidate)) { //uses the emailValidate sequence to confirm correct email format
            Toast.makeText(Register.this, "Invalid email address",
                    Toast.LENGTH_LONG).show();
        }
        else if(entry4.length() < 7) //makes sure password isn't too short
        {
            Toast.makeText(Register.this, "Good passwords should be at least 8 characters or longer",
                    Toast.LENGTH_LONG).show();
        }
        else{ //if everything passes the checks, go back to login
            Toast.makeText(Register.this, "Registration Successful. You can now login",
                    Toast.LENGTH_LONG).show();
            Intent n = new Intent(this, com.zybooks.loginregisterseniorproj.MainActivity.class);
            SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("username", entry1.getText().toString());
            editor.putString("password", entry4.getText().toString());
            editor.apply();

            startActivity(n);
        }
    }
}