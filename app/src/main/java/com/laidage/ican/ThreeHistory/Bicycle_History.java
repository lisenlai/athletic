package com.laidage.ican.ThreeHistory;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.laidage.ican.DataBase.History;
import com.laidage.ican.R;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class Bicycle_History extends AppCompatActivity {
//历史记录
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bicycle__history);
        List<History> historyData = DataSupport.where("type = ?", "bicycle").find(History.class);
        //找到类型为bicycle的数据，放到historyData中
        List<String> history = new ArrayList<>();
        for(History history_data:historyData) {
            @SuppressLint("DefaultLocale")
            String alla = history_data.getStartTime() + "\n"+String.format("%.2f",history_data.getDistance())+"km      "+history_data.getAllTime();
            history.add(alla);//把数据添加到history里面
        }
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(Bicycle_History.this,android.R.layout.simple_list_item_1,history);//新建适配器
        ListView listView = findViewById(R.id.listView);//通过适配器把数据传到listview
        listView.setAdapter(adapter);//通过listview把历史记录显示出来
    }
}
