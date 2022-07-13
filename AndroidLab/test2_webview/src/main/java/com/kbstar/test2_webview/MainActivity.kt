package com.kbstar.test2_webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Toast
import com.kbstar.test2_webview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // js에게 공개하기 위한 클래스
    class JavascriptTest {

        // 이 클래스의 객체가 js에게 공개되었다고 하더라도
        // 아래 어노테이션이 추가된 함수만 호출 가능하다.
        @get:JavascriptInterface
        val webData: String
            get() {
                val sb = StringBuffer()
                sb.append("[")
                for(i in 0..9) {
                    sb.append("[$i,")
                    sb.append("${(0..100).random()}")
                    sb.append("]")
                    if(i < 9)
                        sb.append(",")
                }
                sb.append("]")
                return sb.toString()
            }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // webview의 js engine은 기본으로 Disabled..
        binding.webView.settings.run {
            javaScriptEnabled = true    // enabled!
        }

        binding.webView.run {

            // js에게 객체 공개..
            // android 라는 단어는 개발자 임의의 단어, 공개한 객체의 js 이름
            addJavascriptInterface(JavascriptTest(), "android")

            // 초기 html 로딩..
            loadUrl("file:///android_asset/test.html")

            // browser 자체 이벤트 등록
            webChromeClient = object : WebChromeClient() {
                override fun onJsAlert(
                    view: WebView?,
                    url: String?,
                    message: String?,
                    result: JsResult?
                ): Boolean {
                    // js alert의 문자열을 toast로 띄우고, js alert 닫기
                    Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
                    result?.confirm()
                    return true
                }
            }

            binding.webButton.setOnClickListener {
                // js 함수 호출
                binding.webView.loadUrl("javascript:jsFunction('hello')")
            }
        }
    }
}