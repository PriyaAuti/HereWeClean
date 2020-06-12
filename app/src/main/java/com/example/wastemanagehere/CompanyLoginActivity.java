package com.example.wastemanagehere;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CompanyLoginActivity extends AppCompatActivity {
    private Button btncompanylogin;
    EditText etcompanyname,etcompanypassword;
    String correct_etcompanyname="Greenobin";
    String correct_etcompanypassword="123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_login);
        btncompanylogin=findViewById(R.id.btncompanylogin);
        etcompanyname=findViewById(R.id.etcompanyname);
        etcompanypassword=findViewById(R.id.etcompanypassword);
        btncompanylogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validate inputs
                if(TextUtils.isEmpty(etcompanyname.getText().toString()) || TextUtils.isEmpty(etcompanypassword.getText().toString()))
                {
                    Toast.makeText(CompanyLoginActivity.this, "Empty data provided", Toast.LENGTH_SHORT).show();
                }
                else if(etcompanyname.getText().toString().equals(correct_etcompanyname)){
                    //check password
                    if(etcompanypassword.getText().toString().equals(correct_etcompanypassword)){
                        openCompanyHomePage();

                    }

                }else{
                    Toast.makeText(CompanyLoginActivity.this, "Invalid Customer name/Password", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void openCompanyHomePage() {
        //Intent intent=new Intent(this,CompanyHomepageActivity.class);
        Intent intent=new Intent(this,CompanyHomepageActivity.class);
        startActivity(intent);
    }
}

