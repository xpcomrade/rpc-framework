package com.xpcomrade.study.snpzrpc.client;

import com.xpcomrade.study.snpzrpc.bean.RpcRequest;
import com.xpcomrade.study.snpzrpc.bean.RpcResponse;
import com.xpcomrade.study.snpzrpc.codec.RpcDecoder;
import com.xpcomrade.study.snpzrpc.codec.RpcEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xpcomrade on 2016/7/26.
 * Copyright (c) 2016, xpcomrade@gmail.com All Rights Reserved.
 * Description: (RPC 客户端（用于发送 RPC 请求）). <br/>
 */
public class RpcClient extends SimpleChannelInboundHandler<RpcResponse> {

    private static final Logger logger = LoggerFactory.getLogger(RpcClient.class);

    private String host;

    private int port;

    private RpcResponse response;

    public RpcClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponse rpcResponse) throws Exception {
        this.response = response;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("client caught exception", cause);
        ctx.close();
    }

    public RpcResponse send(RpcRequest request) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel channel) throws Exception {
                    ChannelPipeline pipeline = channel.pipeline();
                    pipeline.addLast(new RpcEncoder(RpcRequest.class)); // 将 RPC 请求进行编码（为了发送请求）
                    pipeline.addLast(new RpcDecoder(RpcResponse.class));  // 将 RPC 响应进行解码（为了处理响应）
                    pipeline.addLast(RpcClient.this); // 使用 RpcClient 发送 RPC 请求
                }
            });
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture future = bootstrap.connect(host, port).sync();

            Channel channel = future.channel();
            channel.writeAndFlush(request).sync();
            channel.closeFuture().sync();

            return response;
        } finally {
            group.shutdownGracefully();
        }
    }
}
