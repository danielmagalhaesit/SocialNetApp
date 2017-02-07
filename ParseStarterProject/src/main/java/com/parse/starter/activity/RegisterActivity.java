package com.parse.starter.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.parse.starter.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText txtUser;
    private EditText txtEmail;
    private EditText txtPassword;
    private Button btnRegister;
    private TextView txtDoLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtUser = (EditText) findViewById(R.id.txt_user_reg);
        txtEmail = (EditText) findViewById(R.id.txt_email_reg);
        txtPassword = (EditText) findViewById(R.id.txt_password_reg);
        btnRegister = (Button) findViewById(R.id.btn_register);
        txtDoLogIn = (TextView) findViewById(R.id.txt_do_login);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        txtDoLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogInScreen();
            }
        });



    }private void registerUser() {

        // Creating user object
        ParseUser parseUser = new ParseUser();
        parseUser.setUsername(txtUser.getText().toString());
        parseUser.setEmail(txtEmail.getText().toString());
        parseUser.setPassword(txtPassword.getText().toString());

        // Saving data
        parseUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    goToLogInScreen();
                }else{
                    Toast.makeText(RegisterActivity.this, "Error code: " + e.getCode() +
                            " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void goToLogInScreen(){
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
