package com.example.dbdemo.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.dbdemo.MainActivity;
import com.example.dbdemo.model.Contact;
import com.example.dbdemo.params.Params;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class MyDbHandler extends SQLiteOpenHelper {



    public MyDbHandler(Context context) {
        super(context, Params.DB_NAME, null, Params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create="CREATE TABLE "+Params.DB_TABLE_NAME+"("+Params.KEY_ID+" INTEGER PRIMARY KEY,"
                       +Params.KEY_NAME+" TEXT,"+Params.KEY_PHONE+" TEXT"+")";

        //Toast.makeText(MainActivity.this,create, Toast.LENGTH_SHORT).show();
        Log.d("dbp","Query being run is: "+create);
        db.execSQL(create);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addContact(Contact contact)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(Params.KEY_NAME, contact.getName());
        values.put(Params.KEY_PHONE, contact.getPhoneNumber());

        db.insert(Params.DB_TABLE_NAME, null, values);
        Log.d("dbp","Successfully inserted.");
        db.close();
    }

    public List<Contact> getAllContacts()
    {
        List<Contact> contactList=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();

        //Generate the query to read the contacts from database
        String select="SELECT * FROM "+Params.DB_TABLE_NAME;
        Cursor cursor=db.rawQuery(select,null);

        //Loop through now
        if(cursor.moveToFirst())
        {
            do {
                Contact contact=new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                contactList.add(contact);
            }while (cursor.moveToNext());
        }
        return contactList;
    }

    public int updateContact(Contact contact)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Params.KEY_NAME,contact.getName());
        values.put(Params.KEY_PHONE,contact.getPhoneNumber());

        //Lets update
        return db.update(Params.DB_TABLE_NAME,values,Params.KEY_ID,new String[]{String.valueOf(contact.getId())});
    }
}
