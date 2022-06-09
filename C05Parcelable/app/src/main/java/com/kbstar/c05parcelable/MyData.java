package com.kbstar.c05parcelable;

import android.os.Parcel;
import android.os.Parcelable;

// Parcelable interface 구현 : implements Parcelable -> Generate -> implement methods
public class MyData implements Parcelable {
    private String name;
    private int age;

    public MyData() {                       // Default Constructor
        this.name = "Kildong Hong";
        this.age = 11;
    }

    public MyData(String name, int age) {   // Copy Constructor (Generate -> constructor)
        this.name = name;
        this.age = age;
    }

    public MyData(Parcel src) {
        this.name = src.readString();
        this.age = src.readInt();
    }

    // Creator 상수 정의
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        public MyData createFromParcel(Parcel in) {
            return new MyData(in);
        }

        public MyData[] newArray(int size) {
            return new MyData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        // 데이터 직렬화, 이 순서가 중요!!
        parcel.writeString(name);
        parcel.writeInt(age);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
