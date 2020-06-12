package com.example.wastemanagehere;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddWasteActivity extends AppCompatActivity {
    private Spinner wastespinner;
    private Button btncart, btnpaper, btnglass, btnorganic, btnplastic, btnewaste, btnmetals;
    TextView tvwastetype;
    private String TAG = getClass().getSimpleName();
    SharedPreferences preferences;
    SharedPreferences.Editor pref;
    Map map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_waste);
        map = new HashMap();
        preferences = getSharedPreferences("Customer_data", MODE_PRIVATE);
        pref = preferences.edit();
        Button btncart = findViewById(R.id.btncart);
        Button btnpaper = findViewById(R.id.btnpaper);
        Button btnglass = findViewById(R.id.btnglass);
        Button btnorganic = findViewById(R.id.btnorganic);
        Button btnplastic = findViewById(R.id.btnplastic);
        Button btnewaste = findViewById(R.id.btnewaste);
        Button btnmetals = findViewById(R.id.btnmetals);

        Spinner wastespinner = findViewById(R.id.wastespinner);
        List<String> waste = new ArrayList<>();
        waste.add(0, "Select");
        waste.add("Munciple waste");
        waste.add("Industrial waste");
        waste.add("Bio-chemical waste");
        waste.add("Other waste");

        //Style and populate the spinner
        ArrayAdapter<String> wasteadapter;
        wasteadapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, waste);
        //Dropdown layout style
        wasteadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //attaching patientadapter to spinner
        wastespinner.setAdapter(wasteadapter);
        wastespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Select")) {
                    //nothing to do
                } else {
                    //on selecting spinner item
                    String item = parent.getItemAtPosition(position).toString();
                    //show selected spinner item
                    Toast.makeText(parent.getContext(), "selected: " + item, Toast.LENGTH_SHORT).show();
                    pref.putString("type", wastespinner.getSelectedItem().toString());
                    pref.commit();
                    if (parent.getItemAtPosition(position).equals("Munciple waste")) {

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityCustomerCart();
            }
        });

        btnpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CartModel model = new CartModel("Paper",R.mipmap.ic_paper);
                map.put("Paper", R.mipmap.ic_paper);
                Log.e(TAG, "onClick: " + map.toString());

                pref.putString("Cart", map.toString());
                pref.commit();
                btnpaper.setText("Added");
                btnpaper.setEnabled(false);
            }
        });

        btnglass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.put("Glass", R.mipmap.ic_glass);
                Log.e(TAG, "onClick: " + map.toString());

                pref.putString("Cart", map.toString());
                pref.commit();
                btnglass.setText("Added");
                btnglass.setEnabled(false);


            }
        });

        btnorganic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.put("Organic", R.mipmap.ic_organic);
                Log.e(TAG, "onClick: " + map.toString());

                pref.putString("Cart", map.toString());
                pref.commit();
                btnorganic.setText("Added");
                btnorganic.setEnabled(false);


            }
        });

        btnplastic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.put("Plastic", R.mipmap.ic_plastic);
                Log.e(TAG, "onClick: " + map.toString());

                pref.putString("Cart", map.toString());
                pref.commit();
                btnplastic.setText("Added");
                btnplastic.setEnabled(false);


            }
        });

        btnewaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.put("Ewaste", R.mipmap.ic_ewaste);
                Log.e(TAG, "onClick: " + map.toString());

                pref.putString("Cart", map.toString());
                pref.commit();
                btnewaste.setText("Added");
                btnewaste.setEnabled(false);


            }
        });

        btnmetals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.put("Metal", R.mipmap.ic_metals);
                Log.e(TAG, "onClick: " + map.toString());

                pref.putString("Cart", map.toString());
                pref.commit();
                btnmetals.setText("Added");
                btnmetals.setEnabled(false);


            }
        });

    }

    private void openActivityCustomerCart() {
        Intent intent = new Intent(this, CustomerCartActivity.class);
        startActivity(intent);
    }

}

