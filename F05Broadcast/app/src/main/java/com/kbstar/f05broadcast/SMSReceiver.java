package com.kbstar.f05broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class SMSReceiver extends BroadcastReceiver {

    private static final String TAG = "SMSReceiver";

    // 문자메시지를 받으면 자동으로 호출
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "-------------------------- onReceive()");   // Log.d - Debug

        Bundle bundle = intent.getExtras();
        SmsMessage[] messages = parseSmsMessage(bundle);

        if(messages != null && messages.length > 0) {
            String sender = messages[0].getOriginatingAddress();
            Log.i(TAG, "-------------------------- from : " + sender);  // Log.i - Info

            String msg = messages[0].getMessageBody();
            Log.i(TAG, "-------------------------- msg : " + msg);

            Date time = new Date(messages[0].getTimestampMillis());
            Log.i(TAG, "-------------------------- time : " + time);

            startSMSActivity(context,sender,msg,time);
        }
    }

    private void startSMSActivity(Context context, String sender, String msg, Date time) {
        // YYYY-MM-DD HH:MM:SS
        Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Intent intent = new Intent(context, SMSActivity.class);
        intent.putExtra("sender", sender);
        intent.putExtra("msg", msg);
        intent.putExtra("time", format.format(time));

        // intent의 FLAG 설정
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_SINGLE_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TOP);

        context.startActivity(intent);
    }

    private SmsMessage[] parseSmsMessage(Bundle bundle) {
        Object[] objs = (Object[]) bundle.get("pdus");
        SmsMessage[] msg = new SmsMessage[objs.length];

        int smsCount = objs.length;

        for(int i=0; i<smsCount; i++) {
            // 단말 OS버전에 따라 사용 메소드가 다름 (M = Marshmellow)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String format = bundle.getString("format");
                msg[i] = SmsMessage.createFromPdu((byte[]) objs[i], format);
            } else {
                msg[i] = SmsMessage.createFromPdu((byte[]) objs[i]);
            }
        }
        return msg;
    }
}