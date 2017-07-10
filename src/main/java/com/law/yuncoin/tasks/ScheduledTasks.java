package com.law.yuncoin.tasks;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bitcoin.market.AbstractMarketApi;
import org.bitcoin.market.MarketApiFactory;
import org.bitcoin.market.bean.Market;
import org.bitcoin.market.bean.Symbol;
import org.bitcoin.market.bean.SymbolPair;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.law.yuncoin.bean.base.TickerInfo;
import com.law.yuncoin.service.CoinService;

@Component
@Configurable
@EnableScheduling
public class ScheduledTasks {

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
        tickerInfo.setAt(new Date(dateTime*1000));
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
    @Scheduled(cron = "*/2 * *  * * * ")
    public void autoBuyAndSell() {
        
    }
    
    
    private SimpleDateFormat dateFormat() {
        return new SimpleDateFormat("HH:mm:ss");
    }

}