package com.example.sample_bh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME="Contact.db";
    public DBHelper(Context context) {
        super(context, "Contact.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table students(name TEXT, email TEXT, age integer, address TEXT, phone integer, course TEXT, interest TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists students");
    }

    public Boolean insertData(String name, String email, Integer age, String address, Integer phone, String course, String interest){
       SQLiteDatabase  MyDB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("age", age);
        contentValues.put("address", address);
        contentValues.put("phone", phone);
        contentValues.put("course", course);
        contentValues.put("interest",interest);
        long result=MyDB.insert("students", null, contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }


}
