package cn.LiTao.questionnaire.mapper;

import cn.LiTao.questionnaire.pojo.ProjectModelType;

import java.util.List;

public interface ProjectModelTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProjectModelType record);

    int insertSelective(ProjectModelType record);

    ProjectModelType selectByPrimaryKey(Integer id);

    List<ProjectModelType> selectAll();

    int updateByPrimaryKeySelective(ProjectModelType record);

    int updateByPrimaryKey(ProjectModelType record);
}