package com.lingd.springbootdubboprovider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.lgd.dubboapi.service.DemoService;

/**
 * @description: demoServiceImpl
 * @author: linguande
 * @create: 2018-05-04 11:59
 **/
@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String string) {
        String str = "provider :" + string;
        System.out.println(str);
        return str;
    }
}
