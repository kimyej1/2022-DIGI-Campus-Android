package com.kbstar.g01web;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button button;
    private WebView webView, popView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        webView = findViewById(R.id.webView);
        popView = findViewById(R.id.webView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

//        webView.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
////                return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
//
//                // 팝업용 웹뷰 속성 설정 (나는 크롬쓰고있는데 엣지 띄워버리면 세션이 끊어지고 정보를 못주고받으니까.. 제어하려고)
//                popView = new WebView(view.getContext()); // 현재 웹브라우저 가져오기
//                popView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//                popView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//                popView.getSettings().setSupportMultipleWindows(true);
//
//                return true;
//            }
//        });

        webView.setWebViewClient(new ViewClient());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl(editText.getText().toString());
            }
        });

        webView.loadUrl("https://www.kbstar.com");  // 홈 페이지 고정
    }

    private class ViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) { // 새 창 띄지 말고 덮어써라
            view.loadUrl(url);
            return true;
        }
    }
}