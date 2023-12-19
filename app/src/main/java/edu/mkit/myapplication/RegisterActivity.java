package edu.mkit.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.mkit.myapplication.dao.UserDao;
import edu.mkit.myapplication.model.User;

public class RegisterActivity extends AppCompatActivity {

    Button registerBtn,logOutBtn;
    EditText userNameText,passwordText,checkPasswordText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerBtn = findViewById(R.id.buttonRegister);
        logOutBtn = findViewById(R.id.buttonExit);
        userNameText = findViewById(R.id.editTextUsername);
        passwordText = findViewById(R.id.editTextPassword);
        checkPasswordText = findViewById(R.id.editTextConfirmPassword);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = userNameText.getText().toString();
                String password = passwordText.getText().toString();
                String checkPassword = checkPasswordText.getText().toString();
                if (userName.equals("") && password.equals("")){
                    Toast.makeText(RegisterActivity.this, R.string.loginNullTips, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!checkPassword.equals(password)){
                    Toast.makeText(RegisterActivity.this, R.string.checkPasswordError, Toast.LENGTH_SHORT).show();
                    return;
                }
                UserDao userDao = new UserDao();
                User user = new User(userName, password);
                long l = userDao.saveOne(RegisterActivity.this, user);
                if (l== -1){
                    Toast.makeText(RegisterActivity.this, R.string.registerError, Toast.LENGTH_SHORT).show();
                    return;
                }
                registerSuccess();
            }
        });
    }

    private void registerSuccess() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() { //新开一个线程
            @Override
            public void run() {
                Toast.makeText(RegisterActivity.this, R.string.registerSuccess, Toast.LENGTH_SHORT).show();

                SharedPreferences sharedPreferences = getSharedPreferences("userData", MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString("username",userNameText.getText().toString());
                edit.putString("password",passwordText.getText().toString());
                edit.commit();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                //页面跳转
                RegisterActivity.this.finish();
                //本页面进行销毁
            }
        }, 2000);
    }




}