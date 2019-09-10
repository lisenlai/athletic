package com.laidage.ican.FourFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.laidage.ican.Doing.Bicycling;
import com.laidage.ican.R;
import com.laidage.ican.TryButNotUse.Run;
import com.laidage.ican.map1;

public class BicycleFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bicycle_fragment,container,false);
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button bicycleButton = getActivity().findViewById(R.id.bicycleButton);
        bicycleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Bicycling.class);
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

    public static BicycleFragment newInstance() {
        Bundle args = new Bundle();
        BicycleFragment fragment = new BicycleFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
