package cn.LiTao.questionnaire.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * convert_record
 * @author 
 */
@Data
public class ConvertRecord implements Serializable {
    private Integer id;

    private Integer uid;

    private Integer gid;

    private String goodsName;

    private Integer goodsPrice;

    private Integer status;

    private String remark;

    private String contactNumber;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;
}