package com.example.w8_p1_emergency_response;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PreferencesActivity extends AppCompatActivity {
    EditText edtPhone;
    EditText edtText;
    Button save;
    Button clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        edtPhone = (EditText)findViewById(R.id.edtPhone);
        edtText = (EditText)findViewById(R.id.edtText);
        save = (Button) findViewById(R.id.btnSave);
        clear = (Button)findViewById(R.id.btnClear);
        SharedPreferences simpleAppInfo = getSharedPreferences("ActivityOneInfo", Context.MODE_PRIVATE);
        String phone = simpleAppInfo.getString("txtPhone","");
        String text = simpleAppInfo.getString("txtText","");
        edtPhone.setText(phone);
        edtText.setText(text);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences simpleAppInfo = getSharedPreferences("ActivityOneInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = simpleAppInfo.edit();
                editor.putString("txtPhone", edtPhone.getText().toString());
                editor.putString("txtText",edtText.getText().toString());
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("txtPhone", edtPhone.getText().toString());
                intent.putExtra("txtText",edtText.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences simpleAppInfo = getSharedPreferences("ActivityOneInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = simpleAppInfo.edit();
                editor.putString("txtPhone", "");
                editor.putString("txtText","");
                editor.apply();

                edtPhone.setText("");
                edtText.setText("");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("txtPhone", edtPhone.getText().toString());
                intent.putExtra("txtText",edtText.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

}
