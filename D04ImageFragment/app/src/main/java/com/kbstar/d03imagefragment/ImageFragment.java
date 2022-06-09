package com.kbstar.d03imagefragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageFragment extends Fragment {

    private ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_list, container, false);

        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_image, container, false);
        imageView = view.findViewById(R.id.imageView);

        return view;
    }

    public void setImage(int drawableResourceId) {
        imageView.setImageResource(drawableResourceId);
    }
}