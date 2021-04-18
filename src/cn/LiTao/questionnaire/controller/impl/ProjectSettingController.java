package cn.LiTao.questionnaire.controller.impl;

import cn.LiTao.questionnaire.controller.MyServlet;
import cn.LiTao.questionnaire.pojo.ProjectLimitSetting;
import cn.LiTao.questionnaire.pojo.ProjectModeSetting;
import cn.LiTao.questionnaire.pojo.ResponseBean;
import cn.LiTao.questionnaire.service.impl.ProjectSettingService;
import cn.LiTao.questionnaire.utils.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Devil
 */

@Slf4j
@WebServlet("/api/projectSetting/*")
public class ProjectSettingController extends MyServlet {
    private static final long serialVersionUID = -8498163620771953906L;

    private final ProjectSettingService projectSettingService = new ProjectSettingService();

    public void getSetting(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final ResponseBean<Map<String, Object>> mapResponseBean = new ResponseBean<>();
        final String pid = request.getParameter("pid");

        if (StringUtils.isBlank(pid)) {
            log.warn("方法【getSetting】参数 pid不能为空");
            mapResponseBean.setCode(4);
            mapResponseBean.setMsg("参数pid不能为空");
            response.getWriter().write(mapResponseBean.toJson());
            return;
        }
        final int numberPid = Integer.parseInt(pid);

        final ProjectLimitSetting limitSetting = projectSettingService.findLimitSettingByPid(numberPid);
        final ProjectModeSetting modelSetting = projectSettingService.findModelSettingByPid(numberPid);

        Map<String, Object> dataMap = new HashMap<>(8);

        if (Objects.nonNull(modelSetting)) {
            dataMap.put("realMode", modelSetting.getRealMode());
            dataMap.put("recordUserInfo", modelSetting.getRecordUserInfo());
        }

        if (Objects.nonNull(limitSetting)) {
            dataMap.put("startTime", DateTimeUtil.getDateTimeString(limitSetting.getStartTime()));
            dataMap.put("endTime", DateTimeUtil.getDateTimeString(limitSetting.getEndTime()));
            dataMap.put("answerLimit", limitSetting.getAnswerLimit());
        }

        mapResponseBean.setCode(0);
        mapResponseBean.setMsg("success");
        mapResponseBean.setData(dataMap);

        response.getWriter().write(mapResponseBean.toJson());
    }

    public void postSetting(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
        final ResponseBean<Object> objectResponseBean = new ResponseBean<>();
        final String pid = request.getParameter("pid");

        if (StringUtils.isBlank(pid)) {
            log.warn("方法【getSetting】参数 pid不能为空");
            objectResponseBean.setCode(4);
            objectResponseBean.setMsg("参数pid不能为空");
            response.getWriter().write(objectResponseBean.toJson());
            return;
        }

        final int numPid = Integer.parseInt(pid);
        final Integer realMode = StringUtils.isNumeric(request.getParameter("realMode")) ? Integer.parseInt(request.getParameter("realMode")) : null;
        final Integer recordUserInfo = StringUtils.isNumeric(request.getParameter("recordUserInfo")) ? Integer.parseInt(request.getParameter("recordUserInfo")) : null;
        final Date startTime = DateTimeUtil.getDate(request.getParameter("startTime"));
        final Date endTime = DateTimeUtil.getDate(request.getParameter("endTime"));
        final Integer answerLimit = StringUtils.isNumeric(request.getParameter("answerLimit")) ? Integer.parseInt(request.getParameter("answerLimit")) : null;

        final ProjectModeSetting projectModeSetting = ProjectModeSetting.builder()
                .pid(numPid)
                .realMode(realMode)
                .recordUserInfo(recordUserInfo)
                .build();

        final ProjectLimitSetting projectLimitSetting = ProjectLimitSetting.builder()
                .pid(numPid)
                .startTime(startTime)
                .endTime(endTime)
                .answerLimit(answerLimit)
                .build();

        projectSettingService.updateModelSetting(projectModeSetting);
        projectSettingService.updateLimitSetting(projectLimitSetting);

        objectResponseBean.setCode(0);
        objectResponseBean.setMsg("success");

        response.getWriter().println(objectResponseBean.toJson());
    }
}
