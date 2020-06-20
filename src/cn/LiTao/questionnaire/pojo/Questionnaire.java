package cn.LiTao.questionnaire.pojo;

import java.io.Serializable;
import java.util.List;

public class Questionnaire implements Serializable {

    private static final long serialVersionUID = 2050771124132576320L;

    private String title;
    private String describe;
    private List<Problem> questionList;
    private String endDescribe;
    private String password;
    private int ipCountLimit;

    public Questionnaire() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public List<Problem> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Problem> questionList) {
        this.questionList = questionList;
    }

    public String getEndDescribe() {
        return endDescribe;
    }

    public void setEndDescribe(String endDescribe) {
        this.endDescribe = endDescribe;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIpCountLimit() {
        return ipCountLimit;
    }

    public void setIpCountLimit(int ipCountLimit) {
        this.ipCountLimit = ipCountLimit;
    }

    public void addQuestion(Problem problem) {
        this.questionList.add(problem);
    }


}
