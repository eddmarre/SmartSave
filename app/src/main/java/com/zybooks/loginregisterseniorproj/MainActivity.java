package com.zybooks.loginregisterseniorproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button Register, Login; //set up to use later
    TextView username, password;
    EditText entry1, entry4;
    String string, string2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent recString= getIntent();


        entry1 = (EditText) findViewById(R.id.entry1); //make them findable by id
        entry4 = (EditText) findViewById(R.id.entry4);
        username = (TextView) findViewById(R.id.username);
        password = (TextView) findViewById(R.id.password);
        Register = (Button) findViewById(R.id.Register);
        Login = (Button) findViewById(R.id.Login);



    }

    public void LoginToApp(View v) { //button to login, checks if username and password matches registration
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        String string = sharedPref.getString("username", "");
        String string2 = sharedPref.getString("password", "");
        if (username.getText().toString().equals(string) && (password.getText().toString().equals(string2)))
        {
            Toast.makeText(MainActivity.this, "Successful login", //displays on success
                    Toast.LENGTH_LONG).show();
            Intent n = new Intent(this, Register.class);
            startActivity(n);
        }
        else{
            Toast.makeText(MainActivity.this, "No account name/password found with those details, try again",
                    Toast.LENGTH_LONG).show(); //displays if failed and doesn't match registration
        }
    }

    public void Register(View v) { //switch to registration activity
        Intent n = new Intent(this, Register.class);
        startActivity(n);
    }
}