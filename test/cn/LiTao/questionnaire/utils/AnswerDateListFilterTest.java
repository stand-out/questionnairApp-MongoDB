//package cn.LiTao.questionnaire.utils;
//
//import cn.LiTao.questionnaire.mapper.AnswerUserMapper;
//import cn.LiTao.questionnaire.pojo.AnswerData;
//import cn.LiTao.questionnaire.pojo.AnswerResultList;
//import cn.LiTao.questionnaire.pojo.AnswerUser;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import org.apache.ibatis.io.Resources;
//import org.apache.ibatis.session.SqlSession;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.apache.ibatis.session.SqlSessionFactoryBuilder;
//import org.junit.Before;
//import org.junit.Test;
//import redis.clients.jedis.Jedis;
//
//import java.io.IOException;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.Assert.*;
//
//public class AnswerDateListFilterTest {
//
//    private SqlSessionFactory factory;
//    private SqlSession sqlSession;
//
//    @Before
//    public void init() throws IOException {
//        factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("SqlMapConfig.xml"));
//        sqlSession = factory.openSession();
//    }
//
//    @Test
//    public void test() throws JsonProcessingException {
//        Jedis jedis = JedisUtil.getJedis();
//
//        String pUuid = "8800514fd47c4308a46e39342b079210";
//        int pid = 16;
//
//        AnswerUserMapper mapper = sqlSession.getMapper(AnswerUserMapper.class);
//        List<AnswerUser> answerUserByPid = mapper.findAnswerUserByPid(16);
//        AnswerResultListFilter answerDateListFilter = new AnswerResultListFilter();
//
//        long st = new Date().getTime();
//        for (AnswerUser answerUser : answerUserByPid) {
//            String subJson = jedis.hget(pUuid, answerUser.getUuid());
//            if (subJson == null) continue;
//            System.out.println("{\"entry\":" + subJson + "}");
//            AnswerResultList answerDataList = JsonUtil.jsonToObject("{\"entry\":" + subJson + "}", AnswerResultList.class);
//
//            List<AnswerData> answerDataList1 = JsonUtil.jsonToList(subJson, AnswerData.class);
//
//            answerDateListFilter.add(answerDataList);
//        }
//        long et = new Date().getTime();
//        System.out.println(et - st);
//
////        List<AnswerData> answerDataList = answerDateListFilter.filterByCol("7ext1sod6do0");
////        for (AnswerData answerData : answerDataList) {
////            System.out.println(answerData);
////        }
//        Map<String, Integer> statistical = answerDateListFilter.statistical("7ext1sod6do0");
//
//        System.out.println(statistical);
//        jedis.close();
//    }
//
//    @Test
//    public void resultPackTest() throws JsonProcessingException {
//        Jedis jedis = JedisUtil.getJedis();
//
//        String pUuid = "8800514fd47c4308a46e39342b079210";
//        int pid = 16;
//
//        AnswerUserMapper mapper = sqlSession.getMapper(AnswerUserMapper.class);
//        List<AnswerUser> answerUserByPid = mapper.findAnswerUserByPid(16);
//        AnswerResultListFilter answerDateListFilter = new AnswerResultListFilter();
//
//        long st = new Date().getTime();
//        for (AnswerUser answerUser : answerUserByPid) {
//            String subJson = jedis.hget(pUuid, answerUser.getUuid());
//            if (subJson == null) continue;
//
//            AnswerResultList answerDataList = new AnswerResultList(JsonUtil.jsonToList(subJson, AnswerData.class));
//
//            answerDateListFilter.add(answerDataList);
//        }
//        long et = new Date().getTime();
//        System.out.println(et - st);
//
////        List<AnswerData> answerDataList = answerDateListFilter.filterByCol("7ext1sod6do0");
////        for (AnswerData answerData : answerDataList) {
////            System.out.println(answerData);
////        }
//        List<Map<String, String>> statistical = answerDateListFilter.resultPack("3q0jz0tr4xy0");
//
//        System.out.println(statistical);
//        jedis.close();
//    }
//}