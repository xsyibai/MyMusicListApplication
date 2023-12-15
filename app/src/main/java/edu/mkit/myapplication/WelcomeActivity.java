package edu.mkit.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        startMainActivity();
    }

    private void startMainActivity() {
            Handler mhander = new Handler();
            mhander.postDelayed(new Runnable() { //新开一个线程
                @Override
                public void run() {
                    Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    //页面跳转
                    WelcomeActivity.this.finish();
                    //本页面进行销毁
                }
            }, 3000);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 ){
            return  true;
        }
        return super.onKeyDown(keyCode, event);
    }


}