package edu.mkit.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
                if (checkPassword.equals(password)){
                    Toast.makeText(RegisterActivity.this, R.string.checkPasswordError, Toast.LENGTH_SHORT).show();
                    return;
                }
                UserDao userDao = new UserDao();
                User user = new User(userName, password);
                long l = userDao.saveOne(RegisterActivity.this, user);
                if (l== -1){
                    Toast.makeText(RegisterActivity.this, R.string.register_error, Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });

    }


}