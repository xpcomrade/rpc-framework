package com.xpcomrade.study.snpzrpc.util;

import org.junit.Test;

/**
 * Created by xpcomrade on 2016/7/27.
 * Copyright (c) 2016, xpcomrade@gmail.com All Rights Reserved.
 * Description: TODO(这里用一句话描述这个类的作用). <br/>
 */
public class SerializationUtilTest {

    @Test
    public void doSerialize(){
        Phone phone = new  Phone("iphone6s", 6000d);

        byte[] data = SerializationUtil.serialize(phone);

        Phone phones = SerializationUtil.deserialize(data, Phone.class);

        System.out.print(phones.toString());
    }

}
