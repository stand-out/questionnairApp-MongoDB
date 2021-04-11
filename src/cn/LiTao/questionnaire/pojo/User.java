package cn.LiTao.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;

/**
 * @author Devil
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class User {

    private int id;
    private String username;

    @JsonIgnore
    private String password;
    private String headerImagePath;
    private String phoneNumber;
    private String wxOpenId;
    private List<Project> projectList;
}
