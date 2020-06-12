package com.example.wastemanagehere;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CustomerCartActivity extends AppCompatActivity implements LocationListener {

    private String TAG = getClass().getSimpleName();
    EditText etlocation;
    Button submit;
    Location location;
    LocationManager locationManager;
    private String current_locality = "";
    private String currentLocation = "";
    SharedPreferences pref;
    TextView tvwasteType;
    RecyclerView customer_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = getSharedPreferences("Customer_data", MODE_PRIVATE);
        setContentView(R.layout.activity_customer_cart);
        Log.e(TAG, "onCreate: " + pref.getString("Cart", ""));
        tvwasteType = findViewById(R.id.tvwastetype);
        tvwasteType.setText(pref.getString("type", "N/A"));
        etlocation = findViewById(R.id.etlocation);
        submit = findViewById(R.id.btncordersubmitted);
        customer_category = findViewById(R.id.customer_category);
        getCurrentAddress();
        customer_category.setLayoutManager(new LinearLayoutManager(this));
        customer_category.setAdapter(new MyAdapter());
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CustomerCartActivity.this,"Successfully submitted your waste collection request",Toast.LENGTH_SHORT).show();

                JSONArray Customers = new JSONArray();
                JSONObject Customer = new JSONObject();
                try {
                    Customer.put("customer_name", pref.getString("customer_name", ""));
                    Customer.put("type", pref.getString("type", ""));
                    Customer.put("zone", pref.getString("zone", ""));
                    Customer.put("location", pref.getString("location", ""));
                    Customer.put("isCovidPatient", pref.getString("isCovidPatient", ""));
                    Customer.put("isPatientInFamily", pref.getString("isPatientInFamily", ""));
                    Customer.put("Lat", pref.getString("Lat", ""));
                    Customer.put("Long", pref.getString("Long", ""));
//                    JSONArray Cartlist = new JSONArray(pref.getString("Cart", "").replace("=", "\":\"").replace("{","\"{\"").replace("}","\"}\""));
                    JSONArray Cartlist = new JSONArray();
                    Cartlist.put(new JSONObject(pref.getString("Cart", "").replace("=", "\":\"").replace("{","{\"").replace("}","\"}").replace(",","\",\"")));
//                    Cartlist.put(new JSONObject(pref.getString("Cart", "").replace("=", "\":\"")));
                    Customer.put("Cart", Cartlist);
                    Customers.put(Customer);
                    SharedPreferences.Editor editor = pref.edit();
                    if (pref.getString("CustomerList", "").equals("")) {
                        editor.putString("CustomerList", Customers.toString());
                        editor.commit();
                    } else {
                        JSONArray oldList= new JSONArray(pref.getString("CustomerList",""));
                        oldList.put(Customer);
                        editor.putString("CustomerList", oldList.toString());
                        editor.commit();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                openActivityMain();

            }

        });
    }

    private void openActivityMain() {
        Intent intent=new Intent(CustomerCartActivity.this,MainActivity.class);
        startActivity(intent);
    }

    private void getCurrentAddress() {
        // Get the location manager
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

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
                Toast.makeText(CustomerCartActivity.this, "test exception", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onLocationChanged(Location location) {

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

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.CustomViewHolder> {
        Map<String, String> map;
        String[] keyValuePairs;

        public MyAdapter() {
            String value = pref.getString("Cart", "");
            value = value.substring(1, value.length() - 1);           //remove curly brackets
            keyValuePairs = value.split(",");              //split the string to creat key-value pairs
            map = new HashMap<>();

            for (String pair : keyValuePairs)                        //iterate over the pairs
            {
                String[] entry = pair.split("=");                   //split the pairs to get key and value
                map.put(entry[0].trim(), entry[1].trim());          //add them to the hashmap and trim whitespaces
            }
        }

        @NonNull
        @Override
        public MyAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.custom_row, parent, false);
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.CustomViewHolder holder, int position) {
            String keyvalue = keyValuePairs[position];
            String[] arr = keyvalue.split("=");
            holder.name.setText(arr[0]);
//           switch (arr[0]){
//               case "Paper":
//           }
            holder.imageView.setImageResource(Integer.parseInt(arr[1]));

        }

        @Override
        public int getItemCount() {
            return map.size();
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {
            TextView name;
            ImageView imageView;

            public CustomViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.row_name);
                imageView = itemView.findViewById(R.id.row_image);

            }
        }
    }
}

