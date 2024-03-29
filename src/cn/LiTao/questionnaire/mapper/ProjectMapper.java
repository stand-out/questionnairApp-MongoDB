package cn.LiTao.questionnaire.mapper;

import cn.LiTao.questionnaire.pojo.Project;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface ProjectMapper {

    @Select("SELECT id, project_name, project_type, project_status, last_modify_time, data_uuid, reward FROM project WHERE uid = #{uid} AND project_name like concat('%', #{projectName}, '%') AND status = 1 ORDER BY last_modify_time DESC")
    @Results(id = "projectListData",
            value = {@Result(id = true, column = "id", property = "id"),
                    @Result(column = "project_name", property = "projectName"),
                    @Result(column = "project_type", property = "projectType"),
                    @Result(column = "project_status", property = "projectStatus"),
                    @Result(column = "last_modify_time", property = "lastModifyTime"),
                    @Result(column = "data_uuid", property = "dataUuid"),
                    @Result(column = "reward", property = "reward"),
                    @Result(column = "id", property = "answerCount", one = @One(select = "cn.LiTao.questionnaire.mapper.AnswerUserMapper.countAnswerByPid", fetchType = FetchType.EAGER))
    })
    List<Project> findProjectByUid(@Param("uid")int uid, @Param("projectName")String projectName);

    @Select("SELECT id, project_name, project_type, project_status, last_modify_time, data_uuid, uid, reward FROM project WHERE id = #{pid}")
    @Results(id = "projectAllData",
            value = {@Result(id = true, column = "id", property = "id"),
                    @Result(column = "project_name", property = "projectName"),
                    @Result(column = "project_type", property = "projectType"),
                    @Result(column = "project_status", property = "projectStatus"),
                    @Result(column = "last_modify_time", property = "lastModifyTime"),
                    @Result(column = "data_uuid", property = "dataUuid"),
                    @Result(column = "reward", property = "reward"),
                    @Result(column = "uid", property = "user", one = @One(select = "cn.LiTao.questionnaire.mapper.UserMapper.findUserByIdSimple", fetchType = FetchType.EAGER)),
                    @Result(column = "id", property = "answerCount", one = @One(select = "cn.LiTao.questionnaire.mapper.AnswerUserMapper.countAnswerByPid", fetchType = FetchType.EAGER))
    })
    Project findProjectByPid(int pid);

    @Select("SELECT id, project_name, project_type, project_status, last_modify_time, data_uuid, uid, reward FROM project WHERE uid = #{uid} AND data_uuid = #{uuid}")
    Project findProjectByUuidAndUid(@Param("uid") int uid,@Param("uuid") String uuid);

    @Select("SELECT id, project_name, project_type, project_status, last_modify_time, data_uuid, uid, reward FROM project WHERE data_uuid = #{uuid}")
    @ResultMap("projectAllData")
    Project findProjectByUuid(@Param("uuid") String uuid);

    @Select("SELECT id, project_name, project_type, project_status, last_modify_time, data_uuid, uid, reward FROM project WHERE id = #{id}")
    Project findProjectById(int id);

    @Insert("INSERT INTO project(project_name, project_type, project_status, last_modify_time, data_uuid, uid) VALUES(#{projectName}, #{projectType}, #{projectStatus}, #{lastModifyTime}, #{dataUuid}, #{user.id})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void insertProject(Project project);

    @Update("UPDATE project SET project_name = #{projectName}, project_type=#{projectType}, project_status=#{projectStatus}, last_modify_time=#{lastModifyTime} WHERE id=#{id}")
    void updateProject(Project project);

    @Update("UPDATE project SET status = 0 where id = #{id}")
    void removeProject(int id);

    @Update("UPDATE project SET reward = #{reward} WHERE id = #{pid}")
    void setReward(int pid, int reward);

}
