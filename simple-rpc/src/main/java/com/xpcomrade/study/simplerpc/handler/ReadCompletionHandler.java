package com.xpcomrade.study.simplerpc.handler;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * Created by wangzp
 * Date: 2015/11/19 16:42
 * Copyright (c) 2015, xpcomrade@gmail.com All Rights Reserved.
 * Description: TODO(用一句话描述此文件的作用). <br/>
 */
public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

    private AsynchronousSocketChannel channel;

    public ReadCompletionHandler(AsynchronousSocketChannel channel){
        if (this.channel == null) {
            this.channel = channel;
        }
    }
    public void completed(Integer result, ByteBuffer attachment) {
        handler(result, attachment);
    }

    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            this.channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handler (Integer service, ByteBuffer attachment) {
        attachment.flip();
        byte[] body = new byte[attachment.remaining()];
        attachment.get(body);
        try {
            ObjectInputStream input = new ObjectInputStream(new ByteArrayInputStream(body));
            try {
                String methodName = input.readUTF();
                Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
                Object[] arguments = (Object[]) input.readObject();
                Method method = service.getClass().getMethod(methodName, parameterTypes);
                Object result = method.invoke(service, arguments);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutput out = new ObjectOutputStream(baos);
                out.writeObject(result);

                byte[] resBytes = baos.toByteArray();

                ByteBuffer resByteBuffer = ByteBuffer.wrap(resBytes);
                channel.write(resByteBuffer, resByteBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                    public void completed(Integer result, ByteBuffer attachment) {
                        if (attachment.hasRemaining()) {
                            channel.write(attachment, attachment, this);
                        }
                    }

                    public void failed(Throwable exc, ByteBuffer attachment) {
                        try {
                            channel.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } finally {
                input.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
