package com.zeyufu.trackdistance;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 222;
    private static final long INTERVAL_LOC_REQUEST = 1000;

    private FusedLocationProviderClient FLPC;
    private LocationRequest locationRequest;
    private Button btnGetLoc;
    private TextView txtLast;
    private TextView txtCurr;
    private TextView txtDist;

    private Location lastPos;
    private Location currPos;
    private boolean firstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set location services
        FLPC = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(INTERVAL_LOC_REQUEST);

        // Set UI widgets
        btnGetLoc = findViewById(R.id.btnGetLoc);
        txtLast = findViewById(R.id.txtLast);
        txtCurr = findViewById(R.id.txtCurr);
        txtDist = findViewById(R.id.txtDist);
        btnGetLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_FINE_LOCATION);
                }
                FLPC.requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                    }
                }, null);
                FLPC.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            updateLocAndDist(location);
                        }
                    }
                });
            }
        });
    }

    /**
     * Updates the text views with new location
     * @param location new location to update
     */
    private void updateLocAndDist(Location location) {
        if (firstTime) {
            firstTime = false;
            lastPos = location;
        } else {
            lastPos = currPos;
        }
        currPos = location;
        txtLast.setText(getStringLocation(lastPos));
        txtCurr.setText(getStringLocation(currPos));
        txtDist.setText(String.valueOf(currPos.distanceTo(lastPos)));
    }

    /**
     * Convert a location to a string
     * @param location location
     * @return Converted location string
     */
    private String getStringLocation(Location location) {
        return location.getLatitude() + ", " + location.getLongitude();
    }
}
