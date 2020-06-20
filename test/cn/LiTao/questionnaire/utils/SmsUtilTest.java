package cn.LiTao.questionnaire.utils;

import org.junit.Test;

import java.util.Date;

public class SmsUtilTest {

    @Test
    public void test() {

        SmsUtil.sendToPhoneNumber("18674668622", new String[]{StringUtil.getCaptcha(5)});
//        System.out.println();
    }

    @Test
    public void justTest() {
        Date date = new Date();
        long time = date.getTime();
        String s = Long.toString(time, 36);
        System.out.println(s);
    }
}
