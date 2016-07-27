package com.xpcomrade.study.snpzrpc.client;

import com.xpcomrade.study.snpzrpc.sample.HelloService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by xpcomrade on 2016/7/26.
 * Copyright (c) 2016, xpcomrade@gmail.com All Rights Reserved.
 * Description: TODO(这里用一句话描述这个类的作用). <br/>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-snpz-rpc-client.xml")
public class HelloServiceTest {

    @Autowired
    private RpcProxy rpcProxy;

    @Test
    public void helloTest() {
        System.out.println("call.....");
        HelloService helloService = rpcProxy.create(HelloService.class);
        String result = helloService.sayHi("World");
        Assert.assertEquals("Hello! World", result);
    }
}
