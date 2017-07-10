package com.law.yuncoin.controller;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.law.yuncoin.bean.User;
import com.law.yuncoin.bean.base.TickerInfo;
import com.law.yuncoin.service.CoinService;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CoinService coinService;
    
    @RequestMapping("/{id}")
    public User view(@PathVariable("id") Long id) {
        User user = new User();
        user.setId(id);
        user.setName("long");
        
        
        TickerInfo tickerInfo = new TickerInfo();
        tickerInfo.setAt(new Date());
        tickerInfo.setBuy(new BigDecimal(11.01));
        tickerInfo.setSell(new BigDecimal(11.02));
        tickerInfo.setHigh(new BigDecimal(11.03));
        tickerInfo.setLow(new BigDecimal(11.04));
        tickerInfo.setVol(new BigDecimal(11.89));
        tickerInfo.setLast(new BigDecimal(11.88));
        
        coinService.saveTickerInfo(tickerInfo);
        
        return user;
    }

    @RequestMapping("/index")
    public ModelAndView view() {
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }
}