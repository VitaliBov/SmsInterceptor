package bov.vitali.smsinterceptor.sample;

import android.text.Editable;
import android.text.TextWatcher;

public class SmsTextWatcher implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // unused method
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        // unused method
    }
}