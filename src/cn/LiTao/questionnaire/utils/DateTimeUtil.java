package cn.LiTao.questionnaire.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * @author Devil
 */
public class DateTimeUtil {
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";

    public static String getDateTimeString(Date date) {
        return Objects.isNull(date) ? "" : new SimpleDateFormat(DATETIME_FORMAT).format(date);
    }

    public static Date getDate(String dateTime) throws ParseException {
        return StringUtils.isBlank(dateTime) ? null : new SimpleDateFormat(DATETIME_FORMAT).parse(dateTime);
    }

}
