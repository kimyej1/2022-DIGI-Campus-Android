package com.kbstar.l01retrofit;

import com.google.gson.annotations.Expose;

public class ResLoginData {

    // Expose : Object에 해당하는 값이 null인 경우 -> JSON 필드를 자동 생략하겠다.
    @Expose
    String token;

    @Override
    public String toString() {
        return "ResLoginData{" +
                "token='" + token + '\'' +
                '}';
    }

    public String getToken() {
        return token;
    }
}
