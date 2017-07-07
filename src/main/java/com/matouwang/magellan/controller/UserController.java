package com.matouwang.magellan.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.matouwang.magellan.bean.User;

@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/{id}")
    public User view(@PathVariable("id") Long id) {
        User user = new User();
        user.setId(id);
        user.setName("long");
        return user;
    }

    @RequestMapping("/index")
    public ModelAndView view() {
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }
}