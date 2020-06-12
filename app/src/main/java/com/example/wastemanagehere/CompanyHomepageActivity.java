package com.example.wastemanagehere;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.here.sdk.core.GeoCoordinates;
import com.here.sdk.core.Point2D;
import com.here.sdk.mapview.MapError;
import com.here.sdk.mapview.MapImage;
import com.here.sdk.mapview.MapImageFactory;
import com.here.sdk.mapview.MapMarker;
import com.here.sdk.mapview.MapScene;
import com.here.sdk.mapview.MapScheme;
import com.here.sdk.mapview.MapView;

import org.json.JSONArray;
import org.json.JSONException;

public class CompanyHomepageActivity extends AppCompatActivity {
    private Button companynotification,btngeneratereport;
    MapView mapView;
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_homepage);
        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapScene().loadScene(MapScheme.NORMAL_DAY, new MapScene.LoadSceneCallback() {
            @Override
            public void onLoadScene(@Nullable MapError mapError) {
                if (mapError == null) {
                    double distanceInMeters = 1000 * 20;
                    mapView.getCamera().lookAt(
                            new GeoCoordinates(19.19642, 73.09318), distanceInMeters);
                    MapImage mapImage = MapImageFactory.fromResource(getResources(), R.drawable.mapiconfinal);
                   // mapView.getMapScene().addMapMarker(new MapMarker(new GeoCoordinates(19.19642, 73.09318), mapImage));
                    SharedPreferences preferences = getSharedPreferences("Customer_data", MODE_PRIVATE);
                    try {
                        JSONArray list = new JSONArray(preferences.getString("CustomerList", ""));
                        for (int i = 0; i < list.length(); i++) {
                            mapView.getMapScene().addMapMarker(new MapMarker(new GeoCoordinates(Double.parseDouble(list.getJSONObject(i).getString("Lat")), Double.parseDouble(list.getJSONObject(i).getString("Long"))), mapImage));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Log.e(TAG, "Loading map failed: mapError: " + mapError.name());
                }
            }
        });

//        mapView.getMapScene().addMapMarker(new MapMarker(new GeoCoordinates(19.86876,72.97686),mapImage));
        companynotification = findViewById(R.id.companynotification);
        btngeneratereport = findViewById(R.id.btngeneratereport);
        companynotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCompanyNotificationActivity();
            }
        });
        btngeneratereport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CompanyHomepageActivity.this,GenerateReportActivity.class);
                startActivity(intent);
            }
        });


    }

    private void openCompanyNotificationActivity() {
        Intent intent = new Intent(this, CompanyNotificationActivity.class);
        startActivity(intent);
    }

    private GeoCoordinates createRandomGeoCoordinatesAroundMapCenter(Location[] location) {
        GeoCoordinates centerGeoCoordinates = mapView.viewToGeoCoordinates(
                new Point2D(mapView.getWidth() / 2, mapView.getHeight() / 2));
        if (centerGeoCoordinates == null) {
            // Should never happen for center coordinates.
            throw new RuntimeException("CenterGeoCoordinates are null");
        }
//        double lat = location.getLatitude();
//        double lon = location.getLongitude();
//        return new GeoCoordinates(lat,lon);
        return null;
    }
}

