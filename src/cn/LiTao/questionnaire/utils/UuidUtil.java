package cn.LiTao.questionnaire.utils;

import java.util.UUID;

/**
 * @author Devil
 */
public class UuidUtil {

    public static String getUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
