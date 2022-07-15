package com.kbstar.test5_fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

// Fcm token 또는 메시지를 전달받기 위한 서비스
class MyService : FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        // 앱의 식별자 토큰을 전달하기 위해 자동으로 호출 (매개변수가 식별자)
        // 원래는.. 서버에서 사용해야 하는 토큰 -> 서버에 넘겨서 서버side DB에 저장되게 해야 함
        Log.d("", "fcm token:$p0")  // 테스트니까 로그만 찍는다.
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        // 서버 메시지가 fcm 서버로부터 전달된 경우 (매개변수가 메시지)
        // 원래는 메시지를 받아서 대부분 notification 띄우는..
        Log.d("", "fcm message:${p0.data}")

        // notification은 시스템 영역인 status bar에 우리 정보를 띄워야 하므로
        // 직접 띄울 순 없고, 시스템에 의뢰한다( : notification manager)
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val builder: NotificationCompat.Builder

        // notification은 직접 생성 안되고, builder로 생성 (버전 26부터 빌더에 '채널' 개념이 도입됨)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {    // version O = 26버전

            // 원래는 채널 등록할 때, 앱 실행하자마자 제일 먼저 실행되는 부분에 만들어놓고
            // 여기저기서 아이디로 가져다가 사용할 수 있게 만들어야함
            val channel = NotificationChannel(
                "oneChannel",   // 채널 식별자
                "oneName",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "description"

            // 만들어진 채널을 매니저에 등록
            manager.createNotificationChannel(channel)

            // 매니저에 등록된 채널을 하나 이용해서 빌더 만듬
            builder = NotificationCompat.Builder(this, "oneChannel")
        } else {
            builder = NotificationCompat.Builder(this)
        }

        builder.run{
            // 화면에 뜨는 정보 (Notification 객체로 표현)
            setSmallIcon(android.R.drawable.ic_notification_overlay)
            setWhen(System.currentTimeMillis())
            setContentTitle("fcm")
            setContentText("${p0.data}")  // 실제 메시지
        }
        // 노티 발생
        manager.notify(11, builder.build())
        // 11: 유저가 노티를 캔슬할 수 있을 땐 별 의미 없는데, 나중에 cancel 개념을 쓰게되면 식별자가 필요함
    }
}