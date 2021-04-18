package cn.LiTao.questionnaire.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * project_hongbao
 * @author 
 */
@Data
public class ProjectHongbao implements Serializable {
    private Integer id;

    private Integer projectId;

    private Integer amount;

    private Integer type;

    private Integer number;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}