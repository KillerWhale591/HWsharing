package com.example.w8_p1_emergency_response;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;



public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private final static int CODE_1 = 1;
    private final static String TAG = "Tag";
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    String message;
    String phoneNum;
    String TextNum;
    ImageButton btnPhone;
    ImageButton btnText;
    String latitude;
    String longtitude;

    SensorManager sensorManager;
    float mAcc;
    float mAccCur;
    float mAccLast;
    private FusedLocationProviderClient FLPC;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPhone = (ImageButton) findViewById(R.id.imgBPhone);
        btnText = (ImageButton) findViewById(R.id.imgBText);
        SharedPreferences simpleAppInfo = getSharedPreferences("ActivityOneInfo", Context.MODE_PRIVATE);
        String phone = simpleAppInfo.getString("txtPhone","");
        String text = simpleAppInfo.getString("txtText","");
        FLPC = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(2 * 1000);


        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        latitude = String.valueOf(location.getLatitude());
                        longtitude = String.valueOf(location.getLatitude());
                    }
                }
            }
        };
        phoneNum = phone;
        TextNum = text;
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        mAcc = 0.00f;
        mAccCur = SensorManager.GRAVITY_EARTH;
        mAccLast = SensorManager.GRAVITY_EARTH;
        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call();
            }
        });
        btnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text();
            }
        });


    }
    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);   //get rid of default behavior.

        // Inflate the menu; this adds items to the action bar
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mnu_Preference) {
            Intent i = new Intent(getBaseContext(),PreferencesActivity.class);
            startActivityForResult(i,CODE_1);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODE_1){
            if(resultCode == RESULT_OK){
                TextNum = data.getStringExtra("txtText");
                phoneNum = data.getStringExtra("txtPhone");
            }
        }
    }
    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            Log.d(TAG, "getLocation: failed");
        return;
        }
        FLPC.requestLocationUpdates(locationRequest, locationCallback, null);
        FLPC.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                Log.i(TAG, "onSuccess: location" + location);
                if(location != null){
                    latitude = String.valueOf(location.getLatitude());
                    Log.i(TAG, "onSuccess: " + latitude);
                    longtitude = String.valueOf(location.getLongitude());
                    Log.i(TAG, "onSuccess: " + longtitude);
                    message = "**THIS IS ONLY A TEST** My GPS Location is";
                    if (latitude != null && longtitude != null) {
                        message = message + " LAT: " + latitude + " Long: " + longtitude;
                    }
                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage(TextNum, null, message, null, null);
                    Log.i(TAG, "text: " + latitude + " long: " + longtitude);
                    Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];
        mAccLast = mAccCur;
        mAccCur = (x * x + y * y + z * z);
        float diff = mAccCur - mAccLast;
        mAcc = Math.abs(mAcc * 0.9f + diff);
//        Log.d(TAG, "onSensorChanged: " + mAcc);
        if(mAcc > 800){
            text();
            call();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    @Override
    protected void onDestroy() {
        sensorManager.unregisterListener(this);
        super.onDestroy();
    }
    private void call(){
        try {
            if (!phoneNum.equals("")) {
                Intent phoneCall = new Intent(Intent.ACTION_DIAL);
                phoneCall.setData(Uri.parse("tel:" + phoneNum));
                Log.d(TAG, "onClick: " + phoneNum);
                startActivity(phoneCall);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void text(){
        try {
            getLocation();

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "SMS failed.", Toast.LENGTH_SHORT).show();
        }
    }

}
