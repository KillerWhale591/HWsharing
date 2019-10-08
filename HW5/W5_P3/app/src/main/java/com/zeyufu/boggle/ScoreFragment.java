package com.zeyufu.boggle;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScoreFragment extends Fragment {

    private static final List<String> VOWELS = Arrays.asList(MainActivity.VOWELS);
    private static final int MINUS_FIVE = -5;
    private static final int MINUS_TWO = -2;
    private static final int MINIMUM_VOWEL_COUNT = 2;
    private static final int MINIMUM_WORD_LENGTH = 4;
    private static final int SCORE_VOWEL = 2;
    private static final int SCORE_CONSONANT = 1;
    private static final String WORD_FILE_NAME = "words.txt";
    private static final String TOAST_CORRECT = "Correct! Score + ";
    private static final String TOAST_INCORRECT = "Wrong answer. Score - ";
    private static final String TOAST_LENGTH_REQUIRE = "Words must be at least 4 chars long.";
    private static final String TOAST_VOWEL_REQUIRE = "Words must contain at least 2 vowels.";
    private static final String TOAST_CONSONANT_REQUIRE = "Consonants must be unique.";
    private TextView txtScore;
    private Button btnNew;
    private ArrayList<String> words;
    OnNewGameListener newGameListener;

    private int score = 0;

    public ScoreFragment() {
        // Required empty public constructor
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            newGameListener.startNewGame();
            score = 0;
            txtScore.setText(String.valueOf(score));
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        newGameListener = (OnNewGameListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_score, container, false);
        txtScore = view.findViewById(R.id.txtScore);
        btnNew = view.findViewById(R.id.btnNew);

        txtScore.setText(String.valueOf(score));
        btnNew.setOnClickListener(listener);

        words = new ArrayList<>();
        readWords();
        return view;
    }

    // Read word file
    private void readWords() {
        String word;
        try {
            InputStream in = getActivity().getAssets().open(WORD_FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            while ((word = reader.readLine()) != null) {
                words.add(word.toUpperCase());
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setScore(String word) {
        int increment;
        String toast;
        if (!words.contains(word)) { // Word not contained
            increment = MINUS_FIVE;
            toast = TOAST_INCORRECT + Math.abs(increment);
        } else { // Word contained
            int length = word.length();
            if (length < MINIMUM_WORD_LENGTH) { // Length too short
                increment = MINUS_TWO;
                toast = TOAST_LENGTH_REQUIRE;
            } else { // Length >= 4, loop all letters
                int countV = 0, countC = 0;
                ArrayList<String> usedConsonants = new ArrayList<>();
                for (int i = 0; i < word.length(); i++) {
                    String letter = String.valueOf(word.charAt(i));
                    if (VOWELS.contains(letter)) {
                        countV++;
                    } else {
                        if (usedConsonants.contains(letter)) { // Check repetitive consonants
                            increment = MINUS_TWO;
                            toast = TOAST_CONSONANT_REQUIRE;
                            Toast.makeText(getContext(), toast, Toast.LENGTH_SHORT).show();
                            score += increment;
                            return;
                        } else {
                            usedConsonants.add(letter);
                            countV++;
                        }
                        countC++;
                    }
                }
                if (countV < MINIMUM_VOWEL_COUNT) { // Check vowel count
                    increment = MINUS_TWO;
                    toast = TOAST_VOWEL_REQUIRE;
                } else { // Right and valid answer
                    increment = countC * SCORE_CONSONANT + countV * SCORE_VOWEL;
                    toast = TOAST_CORRECT + increment;
                }
            }
        }
        Toast.makeText(getContext(), toast, Toast.LENGTH_SHORT).show();
        score += increment;
        txtScore.setText(String.valueOf(score));
    }
}
