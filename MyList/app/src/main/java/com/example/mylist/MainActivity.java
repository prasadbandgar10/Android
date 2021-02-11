package com.example.mylist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView myListView=findViewById(R.id.myListView);
        ArrayList<String> grocery=new ArrayList<>();
        grocery.add("Bhindi");
        grocery.add("Palak");
        grocery.add("Paneer");
        grocery.add("Butter");
        grocery.add("Banana");
        grocery.add("Apples");
        grocery.add("Kachodi");
        grocery.add("Pani Puri");
        grocery.add("Bhel");
        grocery.add("Curd Milk");

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1,grocery);
        myListView.setAdapter(arrayAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text="item" + (position+1) + " " +((TextView)view).getText().toString();
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });

    }
}