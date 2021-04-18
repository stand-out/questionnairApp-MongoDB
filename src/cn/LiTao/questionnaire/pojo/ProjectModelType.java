package cn.LiTao.questionnaire.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * project_model_type
 * @author 
 */
@Data
public class ProjectModelType implements Serializable {
    private Integer id;

    private String typeName;

    private String typeImg;

    private Integer order;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;
}