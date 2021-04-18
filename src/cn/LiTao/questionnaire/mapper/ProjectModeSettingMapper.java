package cn.LiTao.questionnaire.mapper;

import cn.LiTao.questionnaire.pojo.ProjectModeSetting;

public interface ProjectModeSettingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProjectModeSetting record);

    int insertSelective(ProjectModeSetting record);

    ProjectModeSetting selectByPrimaryKey(Integer id);

    ProjectModeSetting selectByProjectId(Integer pid);

    int updateByPrimaryKeySelective(ProjectModeSetting record);

    int updateByProjectIdSelective(ProjectModeSetting record);

    int updateByPrimaryKey(ProjectModeSetting record);
}