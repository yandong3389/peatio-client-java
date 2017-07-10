package com.law.yuncoin.mapper.base;

import com.law.yuncoin.bean.base.TickerInfo;
import com.law.yuncoin.bean.base.TickerInfoExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TickerInfoMapper {
    int countByExample(TickerInfoExample example);

    int deleteByExample(TickerInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TickerInfo record);

    int insertSelective(TickerInfo record);

    List<TickerInfo> selectByExample(TickerInfoExample example);

    TickerInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TickerInfo record, @Param("example") TickerInfoExample example);

    int updateByExample(@Param("record") TickerInfo record, @Param("example") TickerInfoExample example);

    int updateByPrimaryKeySelective(TickerInfo record);

    int updateByPrimaryKey(TickerInfo record);
}