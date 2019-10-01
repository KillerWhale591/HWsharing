package com.zeyufu.w4_p4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class HomeActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private int MIN_FLING_VELO = 200;
    private int MIN_FLING_DIST = 100;

    private GestureDetector GD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GD = new GestureDetector(this, this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.GD.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) { }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e, MotionEvent e1, float v, float v1) {
        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) { }

    @Override
    public boolean onFling(MotionEvent e, MotionEvent e1, float v, float v1) {
        if (Math.abs(v) > MIN_FLING_VELO || Math.abs(v1) > MIN_FLING_VELO) {
            if (e.getX() - e1.getX() > MIN_FLING_DIST) { // fling left
                Intent i = new Intent(this, WestActivity.class);
                startActivity(i);
            } else if (e1.getX() - e.getX() > MIN_FLING_DIST) { // fling right
                Intent i = new Intent(this, EastActivity.class);
                startActivity(i);
            } else if (e.getY() - e1.getY() > MIN_FLING_DIST) { // fling up
                Intent i = new Intent(this, NorthActivity.class);
                startActivity(i);
            } else if (e1.getY() - e.getY() > MIN_FLING_DIST) { // fling down
                Intent i = new Intent(this, SouthActivity.class);
                startActivity(i);
            }
        }
        return false;
    }
}
