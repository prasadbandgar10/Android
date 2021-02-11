package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void send_now(View view)
    {
        Toast.makeText(this, "Sending message fom cell", Toast.LENGTH_SHORT).show();
    }

    public void receive_now(View view)
    {
        Toast.makeText(this, "Receiving message fom cell", Toast.LENGTH_SHORT).show();
    }

    public void delete_now(View view)
    {
        Toast.makeText(this, "Deleting message fom cell", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}