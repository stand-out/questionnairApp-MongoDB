package cn.LiTao.questionnaire.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * user_hongbao_record
 * @author 
 */
@Data
public class UserHongbaoRecord implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 问卷id
     */
    private Integer pid;

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 用户姓名（反范式）
     */
    private String userName;

    /**
     * 抢到的金额
     */
    private Integer amount;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}