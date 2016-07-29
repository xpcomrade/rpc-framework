package com.xpcomrade.study.snpzrpc.services;

import com.xpcomrade.study.snpzrpc.server.RpcService;

/**
 * Created by xpcomrade on 2016/7/25.
 * Copyright (c) 2016, xpcomrade@gmail.com All Rights Reserved.
 * Description: TODO(这里用一句话描述这个类的作用). <br/>
 */
@RpcService(HelloService.class)
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHi(String name) {
        return "Hello," + name;
    }
}
