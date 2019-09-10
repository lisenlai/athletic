package com.laidage.ican;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;

public class map1 extends AppCompatActivity implements AMapLocationListener {
//显示地图
    private AMapLocationClient locationClient = null;
    //定位参数类
    private AMapLocationClientOption locationClientOption = null;
    private AMap amap;
    private MapView mapview;
    MyLocationStyle myLocationStyle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map1);
        mapview = findViewById(R.id.map);
        mapview.onCreate(savedInstanceState);
        //tvLocation = (TextView) findViewById(R.id.tv_location);
        amap = mapview.getMap();
        //初始化定位
        locationClient = new AMapLocationClient(getApplicationContext());
        locationClient.setLocationListener(this);

        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        myLocationStyle.interval(2000);//设置连续定位模下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.showMyLocation(true);
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.local));

        amap.setMyLocationStyle(myLocationStyle);
        //定位参数设置
        locationClientOption = new AMapLocationClientOption();
        locationClientOption.setInterval(2000);
        locationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        locationClient.setLocationOption(locationClientOption);
        locationClient.startLocation();
        LatLng cc =new LatLng(22.528407,113.941956);
        amap.moveCamera(CameraUpdateFactory.newLatLngZoom(cc,16));
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null && aMapLocation.getLatitude()!=0) {
            LatLng ll = new LatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude());
            amap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 16));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationClient != null) {
            locationClient.onDestroy();
            locationClient = null;
            locationClientOption = null;
        }
    }
}
