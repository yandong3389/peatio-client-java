package com.law.yuncoin.service;

import org.bitcoin.market.bean.Symbol;

import com.law.yuncoin.bean.base.TickerInfo;

public interface CoinService {

    public int saveTickerInfo(TickerInfo tickerInfo);
    
    public int autoBuyAndSell(Symbol symbol);
}
