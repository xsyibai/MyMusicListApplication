package edu.mkit.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.mkit.myapplication.dao.UserDao;
import edu.mkit.myapplication.model.User;

public class MainActivity extends AppCompatActivity{

    Button loginBtn,registerBtn,shopBtn;
    EditText userNameText,passwordText;

    TextView tipText;

    private static final int READ_PERMISSION_REQUEST_CODE = 123;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);
        shopBtn = findViewById(R.id.shopBtn);
        userNameText = findViewById(R.id.userText);
        passwordText = findViewById(R.id.lgPasswordText);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = userNameText.getText().toString();
                String password = passwordText.getText().toString();
                Log.e("yibai--------------",userName);
                Log.e("yibai--------------",password);
                if (userName.equals("") || password.equals("")){
                    tipText.setText(R.string.login_tips);
                }else{
                    UserDao userDao = new UserDao();
                    User user = userDao.getOne(MainActivity.this, userName);
                    if (user == null || !user.getPassword().equals(password)) {
                        Toast.makeText(MainActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                    }else{
                        SharedPreferences userData = getSharedPreferences("user_data", MODE_PRIVATE);
                        SharedPreferences.Editor edit = userData.edit();
                        edit.putString("username",userName);
                        edit.putString("password",password);
                        edit.commit();
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this,HomeActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        shopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                startActivity(intent);
            }
        });
    }




//    private void requestReadPermission() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//            // 已经授权，可以开始读取文件
//            Log.i("yibai","已获取权限");
//        } else {
//            // 尚未授权，向用户请求授权
//            Log.i("yibai","未获取权限");
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_PERMISSION_REQUEST_CODE);
//        }
//    }
//
//    // 处理权限请求的结果
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == READ_PERMISSION_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // 授权成功，可以开始读取文件
//                Toast.makeText(this, "读取文件成功", Toast.LENGTH_SHORT).show();
//            } else {
//                // 授权失败，无法读取文件，可以给用户一些提示
//                Toast.makeText(this, "无法读取文件", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
































}