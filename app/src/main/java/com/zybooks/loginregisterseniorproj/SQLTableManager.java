package com.zybooks.loginregisterseniorproj;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//Eddie
public class SQLTableManager extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="AccountUser.db";

    public static final String Account_User_TABLE_NAME ="Account_User";
    public static final String Account_User_COL_1 ="ID";
    public static final String Account_User_COL_2 ="FIRST_NAME";
    public static final String Account_User_COL_3 ="LAST_NAME";
    public static final String Account_User_COL_4 ="DOB";
    public static final String Account_User_COL_5 ="EMAIL";
    public static final String Account_User_COL_6 ="PHONE";
    public static final String Account_User_COL_7 ="PASSWORD";
    public static final String Account_User_Col_8 ="ADMIN";

    public static final String EXPENSE_TABLE_NAME ="Account_User_Expense";
    public static final String Expense_Col_1 ="USERID";
    public static final String Expense_Col_2 ="DESCRIPTION";
    public static final String Expense_Col_3 ="LOST_REVENUE";
    public static final String Expense_Col_4 ="DATE";

    public static final String INCOME_TABLE_NAME ="Account_User_Income";
    public static final String Income_Col_1 ="USERID";
    public static final String Income_Col_2 ="DESCRIPTION";
    public static final String Income_Col_3 ="GAINED_REVENUE";
    public static final String Income_Col_4 ="DATE";

    public static final String Stock_Wallet_TABLE_NAME ="Account_User_Stock_Wallet";
    public static final String Stock_Wallet_Col_1 ="UserID";
    public static final String Stock_Wallet_Col_2 ="Company_Symbol";
    public static final String Stock_Wallet_Col_3 ="Amount_Owned";
    public static final String Stock_Wallet_Col_4 ="Current_Price";
    public static final String Stock_Wallet_Col_5 ="Purchase_Date";

    public static final String Crypto_Wallet_TABLE_NAME ="Account_User_Crypto_Wallet";
    public static final String Crypto_Wallet_Col_1 ="UserID";
    public static final String Crypto_Wallet_Col_2 ="Crypto_Symbol";
    public static final String Crypto_Wallet_Col_3 ="Amount_Owned";
    public static final String Crypto_Wallet_Col_4 ="Current_Price";
    public static final String Crypto_Wallet_Col_5 ="Purchase_Date";

    public static final String Family_User_Table_Name="Account_User_Family_User";
    public static final String Family_User_Col_1="Account_Owner_ID";
    public static final String Family_User_Col_2="UserID";
    public static final String Family_User_Col_3="First_Name";
    public static final String Family_User_Col_4="Last_Name";
    public static final String Family_User_Col_5="DOB";
    public static final String Family_User_Col_6="Relation_To_Owner";

    public static final String Family_User_EXPENSE_TABLE_NAME ="Account_User_Family_User_Expense";
    public static final String Family_User_Expense_Col_1 ="USERID";
    public static final String Family_User_Expense_Col_2 ="DESCRIPTION";
    public static final String Family_User_Expense_Col_3 ="LOST_REVENUE";
    public static final String Family_User_Expense_Col_4 ="DATE";

    public static final String Family_User_INCOME_TABLE_NAME ="Account_User_Family_User_Income";
    public static final String Family_User_Income_Col_1 ="USERID";
    public static final String Family_User_Income_Col_2 ="DESCRIPTION";
    public static final String Family_User_Income_Col_3 ="GAINED_REVENUE";
    public static final String Family_User_Income_Col_4 ="DATE";


    public SQLTableManager(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        AccountUser(db);

        StockWallet(db);
        CryptoWallet(db);

        Expense(db);
        Income(db);

        FamilyUser(db);
        FamilyIncome(db);
        FamilyExpense(db);
    }
    private void AccountUser(SQLiteDatabase db) {
        db.execSQL("create table "+ Account_User_TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,FIRST_NAME TEXT,LAST_NAME TEXT,DOB TEXT, EMAIL TEXT UNIQUE,PHONE INT,PASSWORD TEXT,ADMIN INTEGER)");
    }

    private void StockWallet(SQLiteDatabase db)
    {
        db.execSQL("create table "+ Stock_Wallet_TABLE_NAME +" ("+ Stock_Wallet_Col_1 +" text, "
                + Stock_Wallet_Col_2 +" text unique, "
                + Stock_Wallet_Col_3 +" real, "
                + Stock_Wallet_Col_4 +" real, "
                + Stock_Wallet_Col_5 +" text, "
                +"FOREIGN KEY ("+ Stock_Wallet_Col_1 +") REFERENCES "+ Account_User_TABLE_NAME +"(EMAIL))");
    }

    private void CryptoWallet(SQLiteDatabase db)
    {
        db.execSQL("create table "+ Crypto_Wallet_TABLE_NAME +" ("+ Crypto_Wallet_Col_1 +" text, "
                + Crypto_Wallet_Col_2 +" text unique, "
                + Crypto_Wallet_Col_3 +" real, "
                + Crypto_Wallet_Col_4 +" real, "
                + Crypto_Wallet_Col_5 +" text, "
                +"FOREIGN KEY ("+ Crypto_Wallet_Col_1 +") REFERENCES "+ Account_User_TABLE_NAME +"(EMAIL))");
    }

    private void Expense(SQLiteDatabase db)
    {
        db.execSQL("create table "+ EXPENSE_TABLE_NAME +" ("+ Expense_Col_1 +" text, "
                + Expense_Col_2 +" text, "
                + Expense_Col_3 +" real, "
                + Expense_Col_4 +" text, "
                +"FOREIGN KEY ("+ Expense_Col_1 +") REFERENCES "+ Account_User_TABLE_NAME +"(EMAIL))");
    }

    private void Income(SQLiteDatabase db)
    {
        db.execSQL("create table "+ INCOME_TABLE_NAME +" ("+ Income_Col_1 +" text, "
                + Income_Col_2 +" text, "
                + Income_Col_3 +" real, "
                + Income_Col_4 +" text, "
                +"FOREIGN KEY ("+ Income_Col_1 +") REFERENCES "+ Account_User_TABLE_NAME +"(EMAIL))");
    }

    private void FamilyUser(SQLiteDatabase db)
    {
        db.execSQL("create table "+ Family_User_Table_Name +" ("+ Family_User_Col_1+" text, "
                + Family_User_Col_2 +" text primary key, "
                + Family_User_Col_3 +" text, "
                + Family_User_Col_4 +" text, "
                + Family_User_Col_5 +" text, "
                + Family_User_Col_6 +" text, "
                +"FOREIGN KEY ("+ Family_User_Col_1 +") REFERENCES "+ Account_User_TABLE_NAME +"(EMAIL))");
    }
    private void FamilyIncome(SQLiteDatabase db)
    {
        db.execSQL("create table "+ Family_User_INCOME_TABLE_NAME +" ("+ Family_User_Income_Col_1 +" text, "
                + Family_User_Income_Col_2 +" text, "
                + Family_User_Income_Col_3 +" real, "
                + Family_User_Income_Col_4 +" text, "
                +"FOREIGN KEY ("+ Family_User_Income_Col_1 +") REFERENCES "+ Family_User_Table_Name +"("+Family_User_Col_2+"))");
    }
    private void FamilyExpense(SQLiteDatabase db)
    {
        db.execSQL("create table "+ Family_User_EXPENSE_TABLE_NAME +" ("+ Family_User_Expense_Col_1 +" text, "
                + Family_User_Expense_Col_2 +" text, "
                + Family_User_Expense_Col_3 +" real, "
                + Family_User_Expense_Col_4 +" text, "
                +"FOREIGN KEY ("+ Family_User_Expense_Col_1 +") REFERENCES "+ Family_User_Table_Name +"("+Family_User_Col_2+"))");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Account_User_TABLE_NAME);
        onCreate(db);
    }
    //returns false if failed
    public boolean InsertAccountUser(String firstName,String lastName, String dob, String email,int phone,String password)
    {

        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Account_User_COL_2,firstName);
        contentValues.put(Account_User_COL_3,lastName);
        contentValues.put(Account_User_COL_4,dob);
        contentValues.put(Account_User_COL_5,email);
        contentValues.put(Account_User_COL_6,phone);
        contentValues.put(Account_User_COL_7,password);
        //Defaults no admin privelidges
        contentValues.put(Account_User_Col_8,0);
        //checks to see if actual data is inserted
        long result=db.insert(Account_User_TABLE_NAME,null,contentValues);
        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    //*WARNING* should work with a bool using values 0 and 1 might cause errors in the future be aware *WARNING*
    public boolean InsertAccountAdmin(String firstName,String lastName, String dob, String email,int phone,String password,Boolean admin)
    {

        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Account_User_COL_2,firstName);
        contentValues.put(Account_User_COL_3,lastName);
        contentValues.put(Account_User_COL_4,dob);
        contentValues.put(Account_User_COL_5,email);
        contentValues.put(Account_User_COL_6,phone);
        contentValues.put(Account_User_COL_7,password);
        //Defaults no admin privelidges
        contentValues.put(Account_User_Col_8,admin);
        //checks to see if actual data is inserted
        long result=db.insert(Account_User_TABLE_NAME,null,contentValues);
        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public Cursor GetTableData(String tableName)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+ tableName,null);
        return res;
    }
}
