package org.bitcoin.market;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.bitcoin.market.bean.AppAccount;
import org.bitcoin.market.bean.CoinOrder;
import org.bitcoin.market.bean.Kline;
import org.bitcoin.market.bean.Market;
import org.bitcoin.market.bean.OrderStatus;
import org.bitcoin.market.bean.Symbol;
import org.bitcoin.market.bean.SymbolPair;
import org.junit.Test;

import com.law.yuncoin.common.AccountUtil;

public class PeatioCNYApiTestTest {

    private AppAccount getAppAccount() {
        return AccountUtil.getAppAccount();
    }

//    @Test
//    public void testBuyAndCancel() throws Exception {
//
//        Double amount = 0.01;
//        Double price = 10d;
//        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
//        Long orderId = market.buy(getAppAccount(), amount, price, new SymbolPair(Symbol.eos, Symbol.cny));
//        CoinOrder order = market.getOrder(getAppAccount(), orderId, null);
//        assertNotNull(order);
//        assertEquals(OrderStatus.none, order.getStatus());
//        assertEquals(amount, order.getOrderAmount());
//        assertEquals(new Double("0.0"), order.getProcessedAmount());
//        market.cancel(getAppAccount(), orderId, null);
//        order = market.getOrder(getAppAccount(), orderId, null);
//        assertNotNull(order);
//        assertEquals(OrderStatus.cancelled, order.getStatus());
//    }

    @Test
    public void testSellAndCancel() throws Exception {

        Double amount = 29.97;
        Double price = 11.25; // usd
        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
        Long orderId = market.sell(getAppAccount(), amount, price, new SymbolPair(Symbol.eos, Symbol.cny));
        System.out.println(orderId);
//        CoinOrder order = market.getOrder(getAppAccount(), orderId, null);
//        assertNotNull(order);
//        assertEquals(OrderStatus.none, order.getStatus());
//        assertEquals(amount, order.getOrderAmount());
//        assertEquals(new Double(0.0), order.getProcessedAmount());
//        market.cancel(getAppAccount(), orderId, null);
//        order = market.getOrder(getAppAccount(), orderId, null);
//        assertNotNull(order);
//        assertEquals(OrderStatus.cancelled, order.getStatus());
    }

//    @Test
//    public void testGetInfo() throws Exception {
//        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
//        Asset asset = market.getInfo(getAppAccount());
//        System.out.println(asset.getAvailableCny());
//        System.out.println(asset.getAvailableEos());
//        assertNotNull(asset);
//    }

//    @Test
//    public void testGetOrder() throws Exception {
//
//        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
//        Long orderId = 434669L;
//        BitOrder order = market.getOrder(getAppAccount(), orderId, new SymbolPair(Symbol.eos, Symbol.usd));
//        assertNotNull(order);
//    }

//    @Test
//    public void testGetRunningOrder() throws Exception {
//
//        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
//        List<CoinOrder> bitOrders = market.getRunningOrders(getAppAccount(), Symbol.eos);
//        
//        
//        for (CoinOrder coinOrder : bitOrders) {
//            
//            System.out.print(coinOrder.getOrderId() + "  " + coinOrder.getOrderSide() + "  " + coinOrder.getOrderCnyPrice() + "  " + coinOrder.getOrderAmount());
//            System.out.print("  " + coinOrder.getCreateTime());
//            System.out.println();
//        }
//        
//        
//        assertTrue(bitOrders.size() > 0);
//    }


//    @Test
//    public void testGetKlineDate() throws Exception {
//        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
//        List<Kline> klines = market.getKlineDate(Symbol.btc);
//        for (Kline kline : klines) {
//            convertToUsd(market, kline);
//        }
//        
//          printData(klines);
//    
//        assertTrue(klines.size() > 0);
//
//    }

//    @Test
//    public void testGetKline5Min() throws Exception {
//        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
//        List<Kline> klines = market.getKline5Min(Symbol.eos);
//        for (Kline kline : klines) {
//            convertToUsd(market, kline);
//        }
//        printData(klines);
//        assertTrue(klines.size() > 0);
// 
//    }

//    @Test
//    public void testGetKline1Min() throws Exception {
//        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
//        List<Kline> klines = market.getKline1Min(Symbol.eos);
//        for (Kline kline : klines) {
//            convertToUsd(market, kline);
//        }
//        printData(klines);
//        assertTrue(klines.size() > 0);
//    }

//    @Test
//    public void testTicker() throws Exception {
//        
//        while(true){
//            
//            AbstractMarketApi abstractMarketApi = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
//            JSONObject tickerObj = abstractMarketApi.ticker(new SymbolPair(Symbol.eos, Symbol.cny));
//            
////        System.out.println(tickerObj.toJSONString());
//            
//            String at = tickerObj.getString("at");
//            JSONObject jsonobj = tickerObj.getJSONObject("ticker");
//            
//            long dateTime = Long.parseLong(at);
//            
//            DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//            String dateStr = df.format(new Date(dateTime*1000));
//            
//            System.out.print(dateStr);
//            System.out.print("\t");
//            System.out.print(jsonobj.getString("buy"));
//            System.out.print("\t");
//            System.out.print(jsonobj.getString("sell"));
//            System.out.print("\t");
//            System.out.print(jsonobj.getString("last"));
//            System.out.println();
////        System.out.print(jsonobj.toJSONString());
//            
//            
//            Thread.sleep(1000l);
////            assertNotNull(tickerObj);
//        }
//
//
//    }

//    @Test
//    public void testDepth() throws Exception {
//
//        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
//        JSONObject depth = market.get_depth(new SymbolPair(Symbol.eos, Symbol.cny), true);
//        
//        JSONArray jsonarrasks = depth.getJSONArray("asks");
//        JSONArray jsonarrbids = depth.getJSONArray("bids");
//        
//        for (int i = 0; i < jsonarrasks.size(); i++) {
//            
//            JSONObject obj = jsonarrasks.getJSONObject(i);
//            JSONObject obj2 = jsonarrbids.getJSONObject(i);
//            System.out.print(obj.getString("amount") + " " + obj.getString("price"));
//            System.out.print("\t");
//            System.out.print(obj2.getString("amount") + " " + obj2.getString("price"));
//            System.out.println();
//            
//        }
        
//        System.out.println(depth.getJSONArray("asks").toJSONString());
//        System.out.println(depth.getJSONArray("bids").toJSONString());
        
//        assertTrue(depth.containsKey("asks"));
//        assertTrue(depth.containsKey("bids"));

//    }

