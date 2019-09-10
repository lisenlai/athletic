package com.laidage.ican.Doing;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.laidage.ican.DataBase.History;
import com.laidage.ican.R;
import com.laidage.ican.map1;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Running extends AppCompatActivity implements  AMapLocationListener, View.OnClickListener{
//跑步中
    private Boolean firstLocation = true;
    private LatLng nowLL = null, lastLL = null;
    float distance = 0, speed = 0;
    String startTime;
    int addTime = 0,useTime = 0;
    float kiloCalorie = 0;
    private AMapLocationClient aMapLocationClient;
    private AMapLocationClientOption aMapLocationClientOption;
    private TextView doingText, kiloMetersText, speedText, timeText, kiloCalorieText;
    private Button mapButton, stopButton, continueButton, pauseButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doing);
        initView();//界面添加管关联
        startLocation();
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日 HH:mm:ss ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        startTime = formatter.format(curDate);
        getTime();
    }

    public void initView() {
        doingText = findViewById(R.id.doing);
        doingText.setText("跑步中");
        kiloMetersText = findViewById(R.id.kilometers);
        speedText = findViewById(R.id.speed);
        timeText = findViewById(R.id.time);
        kiloCalorieText = findViewById(R.id.kilocalorie);
        mapButton = findViewById(R.id.mapButton2);
        stopButton = findViewById(R.id.stop1);
        stopButton.setVisibility(View.INVISIBLE);
        continueButton = findViewById(R.id.continue1);
        continueButton.setVisibility(View.INVISIBLE);
        pauseButton = findViewById(R.id.pause1);
        mapButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        continueButton.setOnClickListener(this);
        pauseButton.setOnClickListener(this);
    }
    public void startLocation() {
        aMapLocationClient = new AMapLocationClient(getApplicationContext());
        aMapLocationClient.setLocationListener(this);

        //定位参数设置
        aMapLocationClientOption = new AMapLocationClientOption();
        aMapLocationClientOption.setInterval(10000);//定位间隔
        aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        aMapLocationClient.setLocationOption(aMapLocationClientOption);
        aMapLocationClient.startLocation();
    }
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        addTime += 10;
        if(firstLocation) {
            if(aMapLocation != null && aMapLocation.getLongitude() !=0 && aMapLocation.getLatitude()!=0) {
                firstLocation = false;
                lastLL = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
            }
        }
        else
        {
            if(aMapLocation != null && aMapLocation.getLongitude() !=0 && aMapLocation.getLatitude()!=0)
            {
                nowLL = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());

                distance += AMapUtils.calculateLineDistance(nowLL, lastLL);
                kiloCalorie = distance * 60 / 1000;
                //Toast.makeText(Running.this, kiloCalorie,Toast.LENGTH_SHORT).show();
                speed = distance/addTime*60;
                lastLL = nowLL;
                speedText.setText(String.format("%.1f",speed));
                kiloMetersText.setText(String.format("%.2f",distance/1000));
                kiloCalorieText.setText(String.format("%.2f",kiloCalorie));
            }
        }
    }

    public void getTime() {
        runOnUiThread(new Runnable() {
            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            @Override
            public void run() {
                Handler handler = new Handler();
                useTime++;
                timeText.setText(String.format("%02d",useTime/3600) + ":"+String.format("%02d",(useTime/60)%60)+":"
                        +String.format("%02d",useTime%60));
                handler.postDelayed(this, 1000);
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mapButton2:
                Intent intent = new Intent(Running.this, map1.class);
                startActivity(intent);
                break;
            case R.id.pause1:
                pauseButton.setVisibility(View.INVISIBLE);
                continueButton.setVisibility(View.VISIBLE);
                stopButton.setVisibility(View.VISIBLE);
                doingText.setText("暂停");
                break;
            case R.id.continue1:
                pauseButton.setVisibility(View.VISIBLE);
                continueButton.setVisibility(View.INVISIBLE);
                stopButton.setVisibility(View.INVISIBLE);
                doingText.setText("跑步");
                break;
            default:
                History history = new History();
                history.setSpeed(speed);
                history.setType("run");
                history.setDistance(distance/1000);
                String allTime = String.format("%02d",addTime/3600) + ":"+String.format("%02d",(addTime/60)%60)+":"
                        +String.format("%02d",addTime%60);
                history.setAllTime(allTime);
                history.setStartTime(startTime);
                history.save();
                Toast.makeText(Running.this,"记录保存成功",Toast.LENGTH_SHORT).show();
                Running.this.finish();
        }
    }
}
