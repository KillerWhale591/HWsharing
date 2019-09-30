package com.zeyufu.c7_p28;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {

    private FlingableTextView txtHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtHello = findViewById(R.id.txtHello);

        // Set window size
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        txtHello.setWindowSize(width, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        txtHello.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
