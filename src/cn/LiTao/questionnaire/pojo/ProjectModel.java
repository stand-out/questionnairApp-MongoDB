package cn.LiTao.questionnaire.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * project_model
 * @author 
 */
@Data
public class ProjectModel implements Serializable {
    private Integer id;

    /**
     * 模板类型id
     */
    private Integer mtid;

    /**
     * 模板对应的问卷项目id
     */
    private Integer pid;

    /**
     * 模板对应的问卷项目的UUID
     */
    private String pUuid;

    /**
     * 模板名称
     */
    private String modelTitle;

    /**
     * 排序序号
     */
    private Integer order;

    private static final long serialVersionUID = 1L;
}