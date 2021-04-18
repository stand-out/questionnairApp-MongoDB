package cn.LiTao.questionnaire.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.*;

/**
 * project_mode_setting
 * @author 
 */
@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectModeSetting implements Serializable {
    private Integer id;

    /**
     * 问卷id
     */
    private Integer pid;

    /**
     * 实名模式 0：false 1: true
     */
    private Integer realMode;

    /**
     * 记录用户信息
     */
    private Integer recordUserInfo;

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