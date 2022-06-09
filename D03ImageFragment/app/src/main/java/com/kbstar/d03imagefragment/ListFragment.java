package com.kbstar.d03imagefragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ListFragment extends Fragment {

    private Button button1;
    private Button button2;
    private Button button3;

    // ◼︎ 방법 1. 함수 없이 그냥 main 통해서만 가게 해보자 -> D04에서 해봄!!
    private MainActivity mainActivity;

    // ◼︎ 방법 2. callback 함수 이용하기
    //      지금은 1이 쉽지만.. 액티비티가 많아지고 하면 1이 더 복잡할 수도 있음
    public static interface ImageSelectCallback {        // inner Class (inner Interface)

        // 나를 상속받는 녀석들은 이 함수를 꼭 새로 정의해서 써야해 -> Main Activity 가 상속받음
        public void changeImage(int index);
    }

    public ImageSelectCallback callback;

    @Override
    public void onAttach(@NonNull Context context) {    // Context : 핵심 정보
        super.onAttach(context);

        if(context instanceof ImageSelectCallback) {
            callback = (ImageSelectCallback) context;
        }
    }

    /*
        Callback 변수 자료형을 ImageSelectCallback 으로 선언한 이유
            : 선택한 것에 따라 다른 fragment 이미지를 변경하려고!

            Activity (contains Fragment1, Fragment2) 에게 데이터를 전달해야 한다.
            (Fragment 끼리 바로 줄 수 없고 부모인 Activity를 통해 전달!)
            -> Activity에 changeImage() 메소드를 정의한 후, changeImage() 호출
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_list, container, false);

        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_list, container, false);

        button1 = view.findViewById(R.id.button1);
        button2 = view.findViewById(R.id.button2);
        button3 = view.findViewById(R.id.button3);

        mainActivity = (MainActivity) getActivity();

        // ******* changeImage(index) index를 0~2로 하는 이유 : MainActivity에서 배열 사용하겠다! *******
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(callback != null) {  // 사실 없어도 되는 라인인데, 객체는 보통 널이 아닐때 조건을 주는 것이 좋음
                    callback.changeImage(0);
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(callback != null) {
                    callback.changeImage(1);
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(callback != null) {
                    callback.changeImage(2);
                }
            }
        });

        return view;
    }
}
