package com.lgd.dubboconsumer.controller;

import com.lgd.dubboapi.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: consumer
 * @author: linguande
 * @create: 2018-05-02 17:53
 **/
@Controller
public class DemoController {

    @Autowired
    private DemoService demoService;

    @RequestMapping(value="sayHello")
    @ResponseBody
    public void sayHello(HttpServletRequest request, HttpServletResponse response){
        String string = "hello";
        System.out.println("consumer:"+string);
        demoService.sayHello(string);
    }


}
