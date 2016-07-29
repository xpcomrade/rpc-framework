package com.xpcomrade.study.snpzrpc.client;

import com.xpcomrade.study.snpzrpc.bean.RpcRequest;
import com.xpcomrade.study.snpzrpc.bean.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * Created by xpcomrade on 2016/7/26.
 * Copyright (c) 2016, xpcomrade@gmail.com All Rights Reserved.
 * Description: TODO(这里用一句话描述这个类的作用). <br/>
 */
public class RpcProxy<T> implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
