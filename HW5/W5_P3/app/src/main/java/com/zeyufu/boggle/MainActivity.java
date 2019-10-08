package com.zeyufu.boggle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
        implements OnNewGameListener, OnWordPassListener {

    public static final String[] VOWELS = new String[]{"A", "E", "I", "O", "U"};
    public static final String[] CONSONANTS = new String[]{
            "B", "C", "D", "F", "G", "H", "J", "K", "L", "M",
            "N", "P", "Q", "R", "S", "T", "V", "W", "X", "Y", "Z"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void startNewGame() {
        WordFragment wordFragment =
                (WordFragment) getSupportFragmentManager().findFragmentById(R.id.wordFragment);
        assert wordFragment != null;
        wordFragment.startNewGame();
    }

    @Override
    public void passWord(String word) {
        ScoreFragment scoreFragment =
                (ScoreFragment) getSupportFragmentManager().findFragmentById(R.id.scoreFragment);
        assert scoreFragment != null;
        scoreFragment.setScore(word);
    }
}
