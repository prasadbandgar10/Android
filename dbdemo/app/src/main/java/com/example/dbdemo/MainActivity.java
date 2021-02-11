package com.example.dbdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dbdemo.data.MyDbHandler;
import com.example.dbdemo.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         textView=findViewById(R.id.text);

        MyDbHandler db=new MyDbHandler(MainActivity.this);
        //textView.setText(db.toString());

        //Creating a contact object for db
        Contact pb=new Contact();
        pb.setName("Prasad");
        pb.setPhoneNumber("9090754812");

        //adding contact to db
        db.addContact(pb);

        //Creating a contact object for db
        Contact ab=new Contact();
        ab.setName("ABCD");
        ab.setPhoneNumber("9090125812");

        //adding contact to db
        db.addContact(ab);

        //Creating a contact object for db
        Contact cb=new Contact();
        cb.setName("XYZ");
        cb.setPhoneNumber("9078954812");

        //adding contact to db
        db.addContact(cb);
        Log.d("dbp","ID for pb added is "+pb.getId()+" Id for ab added is "+ab.getId()+" Id for cb added is "+cb.getId());

        cb.setId(13);
        cb.setName("Pooooo");
        cb.setPhoneNumber("0000000000");
        int affectedRows=db.updateContact(cb);
        Log.d("dbp","No of affected row are: "+affectedRows);


        List<Contact> allContacts=db.getAllContacts();
        for(Contact contact: allContacts)
        {
            Log.d("dbp","\nId:"+contact.getId()+"\nName:"+contact.getName()+"\nPhone Number:"+contact.getPhoneNumber()+"\n");
            //textView.setText("\nId:"+contact.getId()+"\nName:"+contact.getName()+"\nPhone Number:"+contact.getPhoneNumber()+"\n");
        }


    }
}