package com.parse.starter.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.starter.R;

public class LoginActivity extends AppCompatActivity {

    private EditText txtUser;
    private EditText txtPassword;
    private Button btnLogIn;
    private TextView txtGoToRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Check if the user is already logged in
        isUserLoggedIn();

        txtUser = (EditText) findViewById(R.id.txt_username_login);
        txtPassword = (EditText) findViewById(R.id.txt_password_login);
        btnLogIn = (Button) findViewById(R.id.btn_login);
        txtGoToRegistration = (TextView) findViewById(R.id.txt_do_registration);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtUser.getText().toString();
                String password = txtPassword.getText().toString();

                checkLogin(username, password);
            }
        });

        txtGoToRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegisterUser();
            }
        });

    }

    private void checkLogin(String username, String password) {

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e==null){
                    Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    goToMainActivity();
                }else{
                    Toast.makeText(LoginActivity.this, "Error code: " + e.getCode() +
                            " " + e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void isUserLoggedIn(){

        if(ParseUser.getCurrentUser() != null){
            goToMainActivity();
        }

    }

    private void goToMainActivity(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToRegisterUser(){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

}
