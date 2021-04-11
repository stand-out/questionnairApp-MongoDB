package cn.LiTao.questionnaire.controller.impl;

import cn.LiTao.questionnaire.constant.ApplicationConstant;
import cn.LiTao.questionnaire.controller.MyServlet;
import cn.LiTao.questionnaire.dependent.ProjectStatus;
import cn.LiTao.questionnaire.pojo.Project;
import cn.LiTao.questionnaire.pojo.ResponseBean;
import cn.LiTao.questionnaire.pojo.User;
import cn.LiTao.questionnaire.service.impl.ProjectService;
import cn.LiTao.questionnaire.utils.JedisUtil;
import cn.LiTao.questionnaire.utils.JsonUtil;
import cn.LiTao.questionnaire.utils.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@WebServlet("/api/project/*")
public class ProjectController extends MyServlet {

    private static final long serialVersionUID = -4266172670390287352L;
    private ProjectService projectService;

    public ProjectController() {
        this.projectService = new ProjectService();
    }

    //  创建新项目
    public void getCreateProject(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ResponseBean<String> responseBean = new ResponseBean<>();
//        从session获取用户的user信息
        final User user = SessionUtil.getSessionUser(request);
        // 用户已登录
        if (user != null) {

            String uuid = projectService.createProject(user, request.getServletContext());
            // 创建新项目业务执行成功
            if (uuid != null) {
                responseBean.setCode(0);
                responseBean.setData(uuid);
            } else {
                responseBean.setCode(2);
                responseBean.setMsg("创建文件失败");
            }
        } else {   // 用户未登录
            responseBean.setCode(1);
            responseBean.setMsg("请先登录");
        }

//        将响应内容对象转成json返回
        response.getWriter().write(responseBean.toJson());
    }

    //获取项目所有内容
    public void getProjectInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        ResponseBean<Project> responseBean = new ResponseBean<>();
//        从session获取用户的user信息
        User user = (User) session.getAttribute("user");
        String uuid = request.getParameter("uuid");

//        if (user != null) {

        if (uuid != null) {
            Project project = projectService.projectInfo(uuid, request.getServletContext());
            if (project != null) {
                responseBean.setCode(0);
                responseBean.setData(project);
            } else {
                responseBean.setCode(2);
                responseBean.setMsg("该项目不存在");
            }
        } else {
            responseBean.setCode(3);
            responseBean.setMsg("uuid不能为空");
        }

//        } else {   // 用户未登录
//            responseBean.setCode(1);
//            responseBean.setMsg("请先登录");
//        }

        response.getWriter().write(responseBean.toJson());
    }

    public void getProject(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        ResponseBean<Project> responseBean = new ResponseBean<>();
//        从session获取用户的user信息
        User user = (User) session.getAttribute("user");
        String uuid = request.getParameter("uuid");

        if (user != null) {

            if (uuid != null) {
                Project project = projectService.getProjectByUuid(user.getId(), uuid);
                if (project != null) {
                    responseBean.setCode(0);
                    responseBean.setData(project);
                } else {
                    responseBean.setCode(2);
                    responseBean.setMsg("该项目不存在");
                }
            } else {
                responseBean.setCode(3);
                responseBean.setMsg("uuid不能为空");
            }

        } else {   // 用户未登录
            responseBean.setCode(1);
            responseBean.setMsg("请先登录");
        }

        response.getWriter().write(responseBean.toJson());
    }

    public void postUpdateProject(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String json = request.getParameter("projectDate");
        log.debug(json);
        Project project = JsonUtil.jsonToObject(json, Project.class);
        ResponseBean responseBean = new ResponseBean<>();

        HttpSession session = request.getSession();
//        从session获取用户的user信息
        User user = SessionUtil.getSessionUser(request);

        if (user != null) {
            if (project != null) {
                boolean result = projectService.updateProject(user, project, true, request.getServletContext());

                if (result) {
                    responseBean.setCode(0);
                } else {
                    responseBean.setCode(2);
                    responseBean.setMsg("操作失败");
                }
            } else {
                responseBean.setCode(3);
                responseBean.setMsg("问卷不存在");
            }

        } else {
            responseBean.setCode(1);
            responseBean.setMsg("请先登录");
        }

        response.getWriter().write(responseBean.toJson());

    }

    public void getPublishedProjectToggle(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String uuid = request.getParameter("pid");
        String status = request.getParameter("s");
        ResponseBean responseBean = new ResponseBean<>();

//        从session获取用户的user信息
        User user = SessionUtil.getSessionUser(request);

        if (user != null) {
//            根据projectId查出project
            Project project = projectService.getProjectByUuid(user.getId(), uuid);
            if (project != null) {
//                改成收集中状态
                if ("1".equals(status)) {
                    project.setProjectStatus(ProjectStatus.COLLECTION);
                } else {
                    project.setProjectStatus(ProjectStatus.UNPUBLISHED);
                }

                project.setLastModifyTime(new Date());
//                更新数据库
                boolean result = projectService.updateProject(user, project, false, null);

                if (result) {
                    responseBean.setCode(0);
                } else {
                    responseBean.setCode(2);
                    responseBean.setMsg("修改文件失败");
                }
            } else {
                responseBean.setCode(3);
                responseBean.setMsg("文件不存在");
            }

        } else {
            responseBean.setCode(1);
            responseBean.setMsg("请先登录");
        }

        response.getWriter().write(responseBean.toJson());

    }

    public void getProjectList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String accessToken = request.getParameter("access_token");
        ResponseBean<List<Project>> responseBean = new ResponseBean<>();
        if (StringUtils.isNotBlank(accessToken)) {
            final String userJson = JedisUtil.get(ApplicationConstant.REDIS_SESSION_KEY_PREFIX + accessToken);
            final User user = JsonUtil.jsonToObject(userJson, User.class);
            if (Objects.nonNull(user)) {
                final List<Project> projectList = projectService.getProjectListByUserId(user.getId());
                responseBean.setCode(0);
                responseBean.setData(projectList);
            } else {
                responseBean.setCode(2);
                responseBean.setMsg("系统异常");
            }
        } else {
            responseBean.setCode(1);
            responseBean.setMsg("用户未登录");
        }

        response.getWriter().write(responseBean.toJson());
    }

    public void getRemoveProject(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final int questionId = Integer.parseInt(request.getParameter("projectId"));
        projectService.removeProject(questionId);
        response.getWriter().write(ResponseBean.SUCCESS().toJson());
    }
}
