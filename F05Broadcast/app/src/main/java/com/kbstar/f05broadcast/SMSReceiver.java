package com.kbstar.f05broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSReceiver extends BroadcastReceiver {

    private static final String TAG = "SMSReceiver";

    // 문자메시지를 받으면 자동으로 호출
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "-------------------------- onReceive()");

        Bundle bundle = intent.getExtras();
        SmsMessage[] messages = parseSmsMessage(bundle);
    }

    private SmsMessage[] parseSmsMessage(Bundle bundle) {
        Object[] objs = (Object[]) bundle.get("pdus");
        SmsMessage[] msg = new SmsMessage[objs.length];

        return msg;
    }
}