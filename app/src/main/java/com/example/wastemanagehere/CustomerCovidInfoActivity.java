package com.example.wastemanagehere;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.text.TextUtils;
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
import java.util.List;
import java.util.Locale;


public class CustomerCovidInfoActivity extends AppCompatActivity implements LocationListener {
    EditText etcustomername, etlocation;
    String customername;
    LocationManager locationManager;
    Location location;
    TextView tvhighlight;
    private Button btndone;
    private Spinner zonespinner, patientspinner, familypatientspinner;
    private String current_locality = "";
    private String currentLocation = "";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_covid_info);
        sharedPreferences = getSharedPreferences("Customer_data", MODE_PRIVATE);
        customername = sharedPreferences.getString("customer_name", "N/A");
        Log.e("TAG", "Test");
        //  Log.e("TAG",sharedPreferences.getString("customer_name","N/A"));
        tvhighlight=findViewById(R.id.tvhighlight);
        EditText etcustomername = findViewById(R.id.etcustomername);
        etlocation = findViewById(R.id.etlocation);
        etcustomername.setText(customername);
        getCurrentAddress();
        Spinner zonespinner = findViewById(R.id.zonespinner);
        List<String> zonelist = new ArrayList<>();
        zonelist.add(0, "Select");
        zonelist.add("Green");
        zonelist.add("Contentment");
        zonelist.add("Red");

        //Style and populate the spinner
        ArrayAdapter<String> zoneadapter;
        zoneadapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, zonelist);
        //Dropdown layout style
        zoneadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //attaching zoneadapter to spinner
        zonespinner.setAdapter(zoneadapter);
        zonespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Select")) {
                    //nothing to do
                } else {
                    //on selecting spinner item
                    String item = parent.getItemAtPosition(position).toString();
                    //show selected spinner item
                    Toast.makeText(parent.getContext(), "selected: " + item, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Spinner patientspinner = findViewById(R.id.patientspinner);
        List<String> patient = new ArrayList<>();
        patient.add(0, "Select");
        patient.add("Yes");
        patient.add("No");

        //Style and populate the spinner
        ArrayAdapter<String> patientadapter;
        patientadapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, patient);
        //Dropdown layout style
        patientadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //attaching patientadapter to spinner
        patientspinner.setAdapter(patientadapter);
        patientspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Select")) {
                    //nothing to do
                } else {
                    //on selecting spinner item
                    String item = parent.getItemAtPosition(position).toString();
                    //show selected spinner item
                    Toast.makeText(parent.getContext(), "selected: " + item, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner familypatientspinner = findViewById(R.id.familypatientspinner);
        List<String> familypatient = new ArrayList<>();
        familypatient.add(0, "Select");
        familypatient.add("Yes");
        familypatient.add("No");

        //Style and populate the spinner
        ArrayAdapter<String> familypatientadapter;
        familypatientadapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, familypatient);
        //Dropdown layout style
        familypatientadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //attaching familypatientadapter to spinner
        familypatientspinner.setAdapter(familypatientadapter);
        familypatientspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Select")) {
                    //nothing to do
                } else {
                    //on selecting spinner item
                    String item = parent.getItemAtPosition(position).toString();
                    //show selected spinner item
                    Toast.makeText(parent.getContext(), "selected: " + item, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btndone = (Button) findViewById(R.id.btndone);
        btndone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("location", etlocation.getText().toString());
                editor.putString("zone", zonespinner.getSelectedItem().toString());
                editor.putString("isCovidPatient", patientspinner.getSelectedItem().toString());
                editor.putString("isPatientInFamily", familypatientspinner.getSelectedItem().toString());
                editor.commit();
                openActivityGenerateWaste();
            }
        });


        tvhighlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(CustomerCovidInfoActivity.this,CovidHighlightActivity.class);
                startActivity(in);
            }
        });

    }

    public void openActivityGenerateWaste() {
        Intent intent = new Intent(this, GenerateWasteActivity.class);
        startActivity(intent);
    }

    public void getCurrentAddress() {
        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (locationManager != null) {

            try {

                if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        5000,
                        1, this);
            } catch (Exception ex) {
                Log.i("msg", "fail to request location update, ignore", ex);
            }
            if (locationManager != null) {
                location = locationManager
                        .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Lat", String.valueOf(location.getLatitude()));
            editor.putString("Long", String.valueOf(location.getLongitude()));
            editor.commit();
            Geocoder gcd = new Geocoder(getBaseContext(),
                    Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(location.getLatitude(),
                        location.getLongitude(), 1);
                if (addresses.size() > 0) {
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String locality = addresses.get(0).getLocality();
                    String subLocality = addresses.get(0).getSubLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName();
                    etlocation.setText(locality);
                    if (subLocality != null) {

                        currentLocation = locality + "," + subLocality;
                    } else {

                        currentLocation = locality;
                    }
                    current_locality = locality;
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("TAG", "getCurrentAddress: " + e.getMessage());
                Toast.makeText(CustomerCovidInfoActivity.this, "test exception", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        getCurrentAddress();
        Log.e("TAG", location.getLatitude() + " " + location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
