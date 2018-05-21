package bov.vitali.smsinterceptor;

import android.Manifest;
import android.app.Activity;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

public class SmsInterceptor {
    private static final int REQUEST_CODE = 100;
    private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";
    private SmsReceiver smsReceiver;
    private OnMessageListener listener;
    private Activity activity;
    private Fragment fragment;
    private String[] phoneNumbers;
    private String regex;

    public SmsInterceptor(Activity activity, OnMessageListener listener) {
        this.activity = activity;
        this.listener = listener;
    }

    public SmsInterceptor(Activity activity, Fragment fragment, OnMessageListener listener) {
        this.activity = activity;
        this.listener = listener;
        this.fragment = fragment;
    }

    public void register() {
        if (isStoragePermissionGranted(activity, fragment)) {
            setupReceiver();
        }
    }

    public void unregister() {
        activity.unregisterReceiver(smsReceiver);
    }

    public void setPhoneNumber(String number) {
        phoneNumbers = new String[]{number};
    }

    public void setPhoneNumbers(String[] numbers) {
        phoneNumbers = numbers;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            setupReceiver();
        }
    }

    private void setupReceiver() {
        smsReceiver = new SmsReceiver();
        smsReceiver.setPhoneNumbers(phoneNumbers);
        smsReceiver.setRegex(regex);
        SmsReceiver.bindListener(listener);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION);
        activity.registerReceiver(smsReceiver, intentFilter);
    }

    private boolean isStoragePermissionGranted(Activity activity, Fragment fragment) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                if (fragment == null) {
                    ActivityCompat.requestPermissions(activity, new String[]{
                            Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, REQUEST_CODE);
                } else {
                    fragment.requestPermissions(new String[]{
                            Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, REQUEST_CODE);
                }
                return false;
            }
        } else {
            return true;
        }
    }
}