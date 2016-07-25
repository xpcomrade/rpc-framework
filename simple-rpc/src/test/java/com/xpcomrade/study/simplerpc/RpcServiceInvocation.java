package com.xpcomrade.study.simplerpc;

import com.xpcomrade.study.simplerpc.core.RPCService;
import com.xpcomrade.study.simplerpc.services.HelloService;
/**
 * Created by wangzp
 * Date: 2015/11/19 14:45
 * Copyright (c) 2015, xpcomrade@gmail.com All Rights Reserved.
 * Description: TODO(用一句话描述此文件的作用). <br/>
 */
public class RpcServiceInvocation {
    public static void main(String[] args) throws Exception {
        HelloService service = RPCService.refer(HelloService.class, "127.0.0.1", 8888);
        for (int i = 0; i < Integer.MAX_VALUE; i ++) {
            String hello = service.hello("World" + i);
            System.out.println(hello);
            Thread.sleep(1000);
        }
    }
}
