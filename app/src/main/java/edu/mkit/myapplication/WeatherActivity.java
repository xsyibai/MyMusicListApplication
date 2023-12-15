package edu.mkit.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

import edu.mkit.myapplication.model.Weather;

public class WeatherActivity extends AppCompatActivity{

    private Button btn1, btn2, btn3;

    private TextView tv_city_name, tv_temp, tv_wind, tv_pm, tv_weather_name;

    private ImageView iv_weather;

    private final String BASE_URL = "http://t.weather.sojson.com/api/weather/city/";

    private static final int SUCCESS = 1;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == SUCCESS) {
                Weather weather = (Weather) msg.obj;
                tv_city_name.setText(weather.getCityName());
                String temperature = weather.getHeightTemp() + "/" + weather.getLowTemp();
                tv_temp.setText(temperature);
                tv_wind.setText(getString(R.string.wind) + ":" + weather.getWind());
                tv_pm.setText("PM2.5浓度:" + weather.getPm25());
                tv_weather_name.setText(weather.getWeatherName());
                updateWeatherIcon(weather.getWeatherName());
            }
        }
    };

    private void updateWeatherIcon(String weatherName) {
        if (weatherName.equals("晴")) {
            iv_weather.setImageResource(R.drawable.weather_qing);
        } else if (weatherName.equals("多云")) {
            iv_weather.setImageResource(R.drawable.weather_duoyun);
        } else if (weatherName.equals("阴")) {
            iv_weather.setImageResource(R.drawable.weather_yin);
        } else if (weatherName.equals("小雨")) {
            iv_weather.setImageResource(R.drawable.weather_xiaoyu);
        } else if (weatherName.equals("中雨")) {
            iv_weather.setImageResource(R.drawable.weather_zhongyu);
        } else if (weatherName.equals("大雨")) {
            iv_weather.setImageResource(R.drawable.weather_dayu);
        } else if (weatherName.equals("暴雨")) {
            iv_weather.setImageResource(R.drawable.weather_dayu);
        } else if (weatherName.equals("小雪")) {
            iv_weather.setImageResource(R.drawable.weather_xiaoyu);
        } else if (weatherName.equals("中雪")) {
            iv_weather.setImageResource(R.drawable.weather_zhongxue);
        } else if (weatherName.equals("大雪")) {
            iv_weather.setImageResource(R.drawable.weather_daxue);
        } else if (weatherName.equals("霾")) {
            iv_weather.setImageResource(R.drawable.weather_wu);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        init();
    }




    private void init() {
        btn1 = findViewById(R.id.btn_city_one);
        btn2 = findViewById(R.id.btn_city_two);
        btn3 = findViewById(R.id.btn_city_three);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWeatherData("101010100");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWeatherData("101230201");
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWeatherData("101230501");
            }
        });

        tv_city_name = findViewById(R.id.tv_city_name);
        tv_temp = findViewById(R.id.tv_city_temperature);
        tv_wind = findViewById(R.id.tv_wind);
        tv_pm = findViewById(R.id.tv_pm);
        tv_weather_name = findViewById(R.id.tv_weather_name);

        iv_weather = findViewById(R.id.iv_weather_img);

        getWeatherData("101010100");
    }

    private String getResponseContent(String link) {
        try {
            URL url = new URL(link);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                return response.toString();
            } else {
                Toast.makeText(WeatherActivity.this, "访问APi失败", Toast.LENGTH_SHORT).show();
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private void getWeatherData(String cityId) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                String responseContent = getResponseContent(BASE_URL + cityId);
                Weather weather = new Weather();
                String jsonStr = responseContent;
                JSONObject jsonObject = JSON.parseObject(jsonStr);
                String cityName = JSON.parseObject(jsonObject.get("cityInfo").toString()).getString("city");
                JSONObject data = JSON.parseObject(jsonObject.getString("data"));
                String pm25 = data.getString("pm25");
                JSONArray forecast = data.getJSONArray("forecast");
                JSONObject today = forecast.getJSONObject(0);
                String high = (today.getString("high")).replace("高温 ","");
                String low = (today.getString("low")).replace("低温 ","");

                weather.setPm25(pm25);
                weather.setHeightTemp(high);
                weather.setLowTemp(low);
                weather.setWind(today.getString("fl"));
                weather.setCityName(cityName);
                weather.setWeatherName(today.getString("type"));

                Message message = mHandler.obtainMessage(SUCCESS, weather);
                mHandler.sendMessage(message);
            }
        });
        thread.start();
    }



}