package cn.LiTao.questionnaire.mapper;

import cn.LiTao.questionnaire.pojo.AnswerUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AnswerUserMapper {

    @Insert("INSERT INTO answer_user(uuid, answer_status, start_time, end_time, browser, os, ip, pid) VALUES(#{uuid}, #{answerStatus}, #{startTime}, #{endTime}, #{browser}, #{os}, #{ip}, #{pid})")
    void insertAnswerUser(AnswerUser user);

    @Select("SELECT id, uuid, answer_status, start_time, end_time, browser, os, ip, pid FROM answer_user WHERE pid = #{pid}")
    List<AnswerUser> findAnswerUserByPid(int pid);

    @Select("SELECT id, uuid, answer_status, start_time, end_time, browser, os, ip, pid FROM answer_user WHERE id = #{id}")
    AnswerUser findAnswerUserById(int id);

    @Select("SELECT COUNT(id) FROM answer_user WHERE pid = #{pid}")
    int countAnswerByPid(int pid);

}
