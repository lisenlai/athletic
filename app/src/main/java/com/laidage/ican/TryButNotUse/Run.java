package com.laidage.ican.TryButNotUse;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.laidage.ican.R;

public class Run extends AppCompatActivity implements  AMapLocationListener {

    private Boolean firstLocation = true;
    private LatLng nowLL = null, lastLL = null;
    private float distance = 0;
    private AMapLocationClient aMapLocationClient;
    private AMapLocationClientOption aMapLocationClientOption;
    private TextView textView;
    private TextView textView2;
    private stepCount step;
    private  float stepC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);
        step = new stepCount();
        aMapLocationClient = new AMapLocationClient(getApplicationContext());
        aMapLocationClient.setLocationListener(this);
        textView = (TextView) findViewById(R.id.text1);
        textView2 = (TextView) findViewById(R.id.text2);
        //定位参数设置
        aMapLocationClientOption = new AMapLocationClientOption();
        aMapLocationClientOption.setInterval(10000);
     //   aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        aMapLocationClient.setLocationOption(aMapLocationClientOption);
        aMapLocationClient.startLocation();
    }
    @SuppressLint("SetTextI18n")
    @Override
     public void onLocationChanged(AMapLocation aMapLocation) {
            stepC = step.getStepCount();
            textView2.setText("跑了："+ stepC + "米");
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
                    lastLL = nowLL;
                    textView.setText(distance+"");
                }
            }
    }
}
