package cn.LiTao.questionnaire.utils;

import cn.LiTao.questionnaire.pojo.User;
import cn.LiTao.questionnaire.pojo.WxUser;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author Devil
 */
public class ObjectConversion {

    public static WxUser userInfoToWxUser(JsonNode userInfo) {
        return WxUser.builder()
                .openId(userInfo.get("openId").asText())
                .nickName(userInfo.get("nickName").asText())
                .gender(userInfo.get("gender").asInt())
                .language(userInfo.get("language").asText())
                .city(userInfo.get("city").asText())
                .province(userInfo.get("province").asText())
                .country(userInfo.get("country").asText())
                .avatarUrl(userInfo.get("avatarUrl").asText())
                .build();

    }

    public static User wxUserToUser(WxUser user) {
        return User.builder()
                .username(user.getNickName())
                .headerImagePath(user.getAvatarUrl())
                .wxOpenId(user.getOpenId())
                .build();
    }
}
