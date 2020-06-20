package cn.LiTao.questionnaire.controller.impl;

import cn.LiTao.questionnaire.controller.MyServlet;
import cn.LiTao.questionnaire.pojo.AnswerData;
import cn.LiTao.questionnaire.pojo.AnswerUser;
import cn.LiTao.questionnaire.pojo.ResponseBean;
import cn.LiTao.questionnaire.service.impl.AnswerUserService;
import cn.LiTao.questionnaire.utils.StringUtil;
import eu.bitwalker.useragentutils.UserAgent;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

@WebServlet("/api/answer/*")
public class AnswerController extends MyServlet {
    private static final long serialVersionUID = -8638001748157464459L;

    private AnswerUserService answerUserService;

    public AnswerController() {
        this.answerUserService = new AnswerUserService();
    }

//    用户回答数据提交
    public void postAnswerSubmit(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ResponseBean responseBean = new ResponseBean();

        int pid = Integer.parseInt(request.getParameter("pid"));
        String uuid = request.getParameter("uuid");
        String data = request.getParameter("data");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");


        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));

        //得到用户的浏览器名
        String userBrowser = userAgent.getBrowser().toString();
        //得到用户的操作系统名 
        String userOs = userAgent.getOperatingSystem().toString();

        AnswerUser user = new AnswerUser();
        user.setUuid(StringUtil.getShortUuid());
        user.setAnswerStatus("正常");
        user.setStartTime(new Date(Long.parseLong(startTime)));
        user.setEndTime(new Date(Long.parseLong(endTime)));
        user.setBrowser(userBrowser);
        user.setOs(userOs);
        user.setIp(request.getRemoteAddr());
        user.setPid(pid);

        if (answerUserService.addAnswerUser(user, uuid, data)) {
            responseBean.setCode(0);
        } else {
            responseBean.setCode(1);
            responseBean.setMsg("保存失败，请稍后重试");
        }

        response.getWriter().write(responseBean.toJson());
    }

//    获取问卷回答人数
    public void getCountAnswer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int pid = Integer.parseInt(request.getParameter("pid"));

        ResponseBean<Integer> responseBean = new ResponseBean<>();

        responseBean.setCode(0);
        responseBean.setData(answerUserService.countAnswer(pid));

        response.getWriter().write(responseBean.toJson());
    }

//    获取所有回答问卷的用户
    public void getAllAnswerUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseBean<List<AnswerUser>> responseBean = new ResponseBean<>();
        try {
            int pid = Integer.parseInt(request.getParameter("pid"));

            List<AnswerUser> answerUsers = answerUserService.findAnswerUserByPid(pid);
            responseBean.setCode(0);
            responseBean.setData(answerUsers);
        }catch (Exception e) {
            e.printStackTrace();
            responseBean.setCode(1);
            responseBean.setMsg("参数错误");
        }

        response.getWriter().write(responseBean.toJson());
    }

//    获取回答用户的详细信息和回答数据
    public void getAnswerUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseBean<AnswerUser> responseBean = new ResponseBean<>();
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String uuid = request.getParameter("uuid");

            AnswerUser answerUser = answerUserService.findAnswerDateById(id, uuid);
            responseBean.setCode(0);
            responseBean.setData(answerUser);
        }catch (Exception e) {
            e.printStackTrace();
            responseBean.setCode(1);
            responseBean.setMsg("参数错误");
        }

        response.getWriter().write(responseBean.toJson());
    }

//    统计用户回答数据
    public void getAnswerResultStatistical(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ResponseBean<Map<String, String>> responseBean = new ResponseBean<>();

        try {
            String uuid = request.getParameter("uuid");
            int id = Integer.parseInt(request.getParameter("pid"));

            try {
                Map<String, String> allAnswerCount = answerUserService.getAllAnswerCount(uuid, id, request.getServletContext());
                responseBean.setCode(0);
                responseBean.setData(allAnswerCount);
            }catch (Exception e) {
                e.printStackTrace();
                responseBean.setCode(2);
                responseBean.setMsg("数据出错");
            }

        }catch (Exception e) {
            e.printStackTrace();
            responseBean.setCode(1);
            responseBean.setMsg("参数错误");
        }

        response.getWriter().write(responseBean.toJson());

    }
}
