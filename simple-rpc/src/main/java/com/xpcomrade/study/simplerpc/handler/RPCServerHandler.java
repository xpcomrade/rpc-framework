package com.xpcomrade.study.simplerpc.handler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * Created by wangzp
 * Date: 2015/11/19 17:24
 * Copyright (c) 2015, xpcomrade@gmail.com All Rights Reserved.
 * Description: TODO(用一句话描述此文件的作用). <br/>
 */
public class RPCServerHandler implements Runnable {

    AsynchronousServerSocketChannel serverSocketChannel;

    public RPCServerHandler (String host, int port) {
        try {
            serverSocketChannel = AsynchronousServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(host, port));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        serverSocketChannel.accept(this, new CompletionHandler<AsynchronousSocketChannel, RPCServerHandler>() {
            public void completed(AsynchronousSocketChannel result, RPCServerHandler attachment) {
                attachment.serverSocketChannel.accept(attachment, this);
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                result.read(buffer, buffer, new ReadCompletionHandler(result));
            }

            public void failed(Throwable exc, RPCServerHandler attachment) {
                  exc.printStackTrace();
            }
        });

    }
}