    private void printData(List<Kline> klines){
      
        klines = klines.subList(klines.size()-10, klines.size());
        
      System.out.print("open \t close \t high \t low \t volume \t vwap \t datetime \r\n");
      for (Kline kline : klines) {
          System.out.print(kline.getOpen());
          System.out.print("\t");
          System.out.print(kline.getClose());
          System.out.print("\t");
          System.out.print(kline.getHigh());
          System.out.print("\t");
          System.out.print(kline.getLow());
          System.out.print("\t");
          System.out.print(kline.getVolume());
          System.out.print("\t");
          System.out.print(kline.getVwap());
          System.out.print("\t");
          System.out.print(kline.getDatetime());
          System.out.println();
      }
        
    }
    private void convertToUsd(AbstractMarketApi market, Kline kline) {
//        if (!market.getMarket().isUsd()) {
//            kline.setOpen(FiatConverter.toUsd(kline.getOpen(), kline.getDatetime()));
//            kline.setHigh(FiatConverter.toUsd(kline.getHigh(), kline.getDatetime()));
//            kline.setLow(FiatConverter.toUsd(kline.getLow(), kline.getDatetime()));
//            kline.setClose(FiatConverter.toUsd(kline.getClose(), kline.getDatetime()));
//            kline.setVwap(FiatConverter.toUsd(kline.getVwap(), kline.getDatetime()));
//        }
    }
    
    /**
     * 
     * 总额
     * 余额
     * 
     * 
     * 两种模式.. 超过0.5%即可交易
     * 
     * 数据库存储30分钟内趋势 Ticker
     * 
     * 计算获取最近5分钟\10分钟内的中间值，最大值最小值
     * 
     * 总额分20份,每低于平均值0.5%，追加投额
     * 
     * 市场深度，买三、卖三
     * 最新买、卖委单
     * 20   21
     * 
     * 
     * 挂单 购买后，直接+1% 挂单
     * 
     * 
     * 5分钟kline
var m=10000;
var total = 0;
var tmp = m/100*0.5;
for(var int = 0;int<1000;int++){
    console.info("tmp is " + tmp + "total is " + total);
    if(total<=m){
        tmp+=m/100*0.5;total += tmp;
    }
}
console.info("total is " + total);
     * 
     * 
     */
    
    
    
}
