package cn.LiTao.questionnaire.pojo;

import java.util.List;

public class AnswerResultList {

    private List<AnswerData> entry;

    public AnswerResultList() {
    }

    public AnswerResultList(List<AnswerData> entry) {
        this.entry = entry;
    }

    public List<AnswerData> getEntry() {
        return entry;
    }

    public void setEntry(List<AnswerData> entry) {
        this.entry = entry;
    }


    public AnswerData get(String field) {
        for (AnswerData answerData : entry) {
            if (field.equals(answerData.getId()))
                return answerData;
        }
        return null;
    }

    public List<AnswerData> getList() {
        return this.entry;
    }

    @Override
    public String toString() {
        return "AnswerDataList{" +
                "entry=" + entry +
                '}';
    }
}
