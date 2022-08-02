package com.kbstar.myapplication.vo;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductVO implements Parcelable {
    private String category, name, description, imageId;
    private int idx, price, cnt;

    public ProductVO(int idx, String category, String name, String description, String imageId, int price, int cnt) {
        this.idx = idx;
        this.category = category;
        this.name = name;
        this.description = description;
        this.imageId = imageId;
        this.price = price;
        this.cnt = cnt;
    }

    public ProductVO() {
    }

    protected ProductVO(Parcel in) {
        idx = in.readInt();
        category = in.readString();
        name = in.readString();
        description = in.readString();
        imageId = in.readString();
        price = in.readInt();
        cnt = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idx);
        dest.writeString(category);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(imageId);
        dest.writeInt(price);
        dest.writeInt(cnt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProductVO> CREATOR = new Creator<ProductVO>() {
        @Override
        public ProductVO createFromParcel(Parcel in) {
            return new ProductVO(in);
        }

        @Override
        public ProductVO[] newArray(int size) {
            return new ProductVO[size];
        }
    };

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ProductVO{" +
                "category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", idx=" + idx +
                ", imageId=" + imageId +
                ", price=" + price +
                ", cnt=" + cnt +
                '}';
    }
}
