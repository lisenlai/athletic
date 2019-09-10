package com.laidage.ican.TryButNotUse;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.laidage.ican.R;

public class Runs extends AppCompatActivity {

    private TabLayout tabLayout;

    private Button startButton;
    private ViewPager viewPager2;
    String[] tittle = {"跑步", "行走","骑行","我"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runs);
        tabLayout = findViewById(R.id.tabLayout);
        startButton = findViewById(R.id.startButton);
        View view = findViewById(R.id.line1);

         for(int i = 0; i < tittle.length; i++)
        {
            TabLayout.Tab tab =tabLayout.newTab();
            tab.setText(tittle[i]);
//            if(i == 0)
//                tab.setIcon(R.drawable.sport);
//            if(i == 1)
//                tab.setIcon(R.drawable.friend);
//            if(i == 2)
//                tab.setIcon(R.drawable.me);
            tabLayout.addTab(tab);
        }


    }
}
