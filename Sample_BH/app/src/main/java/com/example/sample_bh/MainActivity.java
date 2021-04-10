package com.example.sample_bh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void about(View view){
        Toast.makeText(this,"About Us",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this, AboutUs.class);
        startActivity(intent);
    }

    public void gallery(View view){
        Toast.makeText(this,"Gallery",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this,Gallery.class);
        startActivity(intent);
    }

    public void contact_us(View view){
        Toast.makeText(this,"Contact Us",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this,Contact_Us.class);
        startActivity(intent);
    }

    public void courses(View view){
        Toast.makeText(this,"Courses",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this,Courses.class);
        startActivity(intent);
    }


}