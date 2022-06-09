package com.kbstar.d02fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/*
    Fragment
        화면을 분할해서, 화면 전환 안하고 그 부분만 바꿔줄 수 있게 만듬
 */
public class MainFragment extends Fragment {

    Button btnMenu;
    Button btnKb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_main, container, false);

        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);
        /*
            위 줄이랑 똑같은 말임.. 그냥 바로 리턴하냐, 뷰로 만들어서 변수 잡아서 리턴하냐 차이
            view 속에 버튼 등 프로그래밍 좀 해서 리턴해주려고 변수로 잠깐 잡아줬음!
         */

        btnMenu = view.findViewById(R.id.btnMenu);
        btnKb = view.findViewById(R.id.btnKb);
        /*
            setContentView(R.layout.activity_main); 가 없어서, 바로 findView.. 하면 안나옴
            view.findView.. 해야 view 안에서 찾아올 수 있음!
         */

        // fragment_main 에 있는 button 이벤트 처리
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) getActivity();

                // Main Activity Fragment 변환하는 Method 만들기
                activity.changeFragment(1);
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