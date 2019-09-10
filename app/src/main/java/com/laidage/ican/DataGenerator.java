package com.laidage.ican;

import android.support.v4.app.Fragment;

import com.laidage.ican.FourFragment.BicycleFragment;
import com.laidage.ican.FourFragment.MeFragment;
import com.laidage.ican.FourFragment.RunFragment;
import com.laidage.ican.FourFragment.WalkFragment;

public class DataGenerator {
//主界面的数据
    public static final int []mTabRes = new int[]{R.drawable.main_run2,R.drawable.main_walk2,
            R.drawable.main_cycle2,R.drawable.me2};
    public static final int []mTabResPressed = new int[]{R.drawable.main_run1,R.drawable.main_walk1,
            R.drawable.main_cycle1,R.drawable.me};
    public static final String []mTabTitle = new String[]{"跑步","行走","骑行","我"};

    public static Fragment[] getFragments(){
        Fragment fragments[] = new Fragment[4];
        fragments[0] = RunFragment.newInstance();
        fragments[1] = WalkFragment.newInstance();
        fragments[2] = BicycleFragment.newInstance();
        fragments[3] = MeFragment.newInstance();
        return fragments;
    }
}
