package cn.LiTao.questionnaire.pojo;

import java.util.Map;

public class AnswerData {
    private String id;
    private Map<String, String> resultMap;

    public AnswerData() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Map<String, String> getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<String, String> resultMap) {
        this.resultMap = resultMap;
    }

    @Override
    public String toString() {
        return "AnswerData{" +
                "id='" + id + '\'' +
                ", resultMap=" + resultMap +
                '}';
    }
}
