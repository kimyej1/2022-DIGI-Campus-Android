package com.kbstar.homework;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class Fragment2 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment1, container, false);

        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment2, container, false);

        // 필요하면, 버튼 객체를 이용해서 이벤트 처리
        return view;
    }
}