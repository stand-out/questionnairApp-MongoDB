package cn.LiTao.questionnaire.utils;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;

import java.io.IOException;

@Slf4j
public class SmsUtil {
    // 短信应用 SDK AppID
    private final static int appid = 1400165939; // SDK AppID 以1400开头
    // 短信应用 SDK AppKey
    private final static String appkey = "9d008cf8261218f091483c2a79bc7add";
    // 需要发送短信的手机号码
    // 短信模板 ID，需要在短信应用中申请
    private final static int templateId = 497323; // NOTE: 这里的模板 ID`7839`只是示例，真实的模板 ID 需要在短信控制台中申请
    // 签名
    private final static String smsSign = "个人作品展示"; // NOTE: 签名参数使用的是`签名内容`，而不是`签名ID`。这里的签名"腾讯云"只是示例，真实的签名需要在短信控制台申请


    public static boolean sendToPhoneNumber(String phoneNumber, String[] params) {
        try {
//            String[] params = {"5678"};
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumber, templateId, params, smsSign, "", "");
            log.info(result.toString());

            return true;

        } catch (HTTPException e) {
            // HTTP 响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // JSON 解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络 IO 错误
            e.printStackTrace();
        }

        return false;
    }
}
