package cn.LiTao.questionnaire.controller;

import cn.LiTao.questionnaire.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public abstract class MyServlet extends HttpServlet {

    private static final long serialVersionUID = -7921271250054027915L;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.service(req, resp);
        Method method = getMethod(req);

        if (method != null) {
            try {
                method.invoke(this, req, resp);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            resp.setStatus(404);
        }
    }

//    通过反射获取到uri对应的方法
    private Method getMethod(HttpServletRequest req) {
        //        获取到除servlet后的uri
        String method = req.getMethod();
        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String servletPath = uri.substring(contextPath.length() + req.getServletPath().length());

//        转换成方法名
        String[] split = servletPath.split("/");
        StringBuilder sb = new StringBuilder();
        sb.append(method.toLowerCase());
        for (String str: split) {
            sb.append(StringUtil.upperCase(str));
        }

//        通过方法名反射取得对应方法 再return
        try {
            return this.getClass().getMethod(sb.toString(), HttpServletRequest.class, HttpServletResponse.class);
        } catch (NoSuchMethodException e) {
            log.info("没有对应的方法: " + sb.toString());
        }
        return null;
    }


}
