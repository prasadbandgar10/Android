package com.example.sample_bh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class Courses extends AppCompatActivity {

    private Button button, button2;
    ListView resultsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        Intent intent=getIntent();

        button = (Button) findViewById(R.id.submit);
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });

    }
    public void openActivity2() {
        Intent intent1 = new Intent(this, Activity2.class);
        startActivity(intent1);

    }

    public void openActivity3() {
        Intent intent3 = new Intent(this, Activity3.class);
        startActivity(intent3);
    }

}
