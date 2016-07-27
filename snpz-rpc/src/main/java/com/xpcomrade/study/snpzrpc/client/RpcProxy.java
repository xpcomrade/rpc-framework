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
public class RpcProxy {

    private String host;
    private int port;

    public RpcProxy(String host, int port){
        this.host = host;
        this.port = port;
    }

    public <T> T create(Class<?> interfaceClass) {

        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                RpcRequest request = new RpcRequest();
                request.setRequestId(UUID.randomUUID().toString());
                request.setInterfaceName(method.getDeclaringClass().getName());
                request.setMethodName(method.getName());
                request.setParameterTypes(method.getParameterTypes());
                request.setParameters(args);

                RpcClient client = new RpcClient(host, port); // 初始化 RPC 客户端
                RpcResponse response = client.send(request); // 通过 RPC 客户端发送 RPC 请求并获取 RPC 响应
                if (response == null) {
                    throw new RuntimeException("response is null");
                }
                // 返回 RPC 响应结果
                if (response.hasException()) {
                    throw response.getException();
                } else {
                    return response.getResult();
                }
            }
        });
    }
}
