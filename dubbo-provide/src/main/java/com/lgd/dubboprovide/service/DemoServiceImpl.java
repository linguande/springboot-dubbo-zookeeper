package com.lgd.dubboprovide.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.lgd.dubboapi.service.DemoService;

@Service
//此service注解为com.alibaba.dubbo.config.annotation.Service，并非spring注解
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String string) {
        String str = "provider :" + string;
        System.out.println(str);
        return str;
    }
}
