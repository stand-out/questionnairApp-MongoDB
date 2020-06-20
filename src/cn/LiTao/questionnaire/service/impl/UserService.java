package cn.LiTao.questionnaire.service.impl;

import cn.LiTao.questionnaire.mapper.UserMapper;
import cn.LiTao.questionnaire.pojo.Project;
import cn.LiTao.questionnaire.pojo.User;
import cn.LiTao.questionnaire.service.MyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

@Slf4j
public class UserService extends MyService {

    private UserMapper userMapper;

    @Override
    public synchronized SqlSession createSqlSession() {
        sqlSession = sqlSessionFactory.openSession();
        userMapper = sqlSession.getMapper(UserMapper.class);
        return sqlSession;

    }

    //    获取mapper和sqlSession
    public UserService() {
        createSqlSession();
    }

    public void userRegister(User user) {
        SqlSession sqlSession = createSqlSession();
        userMapper.insertUser(user);
//        提交
        this.sqlSession.commit();
        closeSqlSession(sqlSession);
    }

    public User userLogin(User user) {
        SqlSession sqlSession = createSqlSession();
        User resultUser = userMapper.findUserByPhoneAndPassword(user);
        closeSqlSession(sqlSession);
        return resultUser;
    }

    public User findUserById(int id) {
        SqlSession sqlSession = createSqlSession();
        User user = userMapper.findUserById(id);
        user.getProjectList().sort((Project o1, Project o2) -> (int) (o2.getLastModifyTime().getTime() - o1.getLastModifyTime().getTime()));
        closeSqlSession(sqlSession);
        return user;
    }

}
