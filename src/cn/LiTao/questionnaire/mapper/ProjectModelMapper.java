package cn.LiTao.questionnaire.mapper;

import cn.LiTao.questionnaire.pojo.ProjectModel;

import java.util.List;

public interface ProjectModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProjectModel record);

    int insertSelective(ProjectModel record);

    ProjectModel selectByPrimaryKey(Integer id);

    List<ProjectModel> selectByModelTypeId(Integer modelTypeId);

    int updateByPrimaryKeySelective(ProjectModel record);

    int updateByPrimaryKey(ProjectModel record);
}