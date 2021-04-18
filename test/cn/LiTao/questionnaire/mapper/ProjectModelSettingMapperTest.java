package cn.LiTao.questionnaire.mapper;

import cn.LiTao.questionnaire.pojo.ProjectModeSetting;
import cn.LiTao.questionnaire.utils.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Devil
 */
public class ProjectModelSettingMapperTest {

    private SqlSessionFactory factory;
    private SqlSession sqlSession;

    @Before
    public void init() throws IOException {
        factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("SqlMapConfig.xml"));
        sqlSession = factory.openSession();
    }

    @Test
    public void commonTest () throws JsonProcessingException {
        final ProjectModeSettingMapper mapper = sqlSession.getMapper(ProjectModeSettingMapper.class);

        final ProjectModeSetting projectModeSetting = mapper.selectByPrimaryKey(1);

        System.out.println(JsonUtil.objectToString(projectModeSetting));
    }

}
