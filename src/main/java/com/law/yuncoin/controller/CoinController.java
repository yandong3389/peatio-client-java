package com.law.yuncoin.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.law.yuncoin.tasks.ScheduledTasks;


@RestController
@RequestMapping("/coin")
public class CoinController {

    @RequestMapping("/index/{flag}")
    public int view(@PathVariable("flag") int flag) {
        
        ScheduledTasks.start = flag;
        
        return flag;
    }

}