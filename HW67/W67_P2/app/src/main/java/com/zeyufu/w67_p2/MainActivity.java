package com.zeyufu.w67_p2;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int STATE_EMPTY = 0;
    private static final int STATE_FIRST = 1;
    private static final int STATE_SECOND = 2;
    private static final String SP_NAME = "StateInfo";
    private static final String SP_STATE_KEY = "appState";

    private Button btnFirst;
    private Button btnSecond;
    private ImageView imgContent;
    private TextView txtCaption;
    private int state = STATE_EMPTY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnFirst = findViewById(R.id.btnFirst);
        btnSecond = findViewById(R.id.btnSecond);
        imgContent = findViewById(R.id.imgContent);
        txtCaption = findViewById(R.id.txtCaption);
        btnFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFirstRes();
                state = STATE_FIRST;
            }
        });
        btnSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSecondRes();
                state = STATE_SECOND;
            }
        });

        SharedPreferences stateInfo = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        state = stateInfo.getInt(SP_STATE_KEY, STATE_EMPTY);

        if (state == STATE_FIRST) {
            setFirstRes();
        } else if (state == STATE_SECOND) {
            setSecondRes();
        }
    }

//    /**
//     * Fix: save SharedPreferences in OnPause
//     */
//    @Override
//    protected void onPause() {
//        super.onPause();
//        SharedPreferences stateInfo = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = stateInfo.edit();
//        editor.putInt(SP_STATE_KEY, state);
//        editor.apply();
//    }

    @Override
    protected void onDestroy() {
        SharedPreferences stateInfo = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = stateInfo.edit();
        editor.putInt(SP_STATE_KEY, state);
        editor.apply();
        super.onDestroy();
    }

    private void setFirstRes() {
        imgContent.setImageResource(R.drawable.android);
        txtCaption.setText(R.string.caption_first);
    }

    private void setSecondRes() {
        imgContent.setImageResource(R.drawable.ios);
        txtCaption.setText(R.string.caption_second);
    }
}
