package com.laidage.ican.TryButNotUse;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;


import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;

public class Kilo extends Service implements AMapLocationListener {
    private Boolean firstLocation = true;
     LatLng nowLL = null, lastLL = null;
    float distance = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        AMapLocationClient aMapLocationClient = new AMapLocationClient(getApplicationContext());
        aMapLocationClient.setLocationListener(Kilo.this);

        //定位参数设置
        AMapLocationClientOption aMapLocationClientOption = new AMapLocationClientOption();
        aMapLocationClientOption.setInterval(10000);
     //   aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        aMapLocationClient.setLocationOption(aMapLocationClientOption);
        aMapLocationClient.startLocation();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

        if(firstLocation) {
            if(aMapLocation != null && aMapLocation.getLongitude() !=0 ) {
                firstLocation = false;

                lastLL = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
            }
        }
        else
        {
            if(aMapLocation != null && aMapLocation.getLongitude() !=0 )
            {

                nowLL = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                double dd = aMapLocation.getLatitude();
                Toast.makeText(Kilo.this,dd+"",Toast.LENGTH_SHORT).show();
                distance += AMapUtils.calculateLineDistance(nowLL, lastLL);
                lastLL = nowLL;
                Intent intent = new Intent();
                intent.putExtra("distance",distance);
                intent.setAction("com.laidage.ican.TryButNotUse.Kilo");

                sendBroadcast(intent);
            }
        }
    }
}
