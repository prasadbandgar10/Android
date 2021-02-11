package com.example.sampletranslator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedInputStream;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    EditText editText;
    Button translatebutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText=findViewById(R.id.editText);
        textView=findViewById(R.id.textView);
        translatebutton=findViewById(R.id.button);

        Spinner spinner=findViewById(R.id.spinner);
        Spinner myspinner=findViewById(R.id.spinner2);

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(MainActivity.this,
                 android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.names));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        myspinner.setAdapter(arrayAdapter);
        

    }
}