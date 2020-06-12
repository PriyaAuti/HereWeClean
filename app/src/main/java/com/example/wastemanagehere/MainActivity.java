package com.example.wastemanagehere;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button customerlogin,companylogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customerlogin=(Button) findViewById(R.id.customerlogin);
        companylogin=(Button) findViewById(R.id.companylogin);
        customerlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityCustomerLogin();
            }
        });

        companylogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityCompanyLogin();
            }
        });
    }

    public void openActivityCustomerLogin() {
        Intent intent=new Intent(this,CustomerLogin2Activity.class);
        startActivity(intent);
    }
    private void openActivityCompanyLogin() {
        Intent i=new Intent(this,CompanyLoginActivity.class);
        startActivity(i);
    }
}
