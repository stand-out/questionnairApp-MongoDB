package cn.LiTao.questionnaire.utils;

import cn.LiTao.questionnaire.pojo.AnswerData;
import cn.LiTao.questionnaire.pojo.AnswerResultList;
import cn.LiTao.questionnaire.pojo.AnswerUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnswerResultListFilter {

    private List<AnswerResultList> entryList;

    public AnswerResultListFilter() {
        this.entryList = new ArrayList<>();
    }

    public void add(AnswerResultList list) {
        this.entryList.add(list);
    }

    public List<AnswerResultList> getEntry() {
        return entryList;
    }

    public void setEntry(List<AnswerResultList> entry) {
        this.entryList = entry;
    }


    public List<AnswerData> filterByCol(String field) {
        List<AnswerData> tempList = new ArrayList<>();

        for (AnswerResultList enter: this.entryList) {
            tempList.add(enter.get(field));
        }

        return tempList;
    }

    public Map<String, Integer> statistical(String field) {
        List<AnswerData> answerDataList = this.filterByCol(field);
        return statistical(answerDataList);
    }

    public static Map<String, Integer> statistical(List<AnswerData> answerDataList) {

        HashMap<String, Integer> hm = new HashMap<>();
        for (AnswerData answerData : answerDataList) {
            if (answerData != null) {
                for (Map.Entry<String, String> ResultEntry : answerData.getResultMap().entrySet()) {
                    hm.merge(ResultEntry.getKey(), 1, Integer::sum);
                }
            }

        }

        return hm;
    }

    public List<Map<String, String>> resultPack(String field) {
        List<AnswerData> answerDataList = this.filterByCol(field);
        return resultPack(answerDataList);
    }

    public static List<Map<String, String>> resultPack(List<AnswerData> answerDataList) {

        List<Map<String, String>> mapList = new ArrayList<>();

        for (AnswerData answerData : answerDataList) {
            if (answerData == null)
                continue;
            mapList.add(answerData.getResultMap());
        }

        return mapList;
    }
}
