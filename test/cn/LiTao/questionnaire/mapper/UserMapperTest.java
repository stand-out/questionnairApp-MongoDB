package cn.LiTao.questionnaire.mapper;

import cn.LiTao.questionnaire.pojo.Project;
import cn.LiTao.questionnaire.pojo.ResponseBean;
import cn.LiTao.questionnaire.pojo.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperTest {

    private SqlSessionFactory factory;
    private SqlSession sqlSession;

    @Before
    public void init() throws IOException {
        factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("SqlMapConfig.xml"));
        sqlSession = factory.openSession();
    }

    @Test
    public void loginTest() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User("root", "123456", "186746686222");
        User result = mapper.findUserByPhoneAndPassword(user);
        System.out.println(result);
    }

    @Test
    public void test() {

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        User user = new User("root", "123456", "18674668622");
        mapper.insertUser(user);

        sqlSession.commit();
    }

    @Test
    public void findAllUserDataTest() throws JsonProcessingException {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        User user = mapper.findUserById(5);
//
//        List<Project> projectList = user.getProjectList();
//
//        for (Project project : projectList) {
//            System.out.println(project);
//        }

        ResponseBean<User> responseBean = new ResponseBean<>(0, user, "test");

        System.out.println(responseBean.toJson());
    }

    @Test
    public void test1() throws InterruptedException {
        Date date1 = new Date();
        Thread.sleep(1000);
        Date date2 = new Date();

        System.out.println(date2.getTime() - date1.getTime());
    }

}
