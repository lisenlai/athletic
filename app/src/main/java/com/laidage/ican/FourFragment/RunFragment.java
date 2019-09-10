package com.laidage.ican.FourFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.laidage.ican.Doing.Running;
import com.laidage.ican.R;
import com.laidage.ican.map1;

public class RunFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.run_fragment,container,false);
        return view;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button runButton = getActivity().findViewById(R.id.runButton);
        runButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Running.class);
                startActivity(intent);
            }
        });
        Button mapButton = getActivity().findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), map1.class);
                startActivity(intent);
            }
        });
    }
    public static RunFragment newInstance() {
        Bundle args = new Bundle();
        RunFragment fragment = new RunFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
