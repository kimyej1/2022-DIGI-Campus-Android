package com.kbstar.h02recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Person {

    private String name;
    private String mobile;
    private int image;

    // default Constructor
    public Person() {
        this("No Name", "010-1111-1111");
    }

    // copy Constructor
    public Person(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}