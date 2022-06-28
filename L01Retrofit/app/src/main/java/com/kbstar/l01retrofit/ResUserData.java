package com.kbstar.l01retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/*
    user 목록에 대한 response data
    /api/user?page=2 이런식으로 요청했을 때의 응답 데이터
 */
public class ResUserData {

    @Expose
    private int page;
    @Expose
    private int total;

    @Expose
    @SerializedName("per_page") // 자바라서 perPage라고 쓰지만, 실제 JSON 데이터 만들때는 per_page 라고 네이밍
    private int perPage;

    @Expose
    @SerializedName("total_pages")
    private int totalPage;

    @Expose
    List<Data> data;
    @Expose
    Support support;

    @Override
    public String toString() {
        return "ResUserData{" +
                "page=" + page +
                ", total=" + total +
                ", perPage=" + perPage +
                ", totalPage=" + totalPage +
                ", data=" + data +
                ", support=" + support +
                '}';
    }

    public class Data {
        int id;
        String email;
        String avatar;

        @Expose
        @SerializedName("first_name")
        String firstName;

        @Expose
        @SerializedName("last_name")
        String lastName;

        @Override
        public String toString() {
            return "Data{" +
                    "id=" + id +
                    ", email='" + email + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    '}';
        }
    }

    public class Support {
        String url;
        String text;

        @Override
        public String toString() {
            return "Support{" +
                    "url='" + url + '\'' +
                    ", text='" + text + '\'' +
                    '}';
        }
    }
}
