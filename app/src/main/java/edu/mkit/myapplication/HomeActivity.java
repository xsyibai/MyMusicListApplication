package edu.mkit.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HomeActivity extends AppCompatActivity {

    Button logoutBtn;

    EditText userName,password;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        logoutBtn = findViewById(R.id.logoutBtn);
        userName = findViewById(R.id.homeUserName);
        password = findViewById(R.id.homePassword);
        SharedPreferences userData = getSharedPreferences("user_data", MODE_PRIVATE);
        userName.setText(userData.getString("username", ""));
        password.setText(userData.getString("password", ""));
        Activity currentActivity = this;
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentActivity.finish();
            }
        });
    }


}