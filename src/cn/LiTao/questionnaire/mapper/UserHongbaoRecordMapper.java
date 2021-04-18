package cn.LiTao.questionnaire.mapper;

import cn.LiTao.questionnaire.pojo.UserHongbaoRecord;

public interface UserHongbaoRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserHongbaoRecord record);

    int insertSelective(UserHongbaoRecord record);

    UserHongbaoRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserHongbaoRecord record);

    int updateByPrimaryKey(UserHongbaoRecord record);
}