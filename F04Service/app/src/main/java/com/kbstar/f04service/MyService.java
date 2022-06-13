package com.kbstar.f04service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private static final String TAG = "MyService";

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "------------------ onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "------------------ onStartCommand()");

        if(intent == null)
        {
            return Service.START_STICKY;
            /*
            Service가 강제 종료되었을 때
                시스템이 다시 Service를 재시작
                intent == null 초기화해서 재시작한다.
            START_NOT_STICKY
                재시작하지 않는다.
             */
        }else
        {
            processCountDown(intent);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void processCountDown(Intent intent)
    {
        String name = intent.getStringExtra("name");
        String msg = intent.getStringExtra("msg");

        Log.d(TAG, "-------------------- name : " + name);
        Log.d(TAG, "-------------------- msg : " + msg);

        for(int i=10; i>=0; i--)
        {
            try {
                Thread.sleep(1000);
            }catch(Exception e)
            {

            }
            Log.d(TAG, "------------------------- count : " + i);
        }

        // MainActivity한테 알려주기..
        Intent displayIntent = new Intent(
                getApplicationContext(),
                MainActivity.class
        );
        // intent flag
        displayIntent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_SINGLE_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TOP
        );
        displayIntent.putExtra("cmd", "display");
        displayIntent.putExtra("msg", "from MyService");

        startActivity(displayIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}