package com.zeyufu.flashcard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private final String STR_EXTRA_NAME = "username";
    private final String USERNAME = "cs";
    private final String PASSWORD = "591";
    private final String TOAST_INVALID = "Invalid username or password!";

    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnLogin;

    private View.OnClickListener btnLoginListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String username = edtUsername.getText().toString();
            String password = edtPassword.getText().toString();

            if (username.equals(USERNAME) && password.equals(PASSWORD)) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra(STR_EXTRA_NAME, username);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, TOAST_INVALID, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(btnLoginListener);
    }
}
