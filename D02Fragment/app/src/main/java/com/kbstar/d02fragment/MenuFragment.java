package com.kbstar.d02fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class MenuFragment extends Fragment {

    Button btnMain;
    Button btnKb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_menu, container, false);

        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_menu, container, false);

        btnMain = view.findViewById(R.id.btnMain);
        btnKb = view.findViewById(R.id.btnKb);

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 MainActivity activity = (MainActivity) getActivity();
                 activity.changeFragment(0);
            }
        });

        btnKb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) getActivity();
                activity.changeFragment(2);
            }
        });

        return view;
    }
}