package com.law.yuncoin.tasks;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.bitcoin.market.AbstractMarketApi;
import org.bitcoin.market.MarketApiFactory;
import org.bitcoin.market.bean.CoinOrder;
import org.bitcoin.market.bean.Market;
import org.bitcoin.market.bean.Symbol;
import org.bitcoin.market.bean.SymbolPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.law.yuncoin.bean.base.TickerInfo;
import com.law.yuncoin.common.AccountUtil;
import com.law.yuncoin.service.CoinService;

@Component
@Configurable
@EnableScheduling
public class ScheduledTasks {

    public static int start = 0;

    @Autowired
    private CoinService coinService;

    @Scheduled(fixedRate = 1000 * 30)
    public void reportCurrentTime() {
        System.out.println("Scheduling Tasks Examples: The time is now " + dateFormat().format(new Date()));
    }

    /**
     * 保存记录
     */
    @Scheduled(cron = "*/2 * *  * * * ")
    public void reportCurrentByCron() {

        AbstractMarketApi abstractMarketApi = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
        JSONObject tickerObj = null;
        try {
            tickerObj = abstractMarketApi.ticker(new SymbolPair(Symbol.eos, Symbol.cny));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String at = tickerObj.getString("at");
        JSONObject jsonobj = tickerObj.getJSONObject("ticker");

        long dateTime = Long.parseLong(at);

        TickerInfo tickerInfo = new TickerInfo();
        tickerInfo.setAt(new Date(dateTime * 1000));
        tickerInfo.setBuy(new BigDecimal(jsonobj.getString("buy")));
        tickerInfo.setSell(new BigDecimal(jsonobj.getString("sell")));
        tickerInfo.setHigh(new BigDecimal(jsonobj.getString("high")));
        tickerInfo.setLow(new BigDecimal(jsonobj.getString("low")));
        tickerInfo.setVol(new BigDecimal(jsonobj.getString("vol")));
        tickerInfo.setLast(new BigDecimal(jsonobj.getString("last")));

        int result = coinService.saveTickerInfo(tickerInfo);

        if (result > 0) {
            System.out.println("successed.." + jsonobj.getString("last"));
        }
    }

    /**
     * 自动挂单
     */
    @Scheduled(cron = "*/10 * *  * * * ")
    public void autoBuyAndSell() {

        if (start == 1) {
            coinService.autoBuyAndSell(Symbol.eos);
        }
    }

    // 长时间未处理买单，需判断取消
    // 长时间未处理卖单？？
    // 卖单有偶尔挂单失败的，需要补挂措施
    
//    /**
//     * 自动挂单
//     */
//    @Scheduled(cron = "*/10 * *  * * * ")
//    public void getRunningOrder() {
//
//        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
//        List<CoinOrder> bitOrders = market.getRunningOrders(AccountUtil.getAppAccount(), Symbol.eos);
//
//        for (CoinOrder coinOrder : bitOrders) {
//            System.out.print(coinOrder.getOrderId() + "  " + coinOrder.getOrderSide() + "  "
//                    + coinOrder.getOrderCnyPrice() + "  " + coinOrder.getOrderAmount());
//            System.out.print("  " + coinOrder.getCreateTime());
//            System.out.println();
//        }
//    }

    private SimpleDateFormat dateFormat() {
        return new SimpleDateFormat("HH:mm:ss");
    }

}