package com.zeyufu.boggle;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class WordFragment extends Fragment {

    private static final int GRID_SIZE = 4;
    private static final int BUTTON_COUNT = 16;
    private static final int VOWEL_COUNT = 6;
    private static final int INDEX_X_POS = 0;
    private static final int INDEX_Y_POS = 1;
    private static final String[] VOWELS = MainActivity.VOWELS;
    private static final String[] CONSONANTS = MainActivity.CONSONANTS;
    private static final String TOAST_EMPTY = "Please enter a word.";
    private static final String TOAST_SUBMITTED = "This word is submitted before. Try another one.";

    private TextView txtWord;
    private Button btnClear;
    private Button btnSubmit;
    private LinearLayout[] rows;
    OnWordPassListener wordListener;
    private Button[][] buttons = new Button[GRID_SIZE][GRID_SIZE];

    private ArrayList<String> submittedWord = new ArrayList<>();
    private ArrayList<Integer> usedBtnPos = new ArrayList<>();
    private String currWord = "";

    public WordFragment() {
        // Required empty public constructor
    }

    private View.OnClickListener clearListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            clear();
        }
    };

    private View.OnClickListener submitListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (currWord.isEmpty()) {
                Toast.makeText(getContext(), TOAST_EMPTY, Toast.LENGTH_SHORT).show();
            } else {
                if (submittedWord.contains(currWord)) {
                    Toast.makeText(getContext(), TOAST_SUBMITTED, Toast.LENGTH_SHORT).show();
                } else {
                    submittedWord.add(currWord);
                    wordListener.passWord(currWord);
                }
                clear();
            }
        }
    };

    private View.OnClickListener letterListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            int[] position = decodePositionByIndex(id);
            // Set button enable
            usedBtnPos.add(id);
            setAllButtonEnable(false);
            enableNeighbors(position[INDEX_X_POS], position[INDEX_Y_POS]);
            // Set word text
            Button b = (Button) view;
            currWord += b.getText();
            txtWord.setText(currWord);
            b.setTextColor(getResources().getColor(R.color.colorClicked));
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        wordListener = (OnWordPassListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_word, container, false);
        txtWord = view.findViewById(R.id.txtWord);
        btnClear = view.findViewById(R.id.btnClear);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        btnClear.setOnClickListener(clearListener);
        btnSubmit.setOnClickListener(submitListener);

        // Generate letter buttons
        rows = new LinearLayout[]{
                view.findViewById(R.id.layoutRow1),
                view.findViewById(R.id.layoutRow2),
                view.findViewById(R.id.layoutRow3),
                view.findViewById(R.id.layoutRow4)
        };

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                Button btn = new Button(getContext());
                btn.setId(generateButtonId(i, j));
                btn.setTextSize(20);
                btn.setTextColor(getResources().getColor(R.color.colorBlack));
                btn.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
                btn.setOnClickListener(letterListener);
                LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
                llp.weight = 1;
                rows[i].addView(btn, llp);
                buttons[i][j] = btn;
            }
        }

        fillLetters();
        return view;
    }

    private int generateButtonId(int x, int y) {
        return x + y * 4;
    }

    private int[] decodePositionByIndex(int index) {
        int x = index % GRID_SIZE;
        int y = index / GRID_SIZE;
        return new int[]{x, y};
    }

    private String getRandomVowel() {
        int index = new Random().nextInt(VOWELS.length);
        return VOWELS[index];
    }

    private String getRandomConsonant() {
        int index = new Random().nextInt(CONSONANTS.length);
        return CONSONANTS[index];
    }

    private void fillLetters() {
        ArrayList<Integer> randomIndex = new ArrayList<>();
        for (int i = 0; i < BUTTON_COUNT; i++) {
            randomIndex.add(i);
        }
        Collections.shuffle(randomIndex);
        for (int j = 0; j < BUTTON_COUNT; j++) {
            int[] btnXYPos = decodePositionByIndex(randomIndex.get(j));
            if (j < VOWEL_COUNT) {
                buttons[btnXYPos[INDEX_X_POS]][btnXYPos[INDEX_Y_POS]].setText(getRandomVowel());
            } else {
                buttons[btnXYPos[INDEX_X_POS]][btnXYPos[INDEX_Y_POS]].setText(getRandomConsonant());
            }
        }
    }

    private void enableNeighbors(int x, int y) {
        ArrayList<int[]> neighbors = new ArrayList<int[]>() {};
        neighbors.add(new int[] {x, y - 1});
        neighbors.add(new int[] {x, y + 1});
        neighbors.add(new int[] {x - 1, y});
        neighbors.add(new int[] {x + 1, y});
        for (int i = 0; i < neighbors.size(); i++) {
            int[] neighbor = neighbors.get(i);
            int xPos = neighbor[INDEX_X_POS];
            int yPos = neighbor[INDEX_Y_POS];
            if (!usedBtnPos.contains(generateButtonId(xPos, yPos))
                    && xPos >= 0 && xPos < GRID_SIZE && yPos >= 0 && yPos < GRID_SIZE) {
                buttons[xPos][yPos].setEnabled(true);
            }
        }
    }

    private void setAllButtonEnable(boolean enable) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                buttons[i][j].setEnabled(enable);
                if (enable) buttons[i][j].setTextColor(getResources().getColor(R.color.colorBlack));
            }
        }
    }

    private void clear() {
        usedBtnPos.clear();
        setAllButtonEnable(true);
        currWord = "";
        txtWord.setText(currWord);
    }

    public void startNewGame() {
        clear();
        submittedWord.clear();
        fillLetters();
    }
}
