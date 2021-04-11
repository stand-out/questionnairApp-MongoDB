package cn.LiTao.questionnaire.service.impl;

import cn.LiTao.questionnaire.dependent.ProjectStatus;
import cn.LiTao.questionnaire.dependent.QuestionnaireContainer;
import cn.LiTao.questionnaire.mapper.ProjectMapper;
import cn.LiTao.questionnaire.pojo.Project;
import cn.LiTao.questionnaire.pojo.Questionnaire;
import cn.LiTao.questionnaire.pojo.User;
import cn.LiTao.questionnaire.service.MyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletContext;
import java.util.Date;
import java.util.List;

@Slf4j
public class ProjectService extends MyService {

    private ProjectMapper projectMapper;

    @Override
    public synchronized SqlSession createSqlSession() {
        sqlSession = sqlSessionFactory.openSession();
        projectMapper = sqlSession.getMapper(ProjectMapper.class);
        return sqlSession;
    }

    public ProjectService() {
        createSqlSession();
    }

    //    创建项目业务
    public String createProject(User user, ServletContext servletContext) {
        log.debug("用户开始创建项目");
        SqlSession sqlSession = createSqlSession();
//        新建并初始化问卷数据
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setTitle("问卷标题");
        questionnaire.setDescribe("感谢您能抽出几分钟时间来参加本次答题，现在我们就马上开始吧！");
        questionnaire.setEndDescribe("您已完成本次问卷，感谢您的帮助与支持");
        questionnaire.setPassword("");
        questionnaire.setIpCountLimit(-1);

//        获取问卷容器
        QuestionnaireContainer questionnaireContainer = (QuestionnaireContainer) servletContext.getAttribute("questionnaireContainer");

//        把当前新建的问卷对象加入容器 加入成功返回对象对应的uuid
        String uuid = questionnaireContainer.addQuestionnaire(questionnaire);
        log.debug("用户创建项目保存至容器成功");

        if (uuid != null) {
//            生成项目对象并存入数据库
            Project project = new Project();
            project.setProjectName(questionnaire.getTitle());
            project.setProjectType("问卷");
            project.setProjectStatus(ProjectStatus.UNPUBLISHED);
            project.setLastModifyTime(new Date());
            project.setDataUuid(uuid);
            project.setUser(user);

            projectMapper.insertProject(project);
            this.sqlSession.commit();
            log.debug("用户创建项目保存至MySQL成功");
        }

        closeSqlSession(sqlSession);
        return uuid;
    }

    //    获取项目内容
    public Project projectInfo(String uuid, ServletContext servletContext) {
        SqlSession sqlSession = createSqlSession();
//        验证获取项目信息
        Project project = projectMapper.findProjectByUuid(uuid);

        if (project != null) {
            //        获取问卷信息
            QuestionnaireContainer questionnaireContainer = (QuestionnaireContainer) servletContext.getAttribute("questionnaireContainer");

            project.setQuestionnaire(questionnaireContainer.getQuestionnaire(uuid));
        }

        closeSqlSession(sqlSession);
        return project;
    }

    public Project getProjectByUuid(int uid, String uuid) {
        SqlSession sqlSession = createSqlSession();
        Project project = projectMapper.findProjectByUuidAndUid(uid, uuid);
        closeSqlSession(sqlSession);
        return project;
    }

    //    更新项目
    public boolean updateProject(User user, Project newProject, boolean isQuestionnaireUpdate, ServletContext servletContext) {

        SqlSession sqlSession = createSqlSession();
        //        验证获取项目信息
        String uuid = newProject.getDataUuid();
        Project project = projectMapper.findProjectByUuidAndUid(user.getId(), uuid);

        boolean returnResult = false;

        if (project != null) {
            boolean result = true;

//            是否需要更新questionnaire
            if (isQuestionnaireUpdate) {
                //        获取问卷信息容器
                QuestionnaireContainer questionnaireContainer = (QuestionnaireContainer) servletContext.getAttribute("questionnaireContainer");
//            在容器中更新
                result = questionnaireContainer.updateQuestionnaire(newProject.getQuestionnaire(), uuid);
            }

            if (result) {
                projectMapper.updateProject(newProject);
                this.sqlSession.commit();
                returnResult = true;
            }
        }
        closeSqlSession(sqlSession);
        return returnResult;
    }

    public List<Project> getProjectListByUserId(int id) {
        SqlSession sqlSession = createSqlSession();
        final List<Project> projectByUid = projectMapper.findProjectByUid(id);
        closeSqlSession(sqlSession);
        return projectByUid;
    }

    public void removeProject(int projectId) {
        SqlSession sqlSession = createSqlSession();
        projectMapper.removeProject(projectId);
        this.sqlSession.commit();
        closeSqlSession(sqlSession);
    }
}
