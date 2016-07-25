package com.xpcomrade.study.simplerpc;

import com.xpcomrade.rpc.core.RPCService;
import com.xpcomrade.rpc.services.HelloService;
import com.xpcomrade.rpc.services.HelloServiceImpl;

/**
 * Created by wangzp
 * Date: 2015/11/19 14:42
 * Copyright (c) 2015, xpcomrade@gmail.com All Rights Reserved.
 * Description: TODO(用一句话描述此文件的作用). <br/>
 */
public class RpcProvider {
    public static void main(String[] args) throws Exception {
        HelloService service = new HelloServiceImpl();
        RPCService.register(service, 8888);
    }
}
