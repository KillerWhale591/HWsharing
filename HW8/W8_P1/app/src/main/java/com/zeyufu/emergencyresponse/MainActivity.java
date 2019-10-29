package com.zeyufu.emergencyresponse;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    private static final String PREFIX_SMS = "**THIS IS ONLY A TEST** My GPS Location is ";
    private static final String TOAST_TEXT_SENT = "Emergency text sent!";
    private static final String TOAST_TEXT_FAIL = "Failed sending text!";
    private static final int INTERVAL_LOC_UPDATE = 1000;
    private static final int THRESHOLD_SHAKE = 500;
    private static final int INDEX_ACC_X = 0;
    private static final int INDEX_ACC_Y = 1;
    private static final int INDEX_ACC_Z = 2;

    private FusedLocationProviderClient FLPC;
    private LocationRequest locationRequest;
    private SensorManager sensorMgr;
    private ImageButton btnPhone;
    private ImageButton btnText;
    private TextView txtPhone;
    private TextView txtText;

    private String phoneNum;
    private String textNum;
    private float acceleration;
    private float currentAcceleration;
    private float lastAcceleration;
    private boolean emergencySent = false;

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            // Detect shaking
            float x = sensorEvent.values[INDEX_ACC_X];
            float y = sensorEvent.values[INDEX_ACC_Y];
            float z = sensorEvent.values[INDEX_ACC_Z];
            lastAcceleration = currentAcceleration;
            currentAcceleration = x * x + y * y + z * z;
            float diff = currentAcceleration - lastAcceleration;
            acceleration = Math.abs(acceleration + diff);
            if (acceleration > THRESHOLD_SHAKE) {
                if (!emergencySent) {
                    emergencySent = true;
                    textContact();
                    dialContact();
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set accelerate sensor
        sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorMgr.registerListener(sensorEventListener,
                sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        acceleration = 0f;
        currentAcceleration = SensorManager.GRAVITY_EARTH;
        lastAcceleration = SensorManager.GRAVITY_EARTH;

        // Set location services
        FLPC = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(INTERVAL_LOC_UPDATE);

        // Set UI widgets
        btnPhone = findViewById(R.id.btnPhone);
        btnText = findViewById(R.id.btnText);
        txtPhone = findViewById(R.id.txtPhone);
        txtText = findViewById(R.id.txtText);
        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialContact();
            }
        });
        btnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textContact();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefInfo = getSharedPreferences(PreferencesActivity.SP_NAME, Context.MODE_PRIVATE);
        phoneNum = prefInfo.getString(PreferencesActivity.KEY_PHONE_PREF, getResources().getString(R.string.default_contact));
        textNum = prefInfo.getString(PreferencesActivity.KEY_TEXT_PREF, getResources().getString(R.string.default_contact));
        txtPhone.setText(phoneNum);
        txtText.setText(textNum);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.preferences_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mnuPreference) {
            // Go to preferences activity
            Intent i = new Intent(getApplicationContext(), PreferencesActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Dial the phone contact
     */
    private void dialContact() {
        Intent dial = new Intent(Intent.ACTION_DIAL);
        dial.setData(Uri.parse("tel:" + phoneNum));
        startActivity(dial);
    }

    /**
     * Send text message to the text contact
     */
    private void textContact() {
        // Update location
        FLPC.requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
            }
        }, null);
        // Get location and send message
        FLPC.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    try {
                        String msg = getMessageContent(location.getLatitude(), location.getLongitude());
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(textNum, null, msg, null, null);
                        Toast.makeText(MainActivity.this, TOAST_TEXT_SENT, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, TOAST_TEXT_FAIL, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    /**
     * Function to arrange a text message
     * @param latitude the latitude of current location
     * @param longitude the longitude of current location
     * @return message containing location info
     */
    private String getMessageContent(double latitude, double longitude) {
        String msg = PREFIX_SMS;
        msg += String.valueOf(latitude);
        msg += ", ";
        msg += String.valueOf(longitude);
        msg += ".";
        return msg;
    }
}
