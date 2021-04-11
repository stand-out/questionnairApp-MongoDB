package cn.LiTao.questionnaire.utils;

import cn.LiTao.questionnaire.pojo.AnswerData;
import cn.LiTao.questionnaire.pojo.Problem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Devil
 */
public class JsonUtil {

    public static <T> String objectToString(T obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(obj);
    }

    public static <T> T jsonToObject(String json, Class<T> objClass) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, objClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static <T> T jsonToObject(String json, TypeReference<T> valueReference) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, valueReference);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static <E> List<E> jsonToList(String json, Class<E> element) throws JsonProcessingException {
        ArrayList<E> resultList = new ArrayList<>();

        JsonNode node = JsonUtil.getNode(json);

        for (int i = 0; i < node.size(); i++) {
            String subJson = node.get(i).toString();
            E answerData = JsonUtil.jsonToObject(subJson, element);
//            System.out.println(answerData);
            resultList.add(answerData);
        }

        return resultList;
    }

    public static JsonNode getNode(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readTree(json);
    }

}
