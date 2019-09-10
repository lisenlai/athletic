package com.laidage.ican.TryButNotUse;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.laidage.ican.DataBase.History;
import com.laidage.ican.R;

import static java.lang.Thread.sleep;

public class Walkings extends AppCompatActivity {

    TextView kilo1;
    Button mapButton2;
    Button stop1, continue1, pause1;
    float distance;
    private MyReceiver receiver = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walking);
        kilo1 = findViewById(R.id.kilometers);
        mapButton2 = findViewById(R.id.mapButton2);
        continue1=findViewById(R.id.continue1);
        continue1.setVisibility(View.VISIBLE);
        pause1=findViewById(R.id.pause1);
        pause1.setVisibility(View.INVISIBLE);
        stop1=findViewById(R.id.stop1);
        stop1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                History history = new History();
                history.setType("walk");
                history.setDistance(10.0f);
                history.setSpeed(1.0f);
               // history.setStartTime(new Date());
                history.setAllTime("16:30");
                history.setKilocalorie(10);
                history.save();
            }
        });
       // stop1.setVisibility(View.VISIBLE);
        startService(new Intent(Walkings.this, Run.class));
        //注册广播接收器
        receiver=new MyReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction("com.laidage.ican.TryButNotUse.Kilo");
        Walkings.this.registerReceiver(receiver,filter);

    }
    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle=intent.getExtras();
            distance=bundle.getInt("distance");

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                   // Toast.makeText(Walking.this,String.valueOf(distance),Toast.LENGTH_SHORT).show();
                    kilo1.setText(distance+"");
                }
            });
        }
    }
    protected void onDestroy() {
        //结束服务
        //stopService(new Intent(Walking.this, Kilo.class));
        super.onDestroy();
    }
}
