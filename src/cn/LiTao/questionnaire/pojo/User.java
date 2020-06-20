package cn.LiTao.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class User {

    private int id;
    private String username;

    @JsonIgnore
    private String password;
    private String headerImagePath;
    private String phoneNumber;
    private List<Project> projectList;

    public User() {
    }

    public User(String username, String password, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeaderImagePath() {
        return headerImagePath;
    }

    public void setHeaderImagePath(String headerImagePath) {
        this.headerImagePath = headerImagePath;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", headerImagePath='" + headerImagePath + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
