package cn.LiTao.questionnaire.utils;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDBUtil {

    private static MongoClient client = new MongoClient();

    public static MongoDatabase getDatabase(String databaseName) {
        return client.getDatabase(databaseName);
    }

    public static MongoDatabase getDatabase() {
        return getDatabase("questionnaire");
    }
}
