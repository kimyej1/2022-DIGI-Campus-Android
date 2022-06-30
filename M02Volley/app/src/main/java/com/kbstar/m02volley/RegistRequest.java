package com.kbstar.m02volley;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegistRequest extends StringRequest {
    /*
        Static 안쓰면 super(method, URL...)에서 오류나는데,
        생성자가 부모를 먼저 만들고 자식을 나중에 만들기 때문임!
        -> 부모에서는 URL이 뭔지 모름..
        -> static을 쓰면 static 메모리 먼저 만듬!
     */
    private static String URL = "http://10.10.223.89/android/insert.php";
    private Map<String, String> map;

    public RegistRequest(String id,
                         String name,
                         String pass,
                         int method,
                         Response.Listener<String> listener,
                         @Nullable Response.ErrorListener errorListener) {
        super(method, URL, listener, errorListener);

        map = new HashMap<>();
        map.put("id", id);
        map.put("name", name);
        map.put("pass", pass);
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        /*
            postParam = "name="+name+"&id="+id+"&pass="+pass;
         */
        return map;
    }
}
