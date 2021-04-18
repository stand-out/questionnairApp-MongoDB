package cn.LiTao.questionnaire.service.impl;

import cn.LiTao.questionnaire.constant.ApplicationConstant;
import cn.LiTao.questionnaire.dependent.ProjectStatus;
import cn.LiTao.questionnaire.dependent.QuestionnaireContainer;
import cn.LiTao.questionnaire.mapper.*;
import cn.LiTao.questionnaire.pojo.*;
import cn.LiTao.questionnaire.service.HongbaoService;
import cn.LiTao.questionnaire.service.MyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.bouncycastle.util.Strings;

import javax.servlet.ServletContext;
import java.util.*;

@Slf4j
public class ProjectService extends MyService {

    private HongbaoService hongbaoService = new HongbaoService();
    private ProjectMapper projectMapper;
    private ProjectSettingService projectSettingService;

    @Override
    public synchronized SqlSession createSqlSession() {
        sqlSession = sqlSessionFactory.openSession();
        projectMapper = sqlSession.getMapper(ProjectMapper.class);
        return sqlSession;
    }

    public ProjectService() {
        projectSettingService = new ProjectSettingService();
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

            projectSettingService.initProjectSetting(project.getId());
        }

        closeSqlSession(sqlSession);
        return uuid;
    }

    public String createProjectByModel(User user, String modelUuid, ServletContext servletContext) {
        log.debug("用户开始创建模板项目");

        //        获取问卷容器
        QuestionnaireContainer questionnaireContainer = (QuestionnaireContainer) servletContext.getAttribute("questionnaireContainer");
        SqlSession sqlSession = createSqlSession();
        sqlSession.getMapper(ProjectMapper.class);

        final Questionnaire modelQuestionnaire = questionnaireContainer.getQuestionnaire(modelUuid);

        if (Objects.isNull(modelQuestionnaire)) {
            return "";
        }

//        初始化模板问卷数据
        modelQuestionnaire.setTitle(modelQuestionnaire.getTitle() + "模板");
        modelQuestionnaire.setPassword("");
        modelQuestionnaire.setIpCountLimit(-1);

        //        把当前新建的问卷对象加入容器 加入成功返回对象对应的uuid
        String uuid = questionnaireContainer.addQuestionnaire(modelQuestionnaire);
        log.debug("用户创建模板项目保存至容器成功");

        if (StringUtils.isNotBlank(uuid)) {
//            生成项目对象并存入数据库
            Project project = new Project();
            project.setProjectName(modelQuestionnaire.getTitle());
            project.setProjectType("问卷");
            project.setProjectStatus(ProjectStatus.UNPUBLISHED);
            project.setLastModifyTime(new Date());
            project.setDataUuid(uuid);
            project.setUser(user);

            projectMapper.insertProject(project);
            sqlSession.commit();
            log.debug("用户创建模板项目保存至MySQL成功");

            projectSettingService.initProjectSetting(project.getId());
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

    public List<Project> getProjectListByUserId(int id, String projectName) {
        SqlSession sqlSession = createSqlSession();
        final List<Project> projectByUid = projectMapper.findProjectByUid(id, projectName);
        closeSqlSession(sqlSession);
        return projectByUid;
    }

    public void removeProject(int projectId) {
        SqlSession sqlSession = createSqlSession();
        projectMapper.removeProject(projectId);
        this.sqlSession.commit();
        closeSqlSession(sqlSession);
    }

    public int sendHongBao4Project(int uid, int pid, int type, int amount, int number) {
        final SqlSession sqlSession = createSqlSession();
        final ProjectHongbaoMapper projectHongbaoMapper = sqlSession.getMapper(ProjectHongbaoMapper.class);
        final ProjectMapper projectMapper = sqlSession.getMapper(ProjectMapper.class);
        final UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        final UserBalanceRecordMapper userBalanceRecordMapper = sqlSession.getMapper(UserBalanceRecordMapper.class);
        boolean success = true;

        final User user = userMapper.findUserByIdSimple(uid);
        final Project project = projectMapper.findProjectByPid(pid);

        if (Objects.isNull(user)) {
            return 1;
        }
        else if (Objects.isNull(project)) {
            return 2;
        }
        else if (user.getBalance() < amount) {
            return 3;
        }

        final UserBalanceRecord userBalanceRecord = new UserBalanceRecord();
        userBalanceRecord.setUid(user.getId());
        userBalanceRecord.setType(0);
        userBalanceRecord.setChangeAmount(amount);
        userBalanceRecord.setRemark("给[" + project.getProjectName() + "]问卷设置红包");
        userBalanceRecord.setCreateTime(new Date());

        final ProjectHongbao projectHongbao = new ProjectHongbao();
        projectHongbao.setProjectId(pid);
        projectHongbao.setAmount(amount);
        projectHongbao.setType(type);
        projectHongbao.setNumber(number);

        final User updateUser = User.builder()
                .id(user.getId())
                .balance(user.getBalance() - amount)
                .build();

        String poolId = ApplicationConstant.REDIS_HONGBAO_KEY_PREFIX + pid;

        try {
            userBalanceRecordMapper.insert(userBalanceRecord);
            projectHongbaoMapper.insert(projectHongbao);
            userMapper.updateUserData(updateUser);
            projectMapper.setReward(pid, 1);
            hongbaoService.createHongbaoPrizePool(poolId, amount, number, type);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
            success = false;
        } finally {
            sqlSession.close();
        }

        return success ? 0 : 4;
    }

    public Map<String, Integer> getHongBaoData(int pid) {
        Map<String, Integer> dataMap = new HashMap<>(2);
        String poolId = ApplicationConstant.REDIS_HONGBAO_KEY_PREFIX + pid;

        final Set<String> hongBaoData = hongbaoService.getPreData(poolId);
        final int amount = hongbaoService.calcPreAmount(hongBaoData);
        dataMap.put("count", hongBaoData.size());
        dataMap.put("amount", amount);

        return dataMap;
    }

    public int getHongBao4Project(int uid, int pid) {
        String poolId = ApplicationConstant.REDIS_HONGBAO_KEY_PREFIX + pid;
        final int amount = hongbaoService.getHongbaoFromPrizePool(poolId);
        boolean success = true;

        if (amount == -1) {
            return -1;
        }

        final SqlSession sqlSession = createSqlSession();
        final UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        final ProjectMapper projectMapper = sqlSession.getMapper(ProjectMapper.class);
        final UserBalanceRecordMapper userBalanceRecordMapper = sqlSession.getMapper(UserBalanceRecordMapper.class);
        final UserHongbaoRecordMapper userHongbaoRecordMapper = sqlSession.getMapper(UserHongbaoRecordMapper.class);

        final User user = userMapper.findUserByIdSimple(uid);
        final Project project = projectMapper.findProjectByPid(pid);

        if (Objects.isNull(user) || Objects.isNull(project)) {
            return -2;
        }

        final User updateUser = User.builder()
                .id(uid)
                .balance(user.getBalance() + amount)
                .build();

        final UserHongbaoRecord userHongbaoRecord = new UserHongbaoRecord();
        userHongbaoRecord.setPid(pid);
        userHongbaoRecord.setUid(uid);
        userHongbaoRecord.setUserName(user.getUsername());
        userHongbaoRecord.setAmount(amount);

        final UserBalanceRecord userBalanceRecord = new UserBalanceRecord();
        userBalanceRecord.setUid(uid);
        userBalanceRecord.setType(1);
        userBalanceRecord.setChangeAmount(amount);
        userBalanceRecord.setRemark("从[" + project.getProjectName() + "]问卷抢到红包");
        userBalanceRecord.setCreateTime(new Date());

        try {
            userMapper.updateUserData(updateUser);
            userBalanceRecordMapper.insert(userBalanceRecord);
            userHongbaoRecordMapper.insert(userHongbaoRecord);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
            success = false;
        } finally {
            sqlSession.close();
        }

        return success ? amount : -2;
    }

    public List<ProjectModelType> getModelType() {
        final SqlSession sqlSession = createSqlSession();
        final ProjectModelTypeMapper projectModelTypeMapper = sqlSession.getMapper(ProjectModelTypeMapper.class);

        final List<ProjectModelType> projectModelTypes = projectModelTypeMapper.selectAll();
        sqlSession.close();

        return projectModelTypes;
    }

    public List<ProjectModel> getProjectModel(int modelTypeId) {
        final SqlSession sqlSession = createSqlSession();
        final ProjectModelMapper mapper = sqlSession.getMapper(ProjectModelMapper.class);

        final List<ProjectModel> projectModels = mapper.selectByModelTypeId(modelTypeId);
        sqlSession.close();

        return projectModels;
    }
}
