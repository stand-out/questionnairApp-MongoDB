package cn.LiTao.questionnaire.mapper;

import cn.LiTao.questionnaire.pojo.ProjectLimitSetting;

public interface ProjectLimitSettingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProjectLimitSetting record);

    int insertSelective(ProjectLimitSetting record);

    ProjectLimitSetting selectByPrimaryKey(Integer id);

    ProjectLimitSetting selectByProjectId(Integer pid);

    int updateByPrimaryKeySelective(ProjectLimitSetting record);

    int updateByProjectIdSelective(ProjectLimitSetting record);

    int updateByPrimaryKey(ProjectLimitSetting record);
}