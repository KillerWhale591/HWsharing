package com.zeyufu.imagerating;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements OnImageChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void changeImageIndex(int increment) {
        RatingFragment fragment = (RatingFragment) getSupportFragmentManager().findFragmentById(R.id.rating);
        fragment.setAlbum(increment);
    }
}
