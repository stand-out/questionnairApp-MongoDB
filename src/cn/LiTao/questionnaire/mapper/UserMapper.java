package cn.LiTao.questionnaire.mapper;

import cn.LiTao.questionnaire.pojo.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

public interface UserMapper {

    @Insert("INSERT INTO user(username, phone_number, password) VALUES(#{username}, #{phoneNumber}, #{password})")
    void insertUser(User user);

    @Select("SELECT id, username, password, header_image_path, phone_number FROM user WHERE phone_number = #{phoneNumber} AND password = #{password}")
    User findUserByPhoneAndPassword(User user);

    @Select("SELECT id, username, password, header_image_path, phone_number FROM user WHERE id = #{id}")
    @Results(
            id = "userAllDataMap",
            value = {
                    @Result(id = true, column = "id", property = "id"),
                    @Result(column = "username", property = "username"),
                    @Result(column = "password", property = "password"),
                    @Result(column = "header_image_path", property = "headerImagePath"),
                    @Result(column = "phone_number", property = "phoneNumber"),
                    @Result(column = "id", property = "projectList", many = @Many(select = "cn.LiTao.questionnaire.mapper.ProjectMapper.findProjectByUid", fetchType = FetchType.EAGER))
            }
    )
    User findUserById(int id);

    @Select("SELECT id, username, password, header_image_path, phone_number FROM user WHERE id = #{id}")
    User findUserByIdSimple(int id);

}
