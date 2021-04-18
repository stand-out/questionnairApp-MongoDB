package cn.LiTao.questionnaire.service.impl;

import cn.LiTao.questionnaire.mapper.ProjectLimitSettingMapper;
import cn.LiTao.questionnaire.mapper.ProjectModeSettingMapper;
import cn.LiTao.questionnaire.pojo.ProjectLimitSetting;
import cn.LiTao.questionnaire.pojo.ProjectModeSetting;
import cn.LiTao.questionnaire.service.MyService;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;

/**
 * @author Devil
 */
public class ProjectSettingService extends MyService {
    private static final long serialVersionUID = -7625959644787526172L;

    public int insertModelSetting (ProjectModeSetting projectModeSetting) {
        final SqlSession sqlSession = createSqlSession();
        final ProjectModeSettingMapper mapper = sqlSession.getMapper(ProjectModeSettingMapper.class);

        projectModeSetting.setCreateTime(new Date());
        final int result = mapper.insert(projectModeSetting);

        sqlSession.commit();
        closeSqlSession(sqlSession);

        return result;
    }

    public int insertLimitSetting (ProjectLimitSetting projectLimitSetting) {
        final SqlSession sqlSession = createSqlSession();
        final ProjectLimitSettingMapper mapper = sqlSession.getMapper(ProjectLimitSettingMapper.class);

        projectLimitSetting.setCreateTime(new Date());
        final int result = mapper.insert(projectLimitSetting);

        sqlSession.commit();
        closeSqlSession(sqlSession);

        return result;
    }

    public int updateModelSetting (ProjectModeSetting projectModeSetting) {
        final SqlSession sqlSession = createSqlSession();
        final ProjectModeSettingMapper mapper = sqlSession.getMapper(ProjectModeSettingMapper.class);

        projectModeSetting.setUpdateTime(new Date());
        final int result = mapper.updateByProjectIdSelective(projectModeSetting);

        sqlSession.commit();
        closeSqlSession(sqlSession);

        return result;
    }

    public int updateLimitSetting (ProjectLimitSetting projectLimitSetting) {
        final SqlSession sqlSession = createSqlSession();
        final ProjectLimitSettingMapper mapper = sqlSession.getMapper(ProjectLimitSettingMapper.class);

        projectLimitSetting.setUpdateTime(new Date());
        final int result = mapper.updateByProjectIdSelective(projectLimitSetting);

        sqlSession.commit();
        closeSqlSession(sqlSession);

        return result;
    }

    public ProjectModeSetting findModelSettingByPid (int pid) {
        final SqlSession sqlSession = createSqlSession();
        final ProjectModeSettingMapper mapper = sqlSession.getMapper(ProjectModeSettingMapper.class);

        final ProjectModeSetting projectModeSetting = mapper.selectByProjectId(pid);

        sqlSession.commit();
        closeSqlSession(sqlSession);

        return projectModeSetting;
    }

    public ProjectLimitSetting findLimitSettingByPid (int pid) {
        final SqlSession sqlSession = createSqlSession();
        final ProjectLimitSettingMapper mapper = sqlSession.getMapper(ProjectLimitSettingMapper.class);

        final ProjectLimitSetting projectLimitSetting = mapper.selectByProjectId(pid);

        sqlSession.commit();
        closeSqlSession(sqlSession);

        return projectLimitSetting;
    }

    public void initProjectSetting (int pid) {
        final ProjectModeSetting projectModeSetting = ProjectModeSetting.builder()
                .pid(pid)
                .realMode(1)
                .recordUserInfo(0)
                .build();

        final ProjectLimitSetting projectLimitSetting = ProjectLimitSetting.builder()
                .pid(pid)
                .build();

        insertModelSetting(projectModeSetting);
        insertLimitSetting(projectLimitSetting);
    }
}
