package com.xpcomrade.study.snpzrpc.codec;

import com.xpcomrade.study.snpzrpc.util.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by xpcomrade on 2016/7/25.
 * Copyright (c) 2016, xpcomrade@gmail.com All Rights Reserved.
 * Description: (RPC编码). <br/>
 */
public class RpcEncoder extends MessageToByteEncoder {

    private Class<?> genericClass;

    public RpcEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    public void encode(ChannelHandlerContext channelHandlerContext, Object in, ByteBuf out) throws Exception {
        if (genericClass.isInstance(in)) {
            byte[] data = SerializationUtil.serialize(in);
            out.writeInt(data.length);
            out.writeBytes(data);
        } else {
            throw new IllegalArgumentException("编码出错");
        }
    }
}
