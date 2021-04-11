package cn.LiTao.questionnaire.mapper;

import cn.LiTao.questionnaire.pojo.Project;
import cn.LiTao.questionnaire.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class ProjectMapperTest {
    private SqlSessionFactory factory;
    private SqlSession sqlSession;

    @Before
    public void init() throws IOException {
        factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("SqlMapConfig.xml"));
        sqlSession = factory.openSession();
    }

    @Test
    public void findProjectByUidTest() {
        ProjectMapper mapper = sqlSession.getMapper(ProjectMapper.class);

        List<Project> projectList = mapper.findProjectByUid(5);

        System.out.println(projectList);
    }

    @Test
    public void findProjectByIdTest() {
        ProjectMapper mapper = sqlSession.getMapper(ProjectMapper.class);
        Project projectByPid = mapper.findProjectByPid(1);

        System.out.println(projectByPid);
    }

    @Test
    public void insertTest() {
        ProjectMapper mapper = sqlSession.getMapper(ProjectMapper.class);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.findUserByIdSimple(5);

        Project project = mapper.findProjectByPid(1);
        project.setUser(user);

        project.setProjectName("insert:" + project.getProjectName());

        mapper.insertProject(project);
        sqlSession.commit();
    }

    @Test
    public void updateTest() {
        ProjectMapper mapper = sqlSession.getMapper(ProjectMapper.class);

        Project project = mapper.findProjectByPid(1);

        project.setProjectName("update:" + project.getProjectName());
        project.setProjectStatus("已删除");

        mapper.updateProject(project);
        sqlSession.commit();
    }

    @Test
    public void getProjectById() {
        ProjectMapper mapper = sqlSession.getMapper(ProjectMapper.class);
//        Project project = mapper.findProjectById(12);

//        System.out.println(project);
    }

}
