package cn.LiTao.questionnaire.controller.impl;

import cn.LiTao.questionnaire.controller.MyServlet;
import cn.LiTao.questionnaire.pojo.ResponseBean;
import cn.LiTao.questionnaire.pojo.User;
import cn.LiTao.questionnaire.service.impl.UserService;
import cn.LiTao.questionnaire.utils.JsonUtil;
import cn.LiTao.questionnaire.utils.SmsUtil;
import cn.LiTao.questionnaire.utils.StringUtil;
import cn.LiTao.questionnaire.utils.WxApiUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * @author Devil
 */
@Slf4j
@WebServlet("/api/user/*")
public class UserController extends MyServlet {

    private static final long serialVersionUID = 6452448437960211374L;
    private UserService userService;

    public UserController() throws IOException {
        this.userService = new UserService();
    }

    public void postWxLogin(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, IOException {
        final String code = request.getParameter("code");
        final String encryptedData = request.getParameter("encryptedData");
        final String iv = request.getParameter("iv");

        ResponseBean<Object> responseBean = new ResponseBean<>();

        final Map<String, String> responseMap = WxApiUtil.checkCode(code);
        if (responseMap != null && !responseMap.containsKey(WxApiUtil.WX_API_ERROR_CODE_KEY)) {
            final String sessionKey = responseMap.get(WxApiUtil.WX_API_SESSION_KEY);
            final String openId = responseMap.get(WxApiUtil.WX_API_OPEN_ID_KEY);

            final String token = userService.wxLogin(sessionKey, openId, encryptedData, iv);
            if (StringUtils.isNoneBlank(token)) {
                responseBean.setCode(0);
                responseBean.setData(token);
            } else {
                responseBean.setCode(2);
                responseBean.setMsg("登录失败");
            }
        } else {
            log.info("微信用户code码2session失败 code:{}, response:{}", code, responseMap);
            responseBean.setCode(2);
            responseBean.setMsg("code error");
        }
        response.getWriter().write(responseBean.toJson());
    }

//    用户登录
    public void postLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {

//        获取并封装参数
        String phoneNumber = request.getParameter("phoneNumber");
        String password = request.getParameter("password");
        User user = User.builder().password(password).phoneNumber(phoneNumber).build();

//        响应bean对象
        ResponseBean responseBean = new ResponseBean();

//        调用service处理业务
        User loginUser = userService.userLogin(user);
        if (loginUser != null) {
            responseBean.setCode(0);
            responseBean.setMsg("登录成功");

//            登陆成功后把数据库查询到的user数据写入session
            HttpSession session = request.getSession();
            session.setAttribute("user", loginUser);
        } else {
            responseBean.setCode(1);
            responseBean.setMsg("密码错误");
        }

        response.getWriter().write(responseBean.toJson());
    }

//    发送验证码请求
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        String phoneNumber = request.getParameter("phoneNumber");

//        生成随机验证码
        String captcha = StringUtil.getCaptcha(6);
//        响应对象
        ResponseBean responseBean = new ResponseBean();
//        验证码写入session
        session.setAttribute("captcha", captcha);
        log.info("生成验证码：" + captcha);

//        发送短信
        if (SmsUtil.sendToPhoneNumber(phoneNumber, new String[]{captcha, })) {
//        if (true) {
            responseBean.setCode(0);
            responseBean.setMsg("ok");
        } else {
            responseBean.setCode(1);
            responseBean.setMsg("Sms send error...");
        }

//        设置返回内容类型为json
        response.setContentType(session.getServletContext().getMimeType(".json") + ";charset=utf-8");
        log.info(JsonUtil.objectToString(responseBean));
        log.info(session.getServletContext().getMimeType(".json"));

//        把响应对象写入response
        response.getWriter().write(JsonUtil.objectToString(responseBean));
    }

//    用户注册
    public void postRegister(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String phoneNumber = request.getParameter("phoneNumber");
        String captcha = request.getParameter("captcha");
        String password = request.getParameter("password");

        ResponseBean responseBean = new ResponseBean();

//        从session中获取验证码
        HttpSession session = request.getSession();
        String sessionCaptcha = (String)session.getAttribute("captcha");

        log.info(phoneNumber + " " + password + " " + captcha);

//        session里验证码不为空
        if (sessionCaptcha != null) {
            if (sessionCaptcha.equals(captcha)) {
//                把表单信息装入bean对象
                User user = new User();
                user.setPhoneNumber(phoneNumber);
                user.setPassword(password);

//                随机生成用户名
                user.setUsername("新用户" + UUID.randomUUID().toString().replace("-", ""));

//                调用service注册
                userService.userRegister(user);

                responseBean.setCode(0);
            } else {
                responseBean.setCode(2);
                responseBean.setMsg("验证码错误");
            }
        } else {
            responseBean.setCode(1);
            responseBean.setMsg("请先获取验证码");
        }

        response.getWriter().write(responseBean.toJson());
    }

    public void getUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        ResponseBean<User> responseBean = new ResponseBean<>();

        if (user != null) {
            User resultUser = userService.findUserById(user.getId());
            responseBean.setCode(0);
            responseBean.setData(resultUser);
        } else {
            responseBean.setCode(1);
            responseBean.setMsg("请先登录");
        }

        response.getWriter().write(responseBean.toJson());
    }

    public void getLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseBean responseBean = new ResponseBean();

        responseBean.setCode(0);
        HttpSession session = request.getSession();
        session.removeAttribute("user");

        response.getWriter().write(responseBean.toJson());
    }


}
