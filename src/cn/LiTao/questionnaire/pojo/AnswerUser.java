package cn.LiTao.questionnaire.pojo;

import javax.xml.stream.events.DTD;
import java.util.Date;
import java.util.List;

public class AnswerUser {

    private int id;
    private String uuid;
    private String answerStatus;
    private Date startTime;
    private Date endTime;
    private String browser;
    private String os;
    private String ip;
    private int pid;

    private List<AnswerData> answerDataList;

    public AnswerUser() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAnswerStatus() {
        return answerStatus;
    }

    public void setAnswerStatus(String answerStatus) {
        this.answerStatus = answerStatus;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public List<AnswerData> getAnswerDataList() {
        return answerDataList;
    }

    public void setAnswerDataList(List<AnswerData> answerDataList) {
        this.answerDataList = answerDataList;
    }

    @Override
    public String toString() {
        return "AnswerUser{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", answerStatus='" + answerStatus + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", browser='" + browser + '\'' +
                ", os='" + os + '\'' +
                ", ip='" + ip + '\'' +
                ", pid=" + pid +
                ", answerDataList=" + answerDataList +
                '}';
    }
}
