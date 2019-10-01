package com.zeyufu.flashcard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final int NUM_PROBLEMS = 10;
    private final int INDEX_RESULT = 0;
    private final int INDEX_DIVISOR = 1;
    private final int INDEX_DIVIDEND = 2;

    private final String STR_EXTRA_NAME = "username";
    private final String SYMBOL_DIVIDE = "÷";
    private final String TOAST_WELCOME = "Welcome, ";
    private final String TOAST_SCORE = " out of 10";
    private final String TOAST_INVALID_ANS = "Answer must be a number.";

    private final String KEY_PROBLEM = "problem";
    private final String KEY_INDEX = "index";
    private final String KEY_SCORE = "score";
    private final String KEY_WELCOME = "welcome";

    // Widgets
    private TextView txtDividend;
    private TextView txtDivisor;
    private EditText edtAnswer;
    private Button btnGenerate;
    private Button btnSubmit;

    // Variables
    private boolean welcomed = false;
    private int[] problem;
    private int currentInd = -1; // -1: game not started
    private int score = 0;

    // OnClick of Generate Button
    private View.OnClickListener btnGenerateListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            reset();
            currentInd = 0;
            generateOne();
            setCurrentProblem();
            edtAnswer.setText("");
        }
    };

    // OnClick of Submit Button
    private View.OnClickListener btnSubmitListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (currentInd >= 0) { // Game has started
                String input = edtAnswer.getText().toString();
                if (!input.isEmpty()) {
                    double playerAnswer = Double.parseDouble(input);
                    double correctAnswer = (double) problem[INDEX_RESULT];
                    if (playerAnswer == correctAnswer) { // Correct answer
                        score++;
                    }
                    edtAnswer.setText("");
                    currentInd++;
                    if (currentInd < NUM_PROBLEMS) { // Game not ended
                        generateOne();
                        setCurrentProblem();
                        edtAnswer.setText("");
                    } else { // Game ended
                        String strScore = score + TOAST_SCORE;
                        Toast.makeText(MainActivity.this, strScore, Toast.LENGTH_SHORT).show();
                        reset();
                        clearScreen();
                    }
                } else {
                    Toast.makeText(MainActivity.this, TOAST_INVALID_ANS, Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    // OnCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI init
        txtDividend = findViewById(R.id.txtDividend);
        txtDivisor = findViewById(R.id.txtDivisor);
        edtAnswer = findViewById(R.id.edtAnswer);
        btnGenerate = findViewById(R.id.btnGenerate);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnGenerate.setOnClickListener(btnGenerateListener);
        btnSubmit.setOnClickListener(btnSubmitListener);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        welcomed = savedInstanceState.getBoolean(KEY_WELCOME);
        problem = savedInstanceState.getIntArray(KEY_PROBLEM);
        currentInd = savedInstanceState.getInt(KEY_INDEX);
        score = savedInstanceState.getInt(KEY_SCORE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Welcome toast
        if (!welcomed) {
            Intent i = getIntent();
            String welcome = TOAST_WELCOME + i.getStringExtra(STR_EXTRA_NAME);
            Toast.makeText(this, welcome, Toast.LENGTH_SHORT).show();
            welcomed = true;
        }
        // Set problem
        setCurrentProblem();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(KEY_WELCOME, welcomed);
        outState.putIntArray(KEY_PROBLEM, problem);
        outState.putInt(KEY_INDEX, currentInd);
        outState.putInt(KEY_SCORE, score);
        super.onSaveInstanceState(outState);
    }

    // Generate and add a new problem
    private void generateOne() {
        // Range [2, 20]
        int result = new Random().nextInt(19) + 2;
        int divisor = new Random().nextInt(19) + 2;
        int dividend = divisor * result;
        problem = new int[]{ result, divisor, dividend };
    }

    private void setCurrentProblem() {
        if (problem != null) {
            String strDividend = String.valueOf(problem[INDEX_DIVIDEND]);
            String strDivisor = SYMBOL_DIVIDE + problem[INDEX_DIVISOR];
            txtDividend.setText(strDividend);
            txtDivisor.setText(strDivisor);
        } else {
            txtDividend.setText("");
            txtDivisor.setText("");
        }
    }

    private void clearScreen() {
        edtAnswer.setText("");
        txtDividend.setText("");
        txtDivisor.setText("");
    }

    private void reset() {
        problem = null;
        currentInd = -1;
        score = 0;
    }
}
