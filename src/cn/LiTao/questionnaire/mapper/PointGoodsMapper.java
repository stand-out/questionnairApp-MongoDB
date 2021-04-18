package cn.LiTao.questionnaire.mapper;

import cn.LiTao.questionnaire.pojo.PointGoods;

import java.util.List;

public interface PointGoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PointGoods record);

    int insertSelective(PointGoods record);

    PointGoods selectByPrimaryKey(Integer id);

    List<PointGoods> selectAll();

    int updateByPrimaryKeySelective(PointGoods record);

    int updateByPrimaryKey(PointGoods record);
}