package edu.mkit.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.mkit.myapplication.dao.UserDao;
import edu.mkit.myapplication.model.User;

public class RegisterActivity extends AppCompatActivity {

    Button registerBtn;
    EditText userNameText,passwordText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerBtn = findViewById(R.id.registerBtn);
        userNameText = findViewById(R.id.userText);
        passwordText = findViewById(R.id.lgPasswordText);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = userNameText.getText().toString();
                String password = passwordText.getText().toString();
                if (userName.equals("") || password.equals("")){
                    Toast.makeText(RegisterActivity.this, "账号或密码不能为空", Toast.LENGTH_SHORT).show();
                }else{
                    UserDao userDao = new UserDao();
                    User user = new User(userName, password);
                    long l = userDao.saveOne(RegisterActivity.this, user);
                    if (l== -1){
                        Toast.makeText(RegisterActivity.this, "注册失败!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
            }
        });

    }


}