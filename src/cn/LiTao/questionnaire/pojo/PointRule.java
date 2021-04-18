package cn.LiTao.questionnaire.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * point_rule
 * @author 
 */
@Data
public class PointRule implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 顺序
     */
    private Integer order;

    /**
     * 规则说明
     */
    private String ruleMsg;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}