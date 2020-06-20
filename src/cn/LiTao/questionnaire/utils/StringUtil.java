package cn.LiTao.questionnaire.utils;

import java.util.Date;
import java.util.Random;

public class StringUtil {

    public static String upperCase(String str) {
        if ((str == null) || (str.length() == 0))
            return str;
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    public static String getCaptcha(int size) {
        Random random = new Random();

        StringBuilder captcha = new StringBuilder();
        for (int i = 0; i < size; i++) {
            captcha.append(random.nextInt(10));
        }

        return captcha.toString();
    }

    public static String getShortUuid() {
        Date date = new Date();
        long time = date.getTime();
        return Long.toString(time, 36);
    }

}
