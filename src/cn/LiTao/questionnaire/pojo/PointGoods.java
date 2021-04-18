package cn.LiTao.questionnaire.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * point_goods
 * @author 
 */
@Data
public class PointGoods implements Serializable {
    private Integer id;

    private String goodsImg;

    private String goodsName;

    private String goodsVaue;

    private Integer price;

    private Integer order;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;
}