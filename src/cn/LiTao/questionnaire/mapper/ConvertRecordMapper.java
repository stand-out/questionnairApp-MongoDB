package cn.LiTao.questionnaire.mapper;

import cn.LiTao.questionnaire.pojo.ConvertRecord;

import java.util.List;

public interface ConvertRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ConvertRecord record);

    int insertSelective(ConvertRecord record);

    ConvertRecord selectByPrimaryKey(Integer id);

    List<ConvertRecord> selectByUserId(Integer uid);

    int updateByPrimaryKeySelective(ConvertRecord record);

    int updateByPrimaryKey(ConvertRecord record);
}