package com.zybooks.loginregisterseniorproj;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AccountUserTable extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="AccountUser.db";
    public static final String TABLE_NAME="Account_User";
    public static final String COL_1="USERID";
    public static final String COL_2="FIRSTNAME";
    public static final String COL_3="LASTNAME";
    public static final String COL_4="DOB";
    public static final String COL_5="EMAIL";
    public static final String COL_6="PHONE";
    public static final String COL_7="PASSWORD";

    public AccountUserTable(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,FIRSTNAME TEXT,LASTNAME TEXT,DOB TEXT, EMAIL TEXT UNIQUE,PHONE INT,PASSWORD TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    //returns false if failed
    public boolean InsertAccountUser(String firstName,String lastName, String dob, String email,int phone,String password)
    {

        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,firstName);
        contentValues.put(COL_3,lastName);
        contentValues.put(COL_4,dob);
        contentValues.put(COL_5,email);
        contentValues.put(COL_6,phone);
        contentValues.put(COL_7,password);
        //checks to see if actual data is inserted
        long result=db.insert(TABLE_NAME,null,contentValues);
        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }


    public Cursor GetTableData()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return res;
    }
}
