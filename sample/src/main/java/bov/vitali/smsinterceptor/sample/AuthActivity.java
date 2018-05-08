package bov.vitali.smsinterceptor.sample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import bov.vitali.smsinterceptor.OnMessageListener;
import bov.vitali.smsinterceptor.SmsInterceptor;

public class AuthActivity extends AppCompatActivity implements OnMessageListener {
    private static final String SMS_CODE_REGEX = "(\\d{4})";
    private static final String PHONE_NUMBER = "123456";
    private SmsInterceptor smsInterceptor;
    private EditText etAuthPassword;
    private ProgressBar pbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        initViews();
        setupSmsInterceptor();
    }

    @Override
    protected void onResume() {
        super.onResume();
        smsInterceptor.register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        smsInterceptor.unregister();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        smsInterceptor.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void messageReceived(String message) {
        // You can perform your validation here
        etAuthPassword.setText(message);
    }

    private void initViews() {
        pbAuth = findViewById(R.id.pbAuth);
        Button btnAuthPassword = findViewById(R.id.btnAuthPassword);
        btnAuthPassword.setOnClickListener(view -> checkCode());
        etAuthPassword = findViewById(R.id.etAuthPassword);
        etAuthPassword.addTextChangedListener(new SmsTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 4) {
                    btnAuthPassword.setEnabled(true);
                    checkCode();
                } else {
                    btnAuthPassword.setEnabled(false);
                }
            }
        });
    }

    private void setupSmsInterceptor() {
        smsInterceptor = new SmsInterceptor(this, this);
        // Not necessary
        smsInterceptor.setRegex(SMS_CODE_REGEX);
        smsInterceptor.setPhoneNumber(PHONE_NUMBER);
    }

    private void checkCode() {
        showLoading(true);
        new Handler().postDelayed(this::navigateToMainScreen, 1500);
    }

    private void showLoading(boolean visible) {
        pbAuth.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    private void navigateToMainScreen() {
        showLoading(false);
        startActivity(new Intent(this, MainActivity.class));
    }
}