package edu.mkit.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.Manifest;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class NetWorkActivity extends AppCompatActivity {

    ImageView imageIv;
    EditText searchEt;
    Button searchBtn;

    private static final int MSG_SHOW_IMAGE = 1;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_SHOW_IMAGE) {
                Bitmap bitmap = (Bitmap) msg.obj;
                imageIv.setImageBitmap(bitmap);
            }
        }
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_work);

        imageIv = findViewById(R.id.image_iv);
        searchBtn = findViewById(R.id.search_btn);
        searchEt = findViewById(R.id.search_et);
        searchEt.setText("https://img1.baidu.com/it/u=3251236940,937427553&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=750");
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imageUrl = searchEt.getText().toString();
                if (imageUrl.equals("")){
                    Toast.makeText(NetWorkActivity.this, "搜索内容不能为空", Toast.LENGTH_SHORT).show();
                }else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            URL url = null;
                            try {
                                url = new URL(imageUrl);
                                URLConnection urlConnection = url.openConnection();
                                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                connection.setRequestMethod("GET");
                                int responseCode = connection.getResponseCode();
                                if (responseCode == HttpURLConnection.HTTP_OK){
                                    Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());
                                    Message message = mHandler.obtainMessage(MSG_SHOW_IMAGE, bitmap);
                                    mHandler.sendMessage(message);
                                }else{
                                    Toast.makeText(NetWorkActivity.this, "获取图片失败", Toast.LENGTH_SHORT).show();
                                }
                            } catch (MalformedURLException e) {
                                throw new RuntimeException(e);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }).start();
                }
            }
        });
    }


}