package com.zeyufu.dimmer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements OnTranspanrencyChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void passTransparency(float alpha) {
        LightFragment lightFragment = (LightFragment) getSupportFragmentManager().findFragmentById(R.id.lightFragment);
        assert lightFragment != null;
        lightFragment.setTransparency(alpha);
    }
}
