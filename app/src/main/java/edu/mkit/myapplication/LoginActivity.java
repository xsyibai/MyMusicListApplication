package edu.mkit.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.mkit.myapplication.dao.UserDao;
import edu.mkit.myapplication.model.User;

public class LoginActivity extends AppCompatActivity{

    Button loginBtn;
    EditText userNameText,passwordText;
    TextView tipText,registerBtn;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);
        userNameText = findViewById(R.id.userText);
        passwordText = findViewById(R.id.lgPasswordText);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = userNameText.getText().toString();
                String password = passwordText.getText().toString();
                if (userName.equals("") || password.equals("")){
                    tipText.setText(R.string.loginNullTips);
                }else{
                    UserDao userDao = new UserDao();
                    User user = userDao.getOne(LoginActivity.this, userName);
                    if (user == null || !user.getPassword().equals(password)) {
                        Toast.makeText(LoginActivity.this, R.string.authError, Toast.LENGTH_SHORT).show();
                    }else{
                        SharedPreferences userData = getSharedPreferences("user_data", MODE_PRIVATE);
                        SharedPreferences.Editor edit = userData.edit();
                        edit.putString("username",userName);
                        edit.putString("password",password);
                        edit.commit();
                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this,HomeActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent, 1);
            }
        });

    }






























}