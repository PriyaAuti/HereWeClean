package com.example.wastemanagehere;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CustomerLogin2Activity extends AppCompatActivity {
    private Button btncustomerlogin;
    EditText etcustomername,etpassword;
    String correct_etcustomername="Priya";
    String correct_etpassword="123";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login2);
        btncustomerlogin=(Button) findViewById(R.id.btncustomerlogin);
        etcustomername=(EditText) findViewById(R.id.etcustomername);
        etpassword=(EditText) findViewById(R.id.etpassword);
        btncustomerlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validate inputs
                if(TextUtils.isEmpty(etcustomername.getText().toString()) || TextUtils.isEmpty(etpassword.getText().toString()))
                {
                    Toast.makeText(CustomerLogin2Activity.this, "Empty data provided", Toast.LENGTH_SHORT).show();
                }
                else if(etcustomername.getText().toString().equals(correct_etcustomername)){
                    //check password
                    if(etpassword.getText().toString().equals(correct_etpassword)){
                        openActivityCustomerCovidInfo();

                    }

                }else{
                    Toast.makeText(CustomerLogin2Activity.this, "Invalid Customer name/Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void openActivityCustomerCovidInfo(){
        Intent intent=new Intent(this,CustomerCovidInfoActivity.class);
        SharedPreferences sharedpreferences = getSharedPreferences("Customer_data", Context.MODE_PRIVATE);
       SharedPreferences.Editor pref= sharedpreferences.edit();
       pref.putString("customer_name",correct_etcustomername);
       pref.commit();
        startActivity(intent);
    }
}


