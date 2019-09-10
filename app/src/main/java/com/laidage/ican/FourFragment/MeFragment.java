package com.laidage.ican.FourFragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.laidage.ican.Login;
import com.laidage.ican.R;
import com.laidage.ican.ThreeHistory.Bicycle_History;
import com.laidage.ican.ThreeHistory.Run_History;
import com.laidage.ican.ThreeHistory.Walk_History;

public class MeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me_fragment,container,false);
        return view;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] history = {"跑步历史记录","行走历史记录","骑行历史记录"};

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,history);//新建适配器
        ListView listView = getActivity().findViewById(R.id.listView);
        listView.setAdapter(adapter);//显示三个运动记录
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: Intent intent =new Intent(getActivity(), Run_History.class);
                        startActivity(intent);//跑步历史记录
                        break;
                    case 1:Intent intent2 =new Intent(getActivity(), Walk_History.class);
                        startActivity(intent2);//行走历史记录
                        break;
                    default:
                        Intent intent3 =new Intent(getActivity(), Bicycle_History.class);
                        startActivity(intent3);//骑行历史记录
                }
            }
        });
        Button unLoginButton = getActivity().findViewById(R.id.unLogin1);
        unLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sprfMain;
                SharedPreferences.Editor editorMain;
                sprfMain= PreferenceManager.getDefaultSharedPreferences(getActivity());
                editorMain=sprfMain.edit();
                editorMain.putBoolean("isLogin",false);
                editorMain.apply();
                Intent intent4 = new Intent(getActivity(), Login.class);
                startActivity(intent4);
                getActivity().finish();
            }
        });
    }
     public static MeFragment newInstance() {
        Bundle args = new Bundle();
        MeFragment fragment = new MeFragment();
        fragment.setArguments(args);
        return fragment;
    }


}