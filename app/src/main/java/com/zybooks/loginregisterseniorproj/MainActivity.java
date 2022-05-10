package com.zybooks.loginregisterseniorproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
//Tino & Eddie
public class MainActivity extends AppCompatActivity {
    //Tino
    Button register, login; //set up to use later
    TextView username, password;
    EditText entry1, entry4;

    //Eddie
    private SQLTableManager _SQLTableManager;
    private List<String> userNames;
    private List<String> passwords;
    private boolean isValidLogin;
    private static boolean isActivityActive;
    private Intent loginSuccessIntent;


    private static final String SHARED_PREFS="sharedPrefs";
    private static final String TEXT="text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Login");
        //Tino
        Intent recString= getIntent();

        entry1 = (EditText) findViewById(R.id.entry1); //make them findable by id
        entry4 = (EditText) findViewById(R.id.entry4);
        username = (TextView) findViewById(R.id.username);
        password = (TextView) findViewById(R.id.password);
        register = (Button) findViewById(R.id.Register);
        login = (Button) findViewById(R.id.Login);
        //Eddie
        userNames=new ArrayList<>();
        passwords=new ArrayList<>();
        
        isValidLogin=false;

        CheckIfLoggedIn();

        CreateOrAddUserTable();
    }
    //Eddie
    private void CheckIfLoggedIn() {
        if(!GetCurrentUserName().equals(""))
        {
            Intent n = new Intent(this,UserMainMenu.class);
            startActivity(n);
        }
    }

    //Eddie
    private void CreateOrAddUserTable() {
        _SQLTableManager =new SQLTableManager(this);
    }
    /*On Start and On Stop will check to make sure User logins don't try to run after activity
    is shut down */
    //Eddie
    @Override
    protected void onStart() {
        super.onStart();
        isActivityActive=true;
    }
    //Eddie
    @Override
    protected void onStop() {
        super.onStop();
        isActivityActive=false;
    }
    //Eddie
    public void LoginToApp(View v) { //button to login, checks if username and password matches registration
        CheckIfValidUserNameAndPassword();
        if(!isValidLogin && isActivityActive)
        {
            Toast.makeText(this, "error couldn't login", Toast.LENGTH_SHORT).show();
        }
    }
    //Eddie
    private void CheckIfValidUserNameAndPassword() {
        //retrieves the data from the database
        Cursor accountUserTableData= _SQLTableManager.GetTableData("Account_User");
        if(accountUserTableData.getCount()==0)
        {
            Toast.makeText(this, "error, database is empty", Toast.LENGTH_SHORT).show();
        }
        else
        {
            try {
                //reads all data from the database and stores emails & passwords
                while(accountUserTableData.moveToNext()) {
                    int passwordIndex=6;
                    int emailIndex =4;
                    userNames.add(accountUserTableData.getString(emailIndex));
                    passwords.add(accountUserTableData.getString(passwordIndex));
                }
            }
            catch(Exception e)
            {
                Toast.makeText(this, "error, couldn't show user data", Toast.LENGTH_SHORT).show();
            }
        }
        //nested foreach loop used to check if the same username and password from the same database row matches
        //**** email is being used as the userName ****
        for (String _username: userNames)
        {
            for (String _password:passwords)
            {
                if (username.getText().toString().equals(_username) && (password.getText().toString().equals(_password)))
                {
                    Toast.makeText(MainActivity.this, "Successful login", Toast.LENGTH_SHORT).show();
                    isValidLogin=true;
                    SaveUserName(username.getText().toString());
                    ChangeActivityAfterLogin(_username);
                }
                else
                {
                 isValidLogin=false;   
                }
            }
        }
    }
    //Eddie
    private void ChangeActivityAfterLogin(String userName) {
       // InsertAllTablesGarbageValues();
        loginSuccessIntent= new Intent(this, UserMainMenu.class);
        startActivity(loginSuccessIntent);
        finish();
    }
    //Eddie Debug Function
    //**Uncomment Line 133 to work**
    private void InsertAllTablesGarbageValues()
    {
        SQLTableManager SQLTableManager = new SQLTableManager(this);
        boolean accountUserSuccess=SQLTableManager.InsertAccountUser("Admin","User","xx/xx/xxxx","admin@savesmart.com",1234567890,"adminPassword");

        boolean cryptoWalletSuccess=SQLTableManager.InsertCryptoWallet("Admin","AAA",1,100,"today");
        boolean stockWalletSuccess=SQLTableManager.InsertStockWallet("Admin","BTC",1,100,"today");

        boolean userIncomeSuccess=SQLTableManager.InsertUserIncome("Admin","nothing yet",500,"today");
        boolean userExpenseSuccess =SQLTableManager.InsertUserExpense("Admin","nothing yet",500,"today");

        boolean familyUserSuccess=SQLTableManager.InsertFamilyUser("Admin","COOLGUY","John","Doe","today","Son");

        boolean familyUserIncomeSuccess=SQLTableManager.InsertFamilyUserIncome("COOLGUY","nothing yet",500,"today");
        boolean familyUserExpenseSuccess=SQLTableManager.InsertFamilyUserExpense("COOLGUY","nothing yet",500,"today");

        if(cryptoWalletSuccess&&stockWalletSuccess&&userIncomeSuccess&& userExpenseSuccess &&familyUserSuccess&&familyUserIncomeSuccess&&familyUserExpenseSuccess)
        {
            //if they all insert successfully do nothing
        }
        else
        {
            Toast.makeText(this,"one of the table inserts were messed up",Toast.LENGTH_SHORT);
        }
    }

    private void SaveUserName(String userName) {
       // SharedPreferences sharedPreferences =getSharedPreferences("sharedPreferencs",MODE_PRIVATE);
        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(TEXT,userName);
        editor.apply();
    }
    
     public String GetCurrentUserName()
    {
        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        String currentUserName=sharedPreferences.getString(TEXT,"");
        return currentUserName;
    }
    //Tino
    public void Register(View v) { //switch to registration activity
        Intent n = new Intent(this, Register.class);
        startActivity(n);
    }

    //Eddie
    //for debugging purposes
//    public void GetUserInfo(View view) {
//        Cursor tableData = _accountUserTable.GetTableData();
//        if (tableData.getCount() == 0) {
//            Toast.makeText(this, "nothing in database", Toast.LENGTH_SHORT).show();
//        } else {
//            try {
//
//                while (tableData.moveToNext()) {
//                    int passwordIndex = 6;
//                    int emailIndex = 4;
//                    userNames.add(tableData.getString(emailIndex));
//                    passwords.add(tableData.getString(passwordIndex));
//                }
//
//                Toast.makeText(this, userNames.get(1) +" "+ passwords.get(1), Toast.LENGTH_SHORT).show();
//            } catch (Exception e) {
//                Toast.makeText(this, "couldn't show user data", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    public void LoginToApp2(View view) {
        Intent n = new Intent(this, UserMainMenu.class);
        startActivity(n);
    }
}