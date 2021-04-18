package cn.LiTao.questionnaire.mapper;

import cn.LiTao.questionnaire.pojo.ProjectHongbao;

public interface ProjectHongbaoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProjectHongbao record);

    int insertSelective(ProjectHongbao record);

    ProjectHongbao selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProjectHongbao record);

    int updateByPrimaryKey(ProjectHongbao record);
}