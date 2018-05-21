package bov.vitali.smsinterceptor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class StringUtils {

    private StringUtils() {
    }

    static String getSmsCode(String input, String regex) {
        Matcher matcher = Pattern.compile(regex).matcher(input);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}