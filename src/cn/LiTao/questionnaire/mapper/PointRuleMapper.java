package cn.LiTao.questionnaire.mapper;

import cn.LiTao.questionnaire.pojo.PointRule;

import java.util.List;

public interface PointRuleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PointRule record);

    int insertSelective(PointRule record);

    PointRule selectByPrimaryKey(Integer id);

    List<PointRule> selectAll();

    int updateByPrimaryKeySelective(PointRule record);

    int updateByPrimaryKey(PointRule record);
}