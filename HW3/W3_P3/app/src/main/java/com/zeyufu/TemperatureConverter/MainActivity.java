package com.zeyufu.TemperatureConverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText edtCelsius;
    private EditText edtFahrenheit;
    private SeekBar skbCelsius;
    private SeekBar skbFahrenheit;
    private TextView txtHint;

    private final TextWatcher txtWatcherCelsius = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            edtFahrenheit.removeTextChangedListener(txtWatcherFahrenheit);
            skbCelsius.setOnSeekBarChangeListener(null);
            skbFahrenheit.setOnSeekBarChangeListener(null);
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            try {
                double celsius = Double.parseDouble(editable.toString());
                double fahrenheit = celsius * 9 / 5 + 32;
                edtFahrenheit.setText(String.valueOf((int) fahrenheit));
                skbCelsius.setProgress((int) celsius);
                skbFahrenheit.setProgress((int) fahrenheit - 32);
                showMessage((int) celsius);
            } catch (Exception e) {
                e.printStackTrace();
                edtFahrenheit.setText("");
            }
            edtFahrenheit.addTextChangedListener(txtWatcherFahrenheit);
            skbCelsius.setOnSeekBarChangeListener(skbListenerCelsius);
            skbFahrenheit.setOnSeekBarChangeListener(skbListenerFahrenheit);
        }
    };

    private final TextWatcher txtWatcherFahrenheit = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            edtCelsius.removeTextChangedListener(txtWatcherCelsius);
            skbCelsius.setOnSeekBarChangeListener(null);
            skbFahrenheit.setOnSeekBarChangeListener(null);
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            try {
                double fahrenheit = Double.parseDouble(editable.toString());
                double celsius = (fahrenheit - 32) / 9 * 5;
                edtCelsius.setText(String.valueOf((int) celsius));
                skbCelsius.setProgress((int) celsius);
                skbFahrenheit.setProgress((int) fahrenheit - 32);
                showMessage((int) celsius);
            } catch (Exception e) {
                e.printStackTrace();
                edtCelsius.setText("");
            }
            edtCelsius.addTextChangedListener(txtWatcherCelsius);
            skbCelsius.setOnSeekBarChangeListener(skbListenerCelsius);
            skbFahrenheit.setOnSeekBarChangeListener(skbListenerFahrenheit);
        }
    };

    private final SeekBar.OnSeekBarChangeListener skbListenerCelsius = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            int fahrenheit = (int) ((double) i * 9 / 5);
            skbFahrenheit.setProgress(fahrenheit);
            edtCelsius.setText(String.valueOf(i));
            edtFahrenheit.setText(String.valueOf(fahrenheit + 32));
            showMessage(i);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            edtCelsius.removeTextChangedListener(txtWatcherCelsius);
            edtFahrenheit.removeTextChangedListener(txtWatcherFahrenheit);
            skbFahrenheit.setOnSeekBarChangeListener(null);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            edtCelsius.addTextChangedListener(txtWatcherCelsius);
            edtFahrenheit.addTextChangedListener(txtWatcherFahrenheit);
            skbFahrenheit.setOnSeekBarChangeListener(skbListenerFahrenheit);
        }
    };

    private final SeekBar.OnSeekBarChangeListener skbListenerFahrenheit = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            int celsius = (int) ((double) i / 9 * 5);
            skbCelsius.setProgress(celsius);
            edtCelsius.setText(String.valueOf(celsius));
            edtFahrenheit.setText(String.valueOf(i + 32));
            showMessage(celsius);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            edtCelsius.removeTextChangedListener(txtWatcherCelsius);
            edtFahrenheit.removeTextChangedListener(txtWatcherFahrenheit);
            skbCelsius.setOnSeekBarChangeListener(null);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            edtCelsius.addTextChangedListener(txtWatcherCelsius);
            edtFahrenheit.addTextChangedListener(txtWatcherFahrenheit);
            skbCelsius.setOnSeekBarChangeListener(skbListenerCelsius);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI init
        edtCelsius = findViewById(R.id.edtCelsius);
        edtFahrenheit = findViewById(R.id.edtFahrenheit);
        skbCelsius = findViewById(R.id.skbCelsius);
        skbFahrenheit = findViewById(R.id.skbFahrenheit);
        txtHint = findViewById(R.id.txtHint);

        // SeekBar setup
        skbCelsius.setMax(100);
        skbFahrenheit.setMax(180);
        skbCelsius.setOnSeekBarChangeListener(skbListenerCelsius);
        skbFahrenheit.setOnSeekBarChangeListener(skbListenerFahrenheit);

        // EditText setup
        edtCelsius.addTextChangedListener(txtWatcherCelsius);
        edtFahrenheit.addTextChangedListener(txtWatcherFahrenheit);
    }

    private void showMessage(int celsius) {
        if (celsius < 12) {
            txtHint.setText(getResources().getString(R.string.hint_cold));
        } else if (celsius > 30) {
            txtHint.setText(getResources().getString(R.string.hint_hot));
        } else {
            txtHint.setText(getResources().getString(R.string.hint_nice));
        }
    }
}
