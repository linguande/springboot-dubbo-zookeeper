package com.lgd.dubboprovide.service;

import com.lgd.dubboapi.service.DemoService;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public void sayHello(String string) {
        System.out.println("provide:"+string);
    }
}
