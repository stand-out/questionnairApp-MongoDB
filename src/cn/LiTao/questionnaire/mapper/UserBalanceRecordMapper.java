package cn.LiTao.questionnaire.mapper;

import cn.LiTao.questionnaire.pojo.UserBalanceRecord;

import java.util.List;

public interface UserBalanceRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserBalanceRecord record);

    int insertSelective(UserBalanceRecord record);

    UserBalanceRecord selectByPrimaryKey(Integer id);

    List<UserBalanceRecord> selectByUserId(Integer uid);

    int updateByPrimaryKeySelective(UserBalanceRecord record);

    int updateByPrimaryKey(UserBalanceRecord record);
}