package com.lingd.springbootdubboconsumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lgd.dubboapi.service.DemoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: demo
 * @author: linguande
 * @create: 2018-05-04 12:01
 **/
@Controller
public class DemoController {

    @Reference
    private DemoService demoService;

    @RequestMapping(value="sayHello")
    @ResponseBody
    public String sayHello(HttpServletRequest request, HttpServletResponse response){
        String string = "consumer : hello";
        System.out.println(string);
        string = string + "<br>" + demoService.sayHello("hello");
        return string;
    }


}
