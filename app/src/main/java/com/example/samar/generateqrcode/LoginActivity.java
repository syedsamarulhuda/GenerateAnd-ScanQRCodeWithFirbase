package com.example.samar.generateqrcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etUsername;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        boolean isloggedIn = SharedPrefUtil.getBoolean(this, "logged_in");
        if (isloggedIn) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        } else {
            initview();
        }
    }

    private void initview() {

        etUsername = (EditText) findViewById(R.id.etUsername);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                login();
                break;
        }
    }

    private void login() {
        if (etUsername.getText().toString().equals("")) {

            Toast.makeText(this, "Please Enter Username", Toast.LENGTH_LONG).show();

        } else {

            String name;
            int bikerId;

            if (etUsername.getText().toString().equalsIgnoreCase("samar")) {

                name = "Samar";
                bikerId = 125;

                setCredentials(bikerId, name, false);

            } else if (etUsername.getText().toString().equalsIgnoreCase("test")) {

                name = "Test";
                bikerId = 100;

                setCredentials(bikerId, name, false);

            } else if (etUsername.getText().toString().equalsIgnoreCase("apeksha")) {
                name = "Apeksha";
                bikerId = 144;

                setCredentials(bikerId, name, true);
            } else if (etUsername.getText().toString().equalsIgnoreCase("tushar")) {
                name = "Tushar";
                bikerId = 101;

                setCredentials(bikerId, name, true);
            } else {
                Toast.makeText(this, "Invalid Username", Toast.LENGTH_LONG).show();
            }

        }
    }


    private void setCredentials(int bikerId, String bikerName, boolean isManager) {
        SharedPrefUtil.putString(this, "biker_name", bikerName);
        SharedPrefUtil.putInt(this, "biker_id", bikerId);
        SharedPrefUtil.putBoolean(this, "logged_in", true);
        if (isManager) {
            SharedPrefUtil.putBoolean(this, "is_manager", true);
            FireBaseRealTimeDb.getInstance(this).writeToManagerProfile(bikerId, bikerName, 1, "manager_profile");
        } else {
            SharedPrefUtil.putBoolean(this, "is_manager", false);
            FireBaseRealTimeDb.getInstance(this).writeToBikerProfile(bikerId, bikerName, 1, "biker_profile");
        }

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
