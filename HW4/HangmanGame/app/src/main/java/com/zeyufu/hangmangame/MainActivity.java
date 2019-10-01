package com.zeyufu.hangmangame;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    // Parameters
    private final int NUM_LETTERS = 26;
    private final int NUM_FRAMES = 7;
    private final int COL_COUNT_PORTRAIT = 9;
    private final int COL_COUNT_LANDSCAPE = 7;
    private final int STATE_NOT_STARTED = -1;
    private final int STATE_GAME_FIRST = 0;
    private final int STATE_GAME_LAST = 2;
    private final int UI_TEXT_SIZE = 20;
    private final int LAYOUT_WEIGHT = 1;
    private final int LAYOUT_GRID_SIZE = 1;
    private final int CHAR_INT_DIFF = 65;

    // Strings
    private final String EMPTY = "";
    private final String BLANK = " ";
    private final String UNDERLINE = "___";
    private final String TOAST_OVER = "Game Over!";
    private final String TOAST_COMPLETED = "Game Completed!";
    private final String ALPHABET = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z";

    // Keys for Bundle
    private final String KEY_STATE = "state";
    private final String KEY_IMAGE = "image";
    private final String KEY_CORRECT_COUNT = "correct";
    private final String KEY_CURRENT_WORD = "word";
    private final String KEY_CURRENT_HINT = "hint";
    private final String KEY_ONGAME = "ongame";
    private final String KEY_HINT_SHOWED = "hint_showed";
    private final String KEY_HINT_DISABLED = "hint_disabled";
    private final String KEY_WRONG = "wrong";
    private final String KEY_RIGHT = "right";
    private final String KEY_REMAIN = "remain";

    // Widgets
    private Button[] btnLetters;
    private TextView[] answers;
    private Button btnRestart;
    private Button btnHint;
    private TextView txtHint;
    private ImageView imgHangman;
    private LinearLayout layoutBlanks;
    private GridLayout layoutLetters;
    private GridLayout.LayoutParams glp;
    private LinearLayout.LayoutParams llp;

    // Arrays
    private String[] words = new String[] { "APPLE", "FOOTBALL", "MUSTARD" };
    private String[] hints = new String[] { "Fruit", "Sport", "Sauce" };
    private String[] letters = ALPHABET.split(BLANK);
    private int[] imgRes = new int[] {
            R.drawable.img_0,
            R.drawable.img_1,
            R.drawable.img_2,
            R.drawable.img_3,
            R.drawable.img_4,
            R.drawable.img_5,
            R.drawable.img_6,
            R.drawable.img_7
    };

    // Game variables
    private int state = STATE_NOT_STARTED;
    private int currentImage = 0;
    private int correctCount = 0;
    private String currentWord;
    private String currentHint;
    private boolean ongame = false;
    private boolean hintShowed = false;
    private boolean hintDisabled = false;
    private ArrayList<String> wrongLetters = new ArrayList<>();
    private ArrayList<String> rightLetters = new ArrayList<>();
    private ArrayList<String> remaining = new ArrayList<>(Arrays.asList(letters));

    // New game on click
    private View.OnClickListener listenerRestart = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            resetGame();
            // Set state and word
            ongame = true;
            state += 1;
            if (state > STATE_GAME_LAST) state = STATE_GAME_FIRST;
            currentWord = words[state];
            currentHint = hints[state];
            setBlanks();
        }
    };

    // Letters on click
    private View.OnClickListener listenerLetter = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (state != STATE_NOT_STARTED && ongame) { // Game started and not ended
                Button b = (Button) view;
                b.setEnabled(false);
                char curLetter = b.getText().charAt(0);
                if (currentWord.contains(b.getText())) { // Right letter
                    rightLetters.add(String.valueOf(curLetter));
                    // Fill in TextView
                    for (int i = 0; i < currentWord.length(); i++) {
                        if (currentWord.charAt(i) == curLetter) { // A hit
                            answers[i].setText(String.valueOf(curLetter));
                            correctCount++;
                        }
                    }
                    checkGameComplete();
                } else { // Wrong letter
                    wrongLetters.add(String.valueOf(curLetter));
                    currentImage++;
                    imgHangman.setImageResource(imgRes[currentImage]);
                    remaining.remove(String.valueOf(curLetter));
                    checkGameOver();
                }
            }
        }
    };

    // Hint on click
    private View.OnClickListener listenerHint = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (state != STATE_NOT_STARTED && ongame) {
                currentImage++;
                imgHangman.setImageResource(imgRes[currentImage]);
                checkGameOver();
                // First time or Second time
                if (!hintShowed) {
                    txtHint.setText(currentHint);
                    hintShowed = true;
                } else {
                    view.setEnabled(false);
                    hintDisabled = true;
                    randomDisable(remaining);
                }
            }
        }
    };


    /**
     * OnCreate
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI init
        btnRestart = findViewById(R.id.btnRestart);
        imgHangman = findViewById(R.id.imgHangman);
        layoutBlanks = findViewById(R.id.layoutBlanks);
        layoutLetters = findViewById(R.id.layoutLetters);
        btnRestart.setOnClickListener(listenerRestart);

        // Init image
        imgHangman.setImageResource(imgRes[currentImage]);

        // Landscape vs Portrait
        int colCount;
        if (isLandScape()) {
            txtHint = findViewById(R.id.txtHint);
            btnHint = findViewById(R.id.btnHint);
            btnHint.setOnClickListener(listenerHint);
            colCount = COL_COUNT_LANDSCAPE;
        } else {
            colCount = COL_COUNT_PORTRAIT;
        }

        // Init buttons
        btnLetters = new Button[NUM_LETTERS];
        for (int i = 0, c = 0, r = 0; i < NUM_LETTERS; i++, c++) {
            if (c >= colCount) {
                c = 0;
                r++;
            }
            Button btnLetter = new Button(MainActivity.this);
            btnLetter.setText(letters[i]);
            btnLetter.setOnClickListener(listenerLetter);
            glp = new GridLayout.LayoutParams();
            glp.width = 0;
            glp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            glp.rowSpec = GridLayout.spec(r, LAYOUT_GRID_SIZE, LAYOUT_WEIGHT);
            glp.columnSpec = GridLayout.spec(c, LAYOUT_GRID_SIZE, LAYOUT_WEIGHT);
            glp.setGravity(Gravity.FILL);
            layoutLetters.addView(btnLetter, glp);
            btnLetters[i] = btnLetter;
        }
    }

    /**
     * OnRestore
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        state = savedInstanceState.getInt(KEY_STATE);
        currentImage = savedInstanceState.getInt(KEY_IMAGE);
        correctCount = savedInstanceState.getInt(KEY_CORRECT_COUNT);
        currentWord = savedInstanceState.getString(KEY_CURRENT_WORD);
        currentHint = savedInstanceState.getString(KEY_CURRENT_HINT);
        ongame = savedInstanceState.getBoolean(KEY_ONGAME);
        hintShowed = savedInstanceState.getBoolean(KEY_HINT_SHOWED);
        hintDisabled = savedInstanceState.getBoolean(KEY_HINT_DISABLED);
        wrongLetters = savedInstanceState.getStringArrayList(KEY_WRONG);
        rightLetters = savedInstanceState.getStringArrayList(KEY_RIGHT);
        remaining = savedInstanceState.getStringArrayList(KEY_REMAIN);
    }

    /**
     * OnResume
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (state != STATE_NOT_STARTED) {
            imgHangman.setImageResource(imgRes[currentImage]);
            setBlanks();
            setRightLetters(rightLetters);
            setClickedLetters(rightLetters, wrongLetters);
            // Set hint
            if (isLandScape()) {
                if (hintShowed) {
                    txtHint.setText(currentHint);
                    if (hintDisabled) {
                        btnHint.setEnabled(false);
                    }
                }
            }
        }
    }

    /**
     * OnSave
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_STATE, state);
        outState.putInt(KEY_IMAGE, currentImage);
        outState.putInt(KEY_CORRECT_COUNT, correctCount);
        outState.putString(KEY_CURRENT_WORD, currentWord);
        outState.putString(KEY_CURRENT_HINT, currentHint);
        outState.putBoolean(KEY_ONGAME, ongame);
        outState.putBoolean(KEY_HINT_SHOWED, hintShowed);
        outState.putBoolean(KEY_HINT_DISABLED, hintDisabled);
        outState.putStringArrayList(KEY_WRONG, wrongLetters);
        outState.putStringArrayList(KEY_RIGHT, rightLetters);
        outState.putStringArrayList(KEY_REMAIN, remaining);
        super.onSaveInstanceState(outState);
    }

    // Set blank TextView
    private void setBlanks() {
        answers = new TextView[currentWord.length()];
        for (int i = 0; i < currentWord.length(); i++) {
            // Init blank TextView
            TextView txtAns = new TextView(MainActivity.this);
            txtAns.setText(UNDERLINE);
            txtAns.setTextSize(UI_TEXT_SIZE);
            txtAns.setGravity(Gravity.CENTER);
            llp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            llp.weight = LAYOUT_WEIGHT;
            layoutBlanks.addView(txtAns, llp);
            answers[i] = txtAns;
            // Remove right letter from remaining wrong letters
            remaining.remove(String.valueOf(currentWord.charAt(i)));
        }
    }

    // Set answered right letter when resuming
    private void setRightLetters(ArrayList<String> rightLetters) {
        for (int i = 0; i < currentWord.length(); i++) {
            String curLetter = String.valueOf(currentWord.charAt(i));
            if (rightLetters.contains(curLetter)) {
                answers[i].setText(curLetter);
            }
        }
    }

    // Set clicked letter buttons
    private void setClickedLetters(ArrayList<String> rightLetters, ArrayList<String> wrongLetters) {
        for (int i = 0; i < NUM_LETTERS; i++) {
            if (rightLetters.contains(letters[i]) || wrongLetters.contains(letters[i])) {
                btnLetters[i].setEnabled(false);
            }
        }
    }

    // Randomly disable half wrong remaining letters
    private void randomDisable(ArrayList<String> remaining) {
        Collections.shuffle(remaining);
        for (int i = 0; i < remaining.size(); i += 2) {
            int index = letterToIndex(remaining.get(i));
            btnLetters[index].setEnabled(false);
            wrongLetters.add(String.valueOf(remaining.get(i)));
        }
    }

    // Check if game is complete
    private void checkGameComplete() {
        if (correctCount >= currentWord.length()) { // Game completed
            Toast.makeText(MainActivity.this, TOAST_COMPLETED, Toast.LENGTH_SHORT).show();
            ongame = false;
        }
    }

    // Check if game is over
    private void checkGameOver() {
        if (currentImage >= NUM_FRAMES) { // Game over
            Toast.makeText(MainActivity.this, TOAST_OVER, Toast.LENGTH_SHORT).show();
            ongame = false;
        }
    }

    // Reset
    private void resetGame() {
        // Clear all game states
        layoutBlanks.removeAllViews();
        currentImage = 0;
        correctCount = 0;
        hintShowed = false;
        hintDisabled = false;
        wrongLetters.clear();
        rightLetters.clear();
        remaining = new ArrayList<>(Arrays.asList(letters));
        // Clear UI
        for (Button b : btnLetters) b.setEnabled(true);
        imgHangman.setImageResource(imgRes[currentImage]);
        if (isLandScape()) {
            btnHint.setEnabled(true);
            txtHint.setText(EMPTY);
        }
    }

    // Landscape mode?
    private boolean isLandScape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    // Letter to index in alphabet
    private int letterToIndex(String letter) {
        return (int) letter.charAt(0) - CHAR_INT_DIFF;
    }
}
