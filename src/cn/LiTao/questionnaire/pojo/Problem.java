package cn.LiTao.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Problem implements Serializable {

    private static final long serialVersionUID = 2639924745552337620L;

    protected String questionName;
    protected String type;
    protected String title;
    protected String result;
    protected String questionCardClass;
    protected boolean notNull;

    protected List<String> optionList;

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public String getQuestionCardClass() {
        return questionCardClass;
    }

    public void setQuestionCardClass(String questionCardClass) {
        this.questionCardClass = questionCardClass;
    }

    public List<String> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<String> optionList) {
        this.optionList = optionList;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", result='" + result + '\'' +
                ", questionCardClass='" + questionCardClass + '\'' +
                ", notNull=" + notNull +
                ", optionList=" + optionList +
                '}';
    }

}
