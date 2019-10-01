package com.zeyufu.flingingmoney;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private final double EURO_TO_DOLLAR = 1.09;
    private final double EURO_TO_YEN = 118.12;
    private final double EURO_TO_RUPEE = 77.30;
    private final double EURO_TO_RMB = 7.79;

    private final int MIN_FLING_VELO = 500;
    private final int MAX_SCROLL_VELO = 50;

    private final double ONE_DOLLAR = 1;
    private final double FIVE_CENTS = 0.05;

    private EditText edtEuro;
    private TextView txtDollar;
    private TextView txtYen;
    private TextView txtRupee;
    private TextView txtRMB;

    private double euro;
    private DecimalFormat dec = new DecimalFormat("#0.00");
    private GestureDetector GD;
    private TextWatcher tw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            try {
                euro = Double.parseDouble(editable.toString());
                setMoney(euro);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEuro = findViewById(R.id.edtEuro);
        txtDollar = findViewById(R.id.txtDollar);
        txtYen = findViewById(R.id.txtYen);
        txtRupee = findViewById(R.id.txtRupee);
        txtRMB = findViewById(R.id.txtRMB);

        GD = new GestureDetector(this, this);
        edtEuro.addTextChangedListener(tw);
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
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e, MotionEvent e1, float v, float v1) {
        if (Math.abs(v1) < MAX_SCROLL_VELO) {
            if (v1 > 0) { // Scroll up
                changeEuro(FIVE_CENTS);
            } else { // Scroll down
                changeEuro(-FIVE_CENTS);
            }
        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent e, MotionEvent e1, float v, float v1) {
        if (Math.abs(v1) > MIN_FLING_VELO) {
            if (e.getY() > e1.getY()) { // Fling up
                changeEuro(ONE_DOLLAR);
            } else if (e1.getY() > e.getY()) { // Fling down
                changeEuro(-ONE_DOLLAR);
            }
        }
        return true;
    }

    private void setMoney(double euro) {
        txtDollar.setText(dec.format(euro * EURO_TO_DOLLAR));
        txtYen.setText(dec.format(euro * EURO_TO_YEN));
        txtRupee.setText(dec.format(euro * EURO_TO_RUPEE));
        txtRMB.setText(dec.format(euro * EURO_TO_RMB));
    }

    private void changeEuro(double value) {
        euro = 0;
        try {
            euro = Double.parseDouble(edtEuro.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        euro += value;
        if (euro < 0) {
            euro = 0;
        }
        edtEuro.setText(dec.format(euro));
        setMoney(euro);
    }
}
