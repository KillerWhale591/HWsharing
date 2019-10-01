package com.zeyufu.c4_p29;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TranslateActivity extends AppCompatActivity {

    private final String KEY_TRANSLATE = "translate";

    private Button btnFrench;
    private Button btnSpanish;
    private Button btnJapanese;
    private Button btnChinese;
    private Button btnDutch;

    private View.OnClickListener listenerLanguage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            String translated;
            switch (view.getId()) {
                case R.id.btnFrench:
                    translated = getResources().getString(R.string.hello_french);
                    break;
                case R.id.btnSpanish:
                    translated = getResources().getString(R.string.hello_spanish);
                    break;
                case R.id.btnJapanese:
                    translated = getResources().getString(R.string.hello_japanese);
                    break;
                case R.id.btnChinese:
                    translated = getResources().getString(R.string.hello_chinese);
                    break;
                case R.id.btnDutch:
                    translated = getResources().getString(R.string.hello_dutch);
                    break;
                default:
                    translated = getResources().getString(R.string.hello_english);
            }
            i.putExtra(KEY_TRANSLATE, translated);
            startActivity(i);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        btnFrench = findViewById(R.id.btnFrench);
        btnSpanish = findViewById(R.id.btnSpanish);
        btnJapanese = findViewById(R.id.btnJapanese);
        btnChinese = findViewById(R.id.btnChinese);
        btnDutch = findViewById(R.id.btnDutch);

        btnFrench.setOnClickListener(listenerLanguage);
        btnSpanish.setOnClickListener(listenerLanguage);
        btnJapanese.setOnClickListener(listenerLanguage);
        btnChinese.setOnClickListener(listenerLanguage);
        btnDutch.setOnClickListener(listenerLanguage);
    }
}
