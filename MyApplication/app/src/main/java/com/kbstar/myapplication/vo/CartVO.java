package com.kbstar.myapplication.vo;

import android.os.Parcel;
import android.os.Parcelable;

public class CartVO implements Parcelable {
    private String imageId;
    private String name;
    private int price;
    private int cnt;

    public CartVO(String imageId, String name, int price, int cnt) {
        this.imageId = imageId;
        this.name = name;
        this.price =price;
        this.cnt = cnt;
    }

    protected CartVO(Parcel in) {
        imageId = in.readString();
        name = in.readString();
        price = in.readInt();
        cnt = in.readInt();
    }

    @Override
    public String toString() {
        return "CartVO{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", cnt=" + cnt +
                '}';
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public static final Creator<CartVO> CREATOR = new Creator<CartVO>() {
        @Override
        public CartVO createFromParcel(Parcel in) {
            return new CartVO(in);
        }

        @Override
        public CartVO[] newArray(int size) {
            return new CartVO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(imageId);
        parcel.writeString(name);
        parcel.writeInt(price);
        parcel.writeInt(cnt);
    }
}
