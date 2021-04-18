package cn.LiTao.questionnaire.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * user_balance_record
 * @author 
 */
@Data
public class UserBalanceRecord implements Serializable {
    private Integer id;

    private Integer uid;

    private Integer type;

    private Integer changeAmount;

    private String remark;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}