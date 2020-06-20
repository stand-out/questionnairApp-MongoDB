package cn.LiTao.questionnaire.service;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

public abstract class MyService {

    protected static SqlSessionFactory sqlSessionFactory;
    protected SqlSession sqlSession;

    //    创建sqlSessionFactory
    static {
        try {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("SqlMapConfig.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected synchronized void closeSqlSession(SqlSession sqlSession) {
        if (sqlSession != null) {
            sqlSession.close();
            System.out.println("sqlSession关闭");
        }

    }

    protected synchronized SqlSession createSqlSession() {
        sqlSession = sqlSessionFactory.openSession();
        return sqlSession;
    }
}
