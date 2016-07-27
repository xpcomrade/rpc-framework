package com.xpcomrade.study.snpzrpc.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by xpcomrade on 2016/7/26.
 * Copyright (c) 2016, xpcomrade@gmail.com All Rights Reserved.
 * Description: TODO(这里用一句话描述这个类的作用). <br/>
 */
public class RpcServerBootstrap {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("spring-snpz-rpc-server.xml");
    }
}
