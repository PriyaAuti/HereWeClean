package com.example.wastemanagehere;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class CompanyCovidDataActivity extends AppCompatActivity {
    TextView txtcovidpole,txtcovidpoletwo;
    int position;
    JSONObject object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_covid_data);
        txtcovidpole=findViewById(R.id.txtcovidpole);
        txtcovidpoletwo=findViewById(R.id.txtcovidpoletwo);

        String data = getIntent().getStringExtra("object");
//        position = getIntent().getIntExtra("position",0);
        try {
            object = new JSONObject(data.trim());
            txtcovidpole.setText(object.getString("isCovidPatient"));
            txtcovidpoletwo.setText(object.getString("isPatientInFamily"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}