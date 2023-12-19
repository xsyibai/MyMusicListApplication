package edu.mkit.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import edu.mkit.myapplication.dao.UserDao;
import edu.mkit.myapplication.model.User;

public class LoginActivity extends AppCompatActivity {

    Button loginBtn;
    EditText userNameText,passwordText;
    TextView registerText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userNameText = findViewById(R.id.userName_Text);
        passwordText = findViewById(R.id.password_Text);
        loginBtn = findViewById(R.id.login_Btn);
        registerText = findViewById(R.id.register_Text);

        initHandler();
        SharedPreferences userData = getSharedPreferences("userData", MODE_PRIVATE);
        userNameText.setText(userData.getString("username", ""));
        passwordText.setText(userData.getString("password", ""));
    }

    private void initHandler(){
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = userNameText.getText().toString();
                String password = passwordText.getText().toString();
                if (userName.equals("") || password.equals("")){
                    Toast.makeText(LoginActivity.this, R.string.loginNullTips, Toast.LENGTH_SHORT).show();
                }else{
                    UserDao userDao = new UserDao();
                    User user = userDao.getOne(LoginActivity.this, userName);
                    if (user == null || !user.getPassword().equals(password)) {
                        Toast.makeText(LoginActivity.this, R.string.authError, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this,MusicListActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }
            }
        });
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }






























}