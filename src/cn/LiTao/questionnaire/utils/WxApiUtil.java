package cn.LiTao.questionnaire.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.kevinsawicki.http.HttpRequest;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Devil
 */
public class WxApiUtil {
    private static final String APPID = "wxaf683c62840d643d";
    private static final String SECRET = "733e68a79912e428fc5491a498927d26";
    private static final String ACCESS_TOKEN_REDIS_KEY = "QUESTIONNAIRE_WX_ACCESS_TOKEN";
    private static final int REDIS_CACHE_EXPIRE = 60 * 60 * 2 - 60;

    public static final String WX_API_ERROR_CODE_KEY = "errcode";
    public static final String WX_API_SESSION_KEY = "session_key";
    public static final String WX_API_OPEN_ID_KEY = "openid";

    private static Map<String, String> basicDataMap;
    private static final String WX_CODE2SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session";
    private static final String WX_GRANT_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";

    static {
        Map<String, String> dataMap = new HashMap<>(2);
        dataMap.put("appid", APPID);
        dataMap.put("secret", SECRET);

        basicDataMap = Collections.unmodifiableMap(dataMap);
    }

    public static String getAccessToken() throws IllegalAccessException {
        String accessToken = JedisUtil.get(ACCESS_TOKEN_REDIS_KEY);

        if (StringUtils.isBlank(accessToken)) {
            accessToken = grantAccessToken();

            JedisUtil.setex(ACCESS_TOKEN_REDIS_KEY, accessToken, REDIS_CACHE_EXPIRE);
        }
        return accessToken;
    }

    private static String grantAccessToken() throws IllegalAccessException {
        Map<String, String> dataMap = new HashMap<>(basicDataMap);
        dataMap.put("grant_type", "client_credential");

        final HttpRequest httpRequest = HttpRequest.post(WX_GRANT_ACCESS_TOKEN_URL).form(dataMap);

        final Map<String, String> responseMap = sendRequest(httpRequest);
        if (responseMap.containsKey(WX_API_ERROR_CODE_KEY)) {
            throw new IllegalAccessException(responseMap.get("errmsg"));
        }
        return responseMap.get("");
    }

    public static Map<String, String> checkCode(String code) throws IllegalAccessException {
        Map<String, String> dataMap = new HashMap<>(basicDataMap);
        dataMap.put("grant_type", "authorization_code");
        dataMap.put("js_code", code);

        final HttpRequest httpRequest = HttpRequest.post(WX_CODE2SESSION_URL).form(dataMap);
        return sendRequest(httpRequest);
    }

    private static Map<String, String> sendRequest(HttpRequest httpRequest) throws IllegalAccessException {
        if (httpRequest.ok()) {
            final String responseBody = httpRequest.body();
            return JsonUtil.jsonToObject(responseBody, new TypeReference<Map<String, String>>() {});
        } else {
            throw new IllegalAccessException("访问微信API异常");
        }
    }

    public static JsonNode getUserInfo(String encryptedData, String sessionKey, String iv) {
        byte [] rawDataBytes = Base64.decode(encryptedData);
        byte [] aesKeyBytes = Base64.decode(sessionKey);
        byte[] ivBytes = Base64.decode(iv);

        return AseUtil.decrypt(rawDataBytes, aesKeyBytes, ivBytes);
    }

//    public static void main(String[] args) throws Exception {
////        String code = "013iNA1w3dQxKV2ubF3w3pbN5h0iNA1V";
////        final Map<String, String> stringStringMap = checkCode(code);
////        System.out.println(stringStringMap);
//
//        getUserInfo();
////        {session_key=E1d/6+jfkGjK0/m7pwuLJw==, openid=oBqax4hKtGwqu5r8AZ2JBSTgQbuA}
//    }

}
