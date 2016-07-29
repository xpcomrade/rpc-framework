package com.xpcomrade.study.snpzrpc.server;

import com.xpcomrade.study.snpzrpc.bean.RpcRequest;
import com.xpcomrade.study.snpzrpc.bean.RpcResponse;
import com.xpcomrade.study.snpzrpc.codec.RpcDecoder;
import com.xpcomrade.study.snpzrpc.codec.RpcEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xpcomrade on 2016/7/25.
 * Copyright (c) 2016, xpcomrade@gmail.com All Rights Reserved.
 * Description: (注册服务). <br/>
 */
public class RpcServer implements ApplicationContextAware, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);

    private String host;

    private int port;

    public RpcServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private Map<String, Object> handlerMap = new HashMap<>(); // 存放接口名与服务对象之间的映射关系

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        Map<String, Object> serviceBeanMap = ctx.getBeansWithAnnotation(RpcService.class);
        if (serviceBeanMap != null && !serviceBeanMap.isEmpty()) {
            for (Object serviceBean : serviceBeanMap.values()) {
                RpcService rpcService = serviceBean.getClass().getAnnotation(RpcService.class);
                String serviceName = rpcService.value().getName();
                handlerMap.put(serviceName, serviceBean);
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) throws Exception {
                    ChannelPipeline pipeline = channel.pipeline();
                    pipeline.addLast(new LengthFieldBasedFrameDecoder(65536,0,4,0,0));
                    pipeline.addLast(new RpcDecoder(RpcRequest.class));
                    pipeline.addLast(new RpcEncoder(RpcResponse.class));
                    pipeline.addLast(new RpcServerHandler(handlerMap));
                }
            });
            bootstrap.option(ChannelOption.SO_BACKLOG, 128);
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            //bootstrap.childOption(ChannelOption.TCP_NODELAY, true);

            ChannelFuture future = bootstrap.bind(host, port).sync();
            logger.debug("server started on port {}", port);
            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
