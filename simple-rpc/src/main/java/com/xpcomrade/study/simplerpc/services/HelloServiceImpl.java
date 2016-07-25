package com.xpcomrade.study.simplerpc.services;

/**
 * Created by wangzp
 * Date: 2015/11/19 14:41
 * Copyright (c) 2015, xpcomrade@gmail.com All Rights Reserved.
 * Description: TODO(用一句话描述此文件的作用). <br/>
 */
public class HelloServiceImpl implements HelloService {
    public String hello(String name) {
        return "Hello " + name;
    }
}
