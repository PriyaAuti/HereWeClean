package com.example.wastemanagehere;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Iterator;

public class CompanyOnSelectActivity extends AppCompatActivity {
    TextView tvduedate, name, location, waste_type, category,txtczone,tvcovidhighlight;
    Button btncollectwaste;
    DatePickerDialog.OnDateSetListener setListener;
    JSONObject object;
    String data;
    int position;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_on_select);
        name = findViewById(R.id.name);
        location = findViewById(R.id.location);
        waste_type = findViewById(R.id.waste_type);
        category = findViewById(R.id.category);
        txtczone=findViewById(R.id.txtczone);
        tvcovidhighlight=findViewById(R.id.tvcovidhighlight);

         data = getIntent().getStringExtra("object");
         position = getIntent().getIntExtra("position",0);
        try {
            object = new JSONObject(data.trim());
            name.setText(object.getString("customer_name"));
            location.setText(object.getString("location"));
            waste_type.setText(object.getString("type"));
            txtczone.setText(object.getString("zone"));
            JSONArray array = object.getJSONArray("Cart");
            String keys = "";
            for (int i = 0; i < array.length(); i++) {
                Iterator<String> itkeys = array.getJSONObject(i).keys();
                for (int j = 0; j < array.getJSONObject(i).length(); j++) {
                    keys += " " + itkeys.next();
                }
            }
            category.setText(keys);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tvduedate = findViewById(R.id.tvduedate);
        btncollectwaste = findViewById(R.id.btncollectwaste);


        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        tvduedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        CompanyOnSelectActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                tvduedate.setText(date);
            }
        };

        btncollectwaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CompanyOnSelectActivity.this, "Successfully collected waste", Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = getSharedPreferences("Customer_data", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                try {
                    JSONArray newlist = new JSONArray(preferences.getString("CustomerList", ""));
                    newlist.remove(position);
                    editor.putString("CustomerList",newlist.toString());
                    editor.commit();

                } catch (JSONException e) {
                    e.printStackTrace();


                }
                openActivityLogin();
            }
        });
        tvcovidhighlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityCompanyCovidData();
            }
        });
    }

    private void openActivityCompanyCovidData() {
        Intent i=new Intent(this,CompanyCovidDataActivity.class).putExtra("object",data);
        startActivity(i);
    }

    private void openActivityLogin() {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
