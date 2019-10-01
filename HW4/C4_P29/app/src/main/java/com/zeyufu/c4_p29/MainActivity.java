package com.zeyufu.c4_p29;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final String KEY_TRANSLATE = "translate";

    private Button btnTranslate;
    private TextView txtHello;
    private String translated;

    private View.OnClickListener listenerTranslate = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(getApplicationContext(), TranslateActivity.class);
            startActivity(i);
            finish();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtHello = findViewById(R.id.txtHello);
        btnTranslate = findViewById(R.id.btnTranslate);
        btnTranslate.setOnClickListener(listenerTranslate);

        try {
            Intent i = getIntent();
            translated = i.getStringExtra(KEY_TRANSLATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (translated != null) txtHello.setText(translated);
    }
}
