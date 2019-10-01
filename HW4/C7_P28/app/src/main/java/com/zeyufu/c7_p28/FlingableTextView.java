package com.zeyufu.c7_p28;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import java.util.Random;

public class FlingableTextView extends android.support.v7.widget.AppCompatTextView implements GestureDetector.OnGestureListener {

    private final int MAX_VELOCITY = 400;
    private final String TOAST_NOT_FAST = "Fling faster!";
    private final String TOAST_FAST = "That is fast!";

    private int windowWidth;
    private int windowHeight;
    private float offsetX;
    private float offsetY;
    private GestureDetector gd;

    public FlingableTextView(Context context) {
        super(context);
        gd = new GestureDetector(this);
    }

    public FlingableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        gd = new GestureDetector(this);
    }

    public FlingableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        gd = new GestureDetector(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        offsetX = (float) this.getWidth() / 2;
        offsetY = (float) (getStatusBarHeight() + this.getHeight() / 2);
        this.setX(event.getRawX() - offsetX);
        this.setY(event.getRawY() - offsetY);
        this.gd.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        if (v > MAX_VELOCITY || v1 > MAX_VELOCITY) {
            this.setX(new Random().nextInt(windowWidth));
            this.setY(new Random().nextInt(windowHeight));
            Toast.makeText(getContext(), TOAST_FAST, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), TOAST_NOT_FAST, Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private int getStatusBarHeight() {
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    public void setWindowSize(int width, int height) {
        windowWidth = width;
        windowHeight = height;
    }
}
