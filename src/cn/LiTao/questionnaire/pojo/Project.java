package cn.LiTao.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class Project {

    private int id;
    private String projectName;
    private String projectType;
    private String projectStatus;
    private Date lastModifyTime;
    private String dataUuid;
    @JsonIgnore
    private User user;

    private Questionnaire questionnaire;

    public Project() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public String getDataUuid() {
        return dataUuid;
    }

    public void setDataUuid(String dataUuid) {
        this.dataUuid = dataUuid;
    }

    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
                ", projectType='" + projectType + '\'' +
                ", projectStatus='" + projectStatus + '\'' +
                ", lastModifyTime=" + lastModifyTime +
                ", dataUuid='" + dataUuid + '\'' +
                ", user=" + user +
                '}';
    }
}
