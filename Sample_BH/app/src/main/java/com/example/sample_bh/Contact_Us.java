package com.example.sample_bh;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class Contact_Us extends AppCompatActivity {

    private EditText Name, Email, Age, Address, Phonenumber, Course, Interest;
    private Button Submit;
    DBHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact__us);
        //Intent intent = getIntent();

        Name=findViewById(R.id.pname);
        Email=findViewById(R.id.pemail);
        Age=findViewById(R.id.page);
        Address=findViewById(R.id.paddress);
        Phonenumber=findViewById(R.id.pphone);
        Course=findViewById(R.id.pcousre);
        Submit=findViewById(R.id.sbutton);
        Interest=findViewById(R.id.pinterest);

        DB=new DBHelper(this);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p_name=Name.getText().toString();
                String p_email=Email.getText().toString();
                int p_age=Integer.parseInt(Age.getText().toString());
                String p_address=Address.getText().toString();
                int p_phone=Integer.parseInt(Phonenumber.getText().toString());
                String p_course=Course.getText().toString();
                String p_interest=Interest.getText().toString();

                Boolean checkinsertdata= DB.insertData(p_name,p_email,p_age,p_address,p_phone,p_course,p_interest);
                if(checkinsertdata==true)
                    Toast.makeText(Contact_Us.this,"Response recorded.", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Contact_Us.this,"Response not recorded.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


