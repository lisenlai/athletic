package com.laidage.ican.TryButNotUse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.laidage.ican.R;

public class MainActivity extends AppCompatActivity implements AMapLocationListener {

   //private TextView tvLocation;

    //定位服务类
    private AMapLocationClient locationClient = null;
    //定位参数类
    private AMapLocationClientOption locationClientOption = null;
    private AMap amap;
    private MapView mapview;
    private Button startButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
       startButton = (Button) findViewById(R.id.start);
        mapview = (MapView) findViewById(R.id.map);
        mapview.onCreate(savedInstanceState);
        //tvLocation = (TextView) findViewById(R.id.tv_location);
        amap = mapview.getMap();
        //初始化定位
        locationClient = new AMapLocationClient(getApplicationContext());
        locationClient.setLocationListener(this);
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        amap.setMyLocationStyle(myLocationStyle);
        //定位参数设置
        locationClientOption = new AMapLocationClientOption();
        locationClientOption.setInterval(100000);
        locationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        locationClient.setLocationOption(locationClientOption);
        locationClient.startLocation();
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent = new Intent(MainActivity.this, Run.class);
                Intent intent = new Intent(MainActivity.this, ChooseImage.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
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
