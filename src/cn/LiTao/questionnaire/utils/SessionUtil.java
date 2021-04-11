package cn.LiTao.questionnaire.utils;

import cn.LiTao.questionnaire.constant.ApplicationConstant;
import cn.LiTao.questionnaire.pojo.User;
import com.github.kevinsawicki.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Devil
 */
@Slf4j
public class SessionUtil {
    public static User getSessionUser(HttpServletRequest request) {
        final String accessToken = request.getParameter("access_token");
        log.info("get user token: {}", accessToken);
        if (StringUtils.isNotBlank(accessToken)) {
            final String userJson = JedisUtil.get(ApplicationConstant.REDIS_SESSION_KEY_PREFIX + accessToken);
            return JsonUtil.jsonToObject(userJson, User.class);
        }
        return null;
    }
}
