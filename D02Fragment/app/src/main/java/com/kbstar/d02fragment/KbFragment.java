package com.kbstar.d02fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class KbFragment extends Fragment {

    Button btnMain;
    Button btnMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_menu, container, false);

        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_kb, container, false);

        btnMain = view.findViewById(R.id.btnMain);
        btnMenu = view.findViewById(R.id.btnMenu);

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 MainActivity activity = (MainActivity) getActivity();
                 activity.changeFragment(0);
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) getActivity();
                activity.changeFragment(1);
            }
        });

        return view;
    }
}