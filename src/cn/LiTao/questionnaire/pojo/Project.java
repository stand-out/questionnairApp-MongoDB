package cn.LiTao.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Date;

/**
 * @author Devil
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Project {

    private int id;
    private String projectName;
    private String projectType;
    private String projectStatus;
    private Date lastModifyTime;
    private String dataUuid;
    private int answerCount;
    private int reward;
    @JsonIgnore
    private User user;

    private Questionnaire questionnaire;
}
