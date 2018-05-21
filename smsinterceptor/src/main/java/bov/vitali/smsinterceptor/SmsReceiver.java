package bov.vitali.smsinterceptor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.util.Arrays;

class SmsReceiver extends BroadcastReceiver {
    private static OnMessageListener onMessageListener;
    private String[] phoneNumbers;
    private String regex;

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        if (bundle != null) {
            final Object[] smsExtras = (Object[]) bundle.get("pdus");
            if (smsExtras != null) {
                for (Object pdu : smsExtras) {
                    SmsMessage sms = getMessage(pdu, bundle);
                    String number = sms.getDisplayOriginatingAddress();
                    if (phoneNumbers != null && !Arrays.asList(phoneNumbers).contains(number)) {
                        return;
                    }
                    String message = sms.getDisplayMessageBody();
                    if (regex != null) {
                        message = StringUtils.getSmsCode(message, regex);
                    }
                    if (onMessageListener != null) {
                        onMessageListener.messageReceived(message);
                    }
                }
            }
        }
    }

    static void bindListener(OnMessageListener listener) {
        onMessageListener = listener;
    }

    void setPhoneNumbers(String[] numbers) {
        phoneNumbers = numbers;
    }

    void setRegex(String regex) {
        this.regex = regex;
    }

    private SmsMessage getMessage(Object pdu, Bundle bundle) {
        SmsMessage sms;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String format = bundle.getString("format");
            sms = SmsMessage.createFromPdu((byte[]) pdu, format);
        } else {
            sms = SmsMessage.createFromPdu((byte[]) pdu);
        }
        return sms;
    }
}