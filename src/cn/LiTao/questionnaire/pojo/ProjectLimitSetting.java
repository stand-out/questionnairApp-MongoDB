package cn.LiTao.questionnaire.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.*;

/**
 * project_limit_setting
 * @author 
 */
@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectLimitSetting implements Serializable {
    private Integer id;

    private Integer pid;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 用户提交次数限制
     */
    private Integer answerLimit;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}