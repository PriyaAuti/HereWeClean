package com.example.wastemanagehere;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class GenerateWasteActivity extends AppCompatActivity {
    private Button btngenerate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_waste);
        btngenerate=(Button) findViewById(R.id.btngenerate);
        btngenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityAddWaste();
            }
        });
    }
    public void openActivityAddWaste(){
        Intent intent=new Intent(this,AddWasteActivity.class);
        startActivity(intent);
    }

}


