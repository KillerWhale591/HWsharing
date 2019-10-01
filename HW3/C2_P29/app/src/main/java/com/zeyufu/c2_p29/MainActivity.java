package com.zeyufu.c2_p29;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText edtBurger;
    private EditText edtTaco;
    private EditText edtNachos;
    private EditText edtCoke;
    private TextView txtCalories;

    private final int CALO_BURGER = 200;
    private final int CALO_TACO = 150;
    private final int CALO_NACHOS = 250;
    private final int CALO_COKE = 0;

    private int calories = 0;

    private TextWatcher tw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

        @Override
        public void afterTextChanged(Editable editable) {
            calories = getCalories();
            txtCalories.setText(String.valueOf(calories));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtBurger = findViewById(R.id.edtBurger);
        edtTaco = findViewById(R.id.edtTaco);
        edtNachos = findViewById(R.id.edtNachos);
        edtCoke = findViewById(R.id.edtCoke);
        txtCalories = findViewById(R.id.txtCalories);

        txtCalories.setText(String.valueOf(calories));
        edtBurger.addTextChangedListener(tw);
        edtTaco.addTextChangedListener(tw);
        edtNachos.addTextChangedListener(tw);
        edtCoke.addTextChangedListener(tw);
    }

    private int getCalories() {
        int numBurger = 0,
                numTaco = 0,
                numNachos = 0,
                numCoke = 0;
        try {
            numBurger = Integer.parseInt(edtBurger.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        try {
            numTaco = Integer.parseInt(edtTaco.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        try {
            numNachos = Integer.parseInt(edtNachos.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        try {
            numCoke = Integer.parseInt(edtCoke.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        calories = numBurger * CALO_BURGER + numTaco * CALO_TACO
                + numNachos * CALO_NACHOS + numCoke * CALO_COKE;

        return calories;
    }
}
