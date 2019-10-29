package com.zeyufu.emergencyresponse;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PreferencesActivity extends AppCompatActivity {

    public static final String SP_NAME = "PrefInfo";
    public static final String KEY_PHONE_PREF = "phonePreference";
    public static final String KEY_TEXT_PREF = "textPreference";
    private Button btnClear;
    private Button btnSave;
    private EditText edtPhone;
    private EditText edtText;
    private String phoneNum;
    private String textNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        btnClear = findViewById(R.id.btnClear);
        btnSave = findViewById(R.id.btnSave);
        edtPhone = findViewById(R.id.edtPhone);
        edtText = findViewById(R.id.edtText);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtPhone.setText("");
                edtText.setText("");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNum = edtPhone.getText().toString();
                String textNum = edtText.getText().toString();
                SharedPreferences prefInfo = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefInfo.edit();
                editor.putString(KEY_PHONE_PREF, phoneNum);
                editor.putString(KEY_TEXT_PREF, textNum);
                editor.apply();
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefInfo = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        phoneNum = prefInfo.getString(KEY_PHONE_PREF, getResources().getString(R.string.default_contact));
        textNum = prefInfo.getString(KEY_TEXT_PREF, getResources().getString(R.string.default_contact));
        edtPhone.setText(phoneNum);
        edtText.setText(textNum);
    }
}
