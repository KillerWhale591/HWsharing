package com.zeyufu.c2_p23;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView txtLight;
    private Button btnNext;

    private int[] lights = new int[] {
            R.color.greenLight,
            R.color.yellowLight,
            R.color.redLight
    };
    private int state = 0;

    private View.OnClickListener btnNextListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            state++;
            if (state > 2) state = 0;
            setLight();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtLight = findViewById(R.id.txtLight);
        btnNext = findViewById(R.id.btnNext);

        btnNext.setOnClickListener(btnNextListener);
        setLight();
    }

    private void setLight() {
        txtLight.setBackgroundColor(getResources().getColor(lights[state]));
    }
}
