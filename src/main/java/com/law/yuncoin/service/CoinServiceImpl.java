package com.law.yuncoin.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bitcoin.market.AbstractMarketApi;
import org.bitcoin.market.MarketApiFactory;
import org.bitcoin.market.bean.Asset;
import org.bitcoin.market.bean.Market;
import org.bitcoin.market.bean.Symbol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.law.yuncoin.bean.StatusInfo;
import com.law.yuncoin.bean.base.TickerInfo;
import com.law.yuncoin.bean.base.TickerInfoExample;
import com.law.yuncoin.common.AccountUtil;
import com.law.yuncoin.mapper.base.TickerInfoMapper;

@Service
public class CoinServiceImpl implements CoinService {

    public static Map<String, String> flags = (Map<String, String>) new HashMap<String, String>();
    
    @Autowired
    private TickerInfoMapper tickerInfoMapper;

    private Logger logger = LoggerFactory.getLogger(CoinServiceImpl.class);

    @Override
    public int saveTickerInfo(TickerInfo tickerInfo) {
        return tickerInfoMapper.insert(tickerInfo);
    }

    public int autoBuyAndSell(Symbol symbol) {

        // 获取账户余额
        Asset asset = getInfo();

        // 获取中间值
        // 保存已挂单信息（买入价、挂单价）
        // 查询挂单信息
        // 计算收益

        return 0;
    }

    /**
     * 取消买单,以免长时间挂着
     */
    
    /**
     * 获取购买值
     */
    private BigDecimal getBuyCny(Symbol symbol, int minute, StatusInfo statusInfo) {

        Date now = new Date();

        TickerInfoExample example = new TickerInfoExample();
        // 过去5分钟
        example.createCriteria().andAtBetween(now, new Date(now.getTime() - minute * 60 * 1000));
        example.setOrderByClause("at asc");

        List<TickerInfo> tickerInfos = tickerInfoMapper.selectByExample(example);

        // 第一个
        BigDecimal first = tickerInfos.get(0).getLast();
        // 最后一个
        BigDecimal last = tickerInfos.get(tickerInfos.size()-1).getLast();
        // 最大值
        BigDecimal max = null;
        // 最小值
        BigDecimal min = null;
        
        for (TickerInfo tickerInfo : tickerInfos) {
            
            BigDecimal tmp = tickerInfo.getLast();
            
            if (tmp.compareTo(min) == -1) {
                min = tmp;
            }
            if (tmp.compareTo(min) == 1) {
                max = tmp;
            }
            
            logger.debug("----------------------------------------------------------------------------");
            logger.debug(String.valueOf(tickerInfo.getLast()));
            logger.debug("----------------------------------------------------------------------------");
        }
        
        // 涨浮
        BigDecimal level = first.divide(last).subtract(new BigDecimal(1));
        
        logger.debug(minute + "分钟内最小值 " + min);
        logger.debug(minute + "分钟内最大值 " + max);
        // 中间值
        BigDecimal result = min.add(max).divide(new BigDecimal(2));
        logger.debug(minute + "分钟内中间值 " + result);
        
        // 趋势
        boolean isUp = true;
        
        // 中间值结合趋势
        // 下滑趋势
        if (level.compareTo(new BigDecimal(0)) == -1) {
            isUp = false;
            level = level.subtract(level).subtract(level);
        }
        
        // TODO 获取最新的委托信息
        // 买一价
        BigDecimal buy1 = null;
        // 卖一价
        BigDecimal sell1 = null;
        
        if (isUp) {
            
            if (level.compareTo(new BigDecimal(0.035)) == -1) {
                result = result.compareTo(sell1) == 0 || result.compareTo(sell1) == 1 ? sell1 : result;
            } else {
                // 几分钟内上涨10%,观望
                new BigDecimal("0");
            }
            
        } else {
            
            if (result.compareTo(level) == 0 || result.compareTo(level) == -1) {
                result = sell1;
            } else {
                // 几分钟内上涨10%,观望
                new BigDecimal("0");
            }
        }
        
        logger.debug(minute + "分钟内 " + (isUp?"上涨了":"下跌了") + level + "%");
        logger.debug("当前买一价 " + buy1);
        logger.debug("当前卖一价 " + sell1);
        logger.debug(minute + "分钟内计算购买值 " + String.format("%.2f", result));
        
        // 保存数据
        statusInfo.setBuy1(buy1);
        statusInfo.setSell1(sell1);
        statusInfo.setUpFlag(isUp);
        statusInfo.setLevel(level);
        
        return new BigDecimal(String.format("%.2f", result));
    }

    /**
     * 获取账户信息
     */
    private Asset getInfo() {
        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
        Asset asset = market.getInfo(AccountUtil.getAppAccount());
        // System.out.println(asset.getAvailableCny());
        // System.out.println(asset.getAvailableEos());
        return asset;
    }
}
