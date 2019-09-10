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

public class Bicycling extends AppCompatActivity implements AMapLocationListener, View.OnClickListener{
//骑行中
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
        initView();//界面和组件建立关联
        startLocation();//定位并获取距离
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日 HH:mm:ss ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        startTime = formatter.format(curDate);
        getTime();//计算时间
    }

    public void initView() {
        //界面和组件建立关联
        doingText = findViewById(R.id.doing);
        doingText.setText("骑行中");
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
        aMapLocationClientOption.setInterval(5000);
          aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        aMapLocationClient.setLocationOption(aMapLocationClientOption);
        aMapLocationClient.startLocation();
    }
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        //随着位置变化计算距离，速度，千卡
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
        //添加按钮点击响应
        switch (v.getId()) {
            case R.id.mapButton2:
                Intent intent = new Intent(Bicycling.this, map1.class);
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
                doingText.setText("骑行中");
                break;
            default:
                History history = new History();
                history.setSpeed(speed);
                history.setType("bicycle");
                history.setDistance(distance/1000);
                String allTime = String.format("%02d",addTime/3600) + ":"+String.format("%02d",(addTime/60)%60)+":"
                        +String.format("%02d",addTime%60);
                history.setAllTime(allTime);
                history.setStartTime(startTime);
                history.save();
                Toast.makeText(Bicycling.this,"记录保存成功",Toast.LENGTH_SHORT).show();
                Bicycling.this.finish();
        }
    }
}
