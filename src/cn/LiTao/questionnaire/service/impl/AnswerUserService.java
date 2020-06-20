package cn.LiTao.questionnaire.service.impl;

import cn.LiTao.questionnaire.dependent.QuestionnaireContainer;
import cn.LiTao.questionnaire.factory.AnswerResultFactory;
import cn.LiTao.questionnaire.mapper.AnswerUserMapper;
import cn.LiTao.questionnaire.mapper.ProjectMapper;
import cn.LiTao.questionnaire.pojo.*;
import cn.LiTao.questionnaire.service.MyService;
import cn.LiTao.questionnaire.utils.AnswerResultListFilter;
import cn.LiTao.questionnaire.utils.JsonUtil;
import cn.LiTao.questionnaire.utils.MongoDBUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.ibatis.session.SqlSession;
import org.bson.Document;

import javax.servlet.ServletContext;
import java.util.*;

public class AnswerUserService extends MyService {

    private AnswerUserMapper answerUserMapper;


    @Override
    protected synchronized SqlSession createSqlSession() {
        System.out.println("开始创建SqlSession");
        sqlSession = sqlSessionFactory.openSession();
        answerUserMapper = sqlSession.getMapper(AnswerUserMapper.class);

        return sqlSession;
    }

    public boolean addAnswerUser(AnswerUser user, String uuid, String data) {

        try {
            //        用户信息存数据库
            SqlSession sqlSession = createSqlSession();
            answerUserMapper.insertAnswerUser(user);
            this.sqlSession.commit();
            closeSqlSession(sqlSession);

//            封装数据
            Map<String, Object> map = new HashMap<>();
            map.put("uuid", user.getUuid());
            map.put("data", data);
//        用户填写数据存mongoDB
            MongoDatabase database = MongoDBUtil.getDatabase();
            MongoCollection<Document> collection = database.getCollection(uuid);
            collection.insertOne(new Document(map));

            return true;
        }catch (Exception e) {
            return false;
        }

    }

    public AnswerUser findAnswerDateById(int id, String pUuid) {
        SqlSession sqlSession = createSqlSession();
        AnswerUser answerUser = answerUserMapper.findAnswerUserById(id);
        closeSqlSession(sqlSession);

        String aUuid = answerUser.getUuid();

        MongoDatabase database = MongoDBUtil.getDatabase();
//        打开pUuid对应集合
        MongoCollection<Document> collection = database.getCollection(pUuid);
        Document document = collection.find(new BasicDBObject("uuid", aUuid)).first();
        if (document == null) return null;
        String jsonData = (String) document.get("data");


//        Jedis jedis = JedisUtil.getJedis();
//        String jsonData = jedis.hget(pUuid, aUuid);
//        jedis.close();

        System.out.println(jsonData);

        try {

            List<AnswerData> answerDataList = JsonUtil.jsonToList(jsonData, AnswerData.class);
            answerUser.setAnswerDataList(answerDataList);

            return answerUser;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<AnswerUser> findAnswerUserByPid(int pid) {

        SqlSession sqlSession = createSqlSession();
        List<AnswerUser> answerUserByPid = answerUserMapper.findAnswerUserByPid(pid);
        closeSqlSession(sqlSession);

        return answerUserByPid;
    }

    public int countAnswer(int pid) {

        SqlSession sqlSession = createSqlSession();
        int count = answerUserMapper.countAnswerByPid(pid);
        closeSqlSession(sqlSession);

        return count;
    }

//    统计回答
    public Map<String, String> getAllAnswerCount(String pUuid, int pid, ServletContext servletContext) throws Exception {
//        Jedis jedis = JedisUtil.getJedis();
        MongoDatabase database = MongoDBUtil.getDatabase();
//        打开pUuid对应集合
        MongoCollection<Document> collection = database.getCollection(pUuid);


        SqlSession sqlSession = createSqlSession();

        AnswerResultListFilter answerDateListFilter = new AnswerResultListFilter();
        Map<String, String> answerCount = new HashMap<>();

//        查出所有回答pid项目的用户
        List<AnswerUser> answerUserByPid = answerUserMapper.findAnswerUserByPid(pid);

//        获取容器
        QuestionnaireContainer questionnaireContainer = (QuestionnaireContainer) servletContext.getAttribute("questionnaireContainer");
//        从容器中取到问卷数据
        Questionnaire questionnaire = questionnaireContainer.getQuestionnaire(pUuid);

//        根据答题用户的uuid从redis中取出回答数据
        for (AnswerUser answerUser : answerUserByPid) {

//            取到json字符串
            Document document = collection.find(new BasicDBObject("uuid", answerUser.getUuid())).first();
            if (document == null) continue;

            String subJson = (String) document.get("data");
            if (subJson == null) continue;

//            封装到AnswerResultList中
            AnswerResultList answerDataList = new AnswerResultList(JsonUtil.jsonToList(subJson, AnswerData.class));

            answerDateListFilter.add(answerDataList);
        }
//        获取问卷下所有题目
        List<Problem> questionList = questionnaire.getQuestionList();


        if (questionList != null) {
            AnswerResultFactory answerResultFactory = new AnswerResultFactory();
            for (Problem problem : questionList) {
//            Map<String, String> tempMap = new HashMap<>();

                String problemId = problem.getQuestionName();
                List<AnswerData> answerDataList = answerDateListFilter.filterByCol(problemId);
                String resultStr = answerResultFactory.getResultStr(problem.getType(), new AnswerResultList(answerDataList));

                answerCount.put(problemId, resultStr);
//            answerCount.add(tempMap);
            }
        }



//        jedis.close();
        closeSqlSession(sqlSession);
        return answerCount;
    }

}
