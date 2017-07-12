package com.law.yuncoin.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.bitcoin.market.AbstractMarketApi;
import org.bitcoin.market.MarketApiFactory;
import org.bitcoin.market.bean.Asset;
import org.bitcoin.market.bean.CoinOrder;
import org.bitcoin.market.bean.Market;
import org.bitcoin.market.bean.Symbol;
import org.bitcoin.market.bean.SymbolPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.law.yuncoin.bean.StatusInfo;
import com.law.yuncoin.bean.base.TickerInfo;
import com.law.yuncoin.bean.base.TickerInfoExample;
import com.law.yuncoin.common.AccountUtil;
import com.law.yuncoin.mapper.base.TickerInfoMapper;

@Service
public class CoinServiceImpl implements CoinService {

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
        // 获取计算结果信息,币种、分钟、接受浮动比例范围
        StatusInfo statusInfo = getBuyCny(symbol, 3, 0.015);
        
        if (statusInfo == null) {
            return 0;
        }
        
        double amount = 2d;
        double buyPrice = Double.valueOf(statusInfo.getPrice().toString());
        
        double total = buyPrice * amount;
        
        if (asset.getAvailableCny() < total) {
            logger.error("cny余额不足....");
            return 0;
        }
        
        // +价挂单
        CoinOrder coinBuyOrder = buy(buyPrice, amount);

        // 挂单失败
        if (coinBuyOrder == null) {
            logger.info("下单失败....购买价：" + buyPrice + ", 个数：" + amount);
            return 0;
        } else {
            logger.info("下单成功....购买价：" + buyPrice + ", 个数：" + amount);
        }
        
        // 卖价加0.4个点（成本0.2个点）
        double sellPrice = buyPrice + (buyPrice * 0.004);
        double sellAmount = amount - (amount * 0.001);
        
//        BigDecimal bg = new BigDecimal(sellAmount);  
//        sellAmount = bg.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
        
        System.err.println(sellAmount);
        try {
            Thread.sleep(500l);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        CoinOrder coinSellOrder = sell(sellPrice, sellAmount);
        
        if (coinSellOrder == null) {
            logger.info("下单失败重试1....卖价：" + sellPrice + ", 个数：" + sellAmount);
            coinSellOrder = sell(sellPrice, sellAmount);
        }
        
        if (coinSellOrder == null) {
            logger.info("下单失败重试2....卖价：" + sellPrice + ", 个数：" + sellAmount);
            coinSellOrder = sell(sellPrice, sellAmount);
        }
        
        if (coinSellOrder == null) {
            logger.info("再次下单失败....卖价：" + sellPrice + ", 个数：" + sellAmount);
            return 0;
        } else {
            logger.info("下单成功....卖价：" + sellPrice + ", 个数：" + sellAmount);
        }
        
        return 1;
    }

    /**
     * 挂卖单
     */
    private CoinOrder sell(Double price, Double amount){
        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
        Long orderId = market.sell(AccountUtil.getAppAccount(), amount, price, new SymbolPair(Symbol.eos, Symbol.cny));
        if (orderId == -1) {
            return null;
        }
        CoinOrder order = market.getOrder(AccountUtil.getAppAccount(), orderId, null);
        return order;
    }
    
    /**
     * 挂买单
     */
    private CoinOrder buy(Double price, Double amount) {
        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
        Long orderId = market.buy(AccountUtil.getAppAccount(), amount, price, new SymbolPair(Symbol.eos, Symbol.cny));
        if (orderId == -1) {
            return null;
        }
        CoinOrder order = market.getOrder(AccountUtil.getAppAccount(), orderId, null);
        return order;
    }
    
    /**
     * 获取购买值
     */
    private StatusInfo getBuyCny(Symbol symbol, int minute, double paramLevel) {

        Date now = new Date();

        TickerInfoExample example = new TickerInfoExample();
        // 过去5分钟
        example.createCriteria().andAtBetween(new Date(now.getTime() - minute * 60 * 1000), now);
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
        
        // TODO ETH趋势
        
        for (TickerInfo tickerInfo : tickerInfos) {
            
            BigDecimal tmp = tickerInfo.getLast();
            
            if (min == null || tmp.compareTo(min) == -1) {
                min = tmp;
            }
            if (max == null || tmp.compareTo(max) == 1) {
                max = tmp;
            }
            
//            logger.debug("----------------------------------------------------------------------------");
//            logger.debug(String.valueOf(tickerInfo.getLast()));
//            logger.debug("----------------------------------------------------------------------------");
        }
        
        // 涨浮
        BigDecimal level = first.divide(last, 5, RoundingMode.HALF_UP).subtract(new BigDecimal(1));
        
        // 中间值
        BigDecimal result = min.add(max).divide(new BigDecimal(2), 3, RoundingMode.HALF_UP);
        
        // 趋势
        boolean isUp = true;
        
        // 中间值结合趋势
        // 下滑趋势
        if (level.compareTo(new BigDecimal(0)) == -1) {
            isUp = false;
            level = level.subtract(level).subtract(level);
        }
        
        // 获取最新的委托信息
        JSONObject depth = getDepth(symbol);
        
        // 买一价
        BigDecimal buy1 = depth.getJSONArray("bids").getJSONObject(0).getBigDecimal("price");
        // 卖一价
        BigDecimal sell1 = depth.getJSONArray("asks").getJSONObject(0).getBigDecimal("price");
        
        logger.info(minute + "分钟内最小值 " + min + "  最大值 " + max + "  中间值 " + result);
        logger.info(minute + "分钟内 " + (isUp?"上涨了":"下跌了") + level + "%" + "  当前买一价 " + buy1 + "  当前卖一价   " + sell1);
        
        if (isUp) {
            
            if (level.compareTo(new BigDecimal(paramLevel)) == -1) {
                result = result.compareTo(sell1) == 0 || result.compareTo(sell1) == 1 ? sell1 : result;
            } else {
                // 几分钟内上涨超过3.5%,观望
                logger.info("几分钟内上涨超过" + paramLevel + ",观望...");
                return null;
            }
            
        } else {
            
            if (new BigDecimal(paramLevel).compareTo(level) == 0 || new BigDecimal(paramLevel).compareTo(level) == 1) {
                result = sell1;
            } else {
                
                // 几分钟内下跌超过3.5%,观望
                logger.info("几分钟内下跌超过" + paramLevel + ",观望...");
                return null;
            }
        }
        
        logger.info(minute + "分钟内计算购买值 " + String.format("%.2f", result));
        
        StatusInfo statusInfo = new StatusInfo();
        // 保存数据
        statusInfo.setBuy1(buy1);
        statusInfo.setSell1(sell1);
        statusInfo.setUpFlag(isUp);
        statusInfo.setLevel(level);
        statusInfo.setPrice(new BigDecimal(String.format("%.2f", result)));
        
        return statusInfo;
    }

    /**
     * 获取账户信息
     */
    private Asset getInfo() {
        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
        Asset asset = market.getInfo(AccountUtil.getAppAccount());
        return asset;
    }
    
    /**
     * 获取市场深度
     */
    private JSONObject getDepth(Symbol symbol){
        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
        JSONObject depth = market.get_depth(new SymbolPair(symbol, Symbol.cny), true);
        return depth;
    }
    
    /**
     * 获取已挂单
     */
    private List<CoinOrder> getRunningOrder(Symbol symbol) {
        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
        return market.getRunningOrders(AccountUtil.getAppAccount(), symbol);
    }
}
