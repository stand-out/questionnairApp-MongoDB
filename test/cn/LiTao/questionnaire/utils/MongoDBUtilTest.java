package cn.LiTao.questionnaire.utils;

import cn.LiTao.questionnaire.pojo.AnswerData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class MongoDBUtilTest {

    @Test
    public void getDatabaseTest() throws JsonProcessingException {
        MongoDatabase database = MongoDBUtil.getDatabase();
        MongoCollection<Document> collection = database.getCollection("text");

        String json = "[{\"id\":\"7hf43h88big0\",\"resultMap\":{\"选项1\":\"选项1\"}},{\"id\":\"1x4ebllqei1s\",\"resultMap\":{\"选项1\":\"选项1\",\"选项2\":\"选项2\"}},{\"id\":\"v4qdqia4lj4\",\"resultMap\":{\"答案\":\"fill-in\"}},{\"id\":\"17b3ek5tsi9s\",\"resultMap\":{\"问题1\":\"yes\",\"问题2\":\"1\"}},{\"id\":\"1gj0lodfdusg\",\"resultMap\":{\"问题1\":3}}]";

//        List<AnswerData> answerDataList = JsonUtil.jsonToList(json, AnswerData.class);

        Map<String, Object> map = new HashMap<>();
        map.put("id", "test");
        map.put("data", json);

        Document document = new Document(map);

        collection.insertOne(document);
//        Jedis jedis = JedisUtil.getJedis();
//        jedis.hget()
    }

    @Test
    public void findTest() {
        MongoDatabase database = MongoDBUtil.getDatabase();
        MongoCollection<Document> collection = database.getCollection("text");

        FindIterable<Document> documents = collection.find(new BasicDBObject("id", "test"));

        Document document = documents.first();

        assert document != null;
        System.out.println(document.get("data"));

//        collection.get
    }

}