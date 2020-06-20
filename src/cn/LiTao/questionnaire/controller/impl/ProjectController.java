package cn.LiTao.questionnaire.controller.impl;

import cn.LiTao.questionnaire.controller.MyServlet;
import cn.LiTao.questionnaire.dependent.ProjectStatus;
import cn.LiTao.questionnaire.pojo.Project;
import cn.LiTao.questionnaire.pojo.ResponseBean;
import cn.LiTao.questionnaire.pojo.User;
import cn.LiTao.questionnaire.service.impl.ProjectService;
import cn.LiTao.questionnaire.utils.JsonUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@WebServlet("/api/project/*")
public class ProjectController extends MyServlet {

    private static final long serialVersionUID = -4266172670390287352L;
    private ProjectService projectService;

    public ProjectController() {
        this.projectService = new ProjectService();
    }

    //    创建新项目
    public void getCreateProject(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        ResponseBean<String> responseBean = new ResponseBean<>();
//        从session获取用户的user信息
        User user = (User) session.getAttribute("user");

        if (user != null) { // 用户已登录

            String uuid = projectService.createProject(user, request.getServletContext());

            if (uuid != null) {  // 创建新项目业务执行成功
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
        Project project = JsonUtil.jsonToObject(json, Project.class);
        ResponseBean responseBean = new ResponseBean<>();

        HttpSession session = request.getSession();
//        从session获取用户的user信息
        User user = (User) session.getAttribute("user");

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

        HttpSession session = request.getSession();
//        从session获取用户的user信息
        User user = (User) session.getAttribute("user");

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
}
