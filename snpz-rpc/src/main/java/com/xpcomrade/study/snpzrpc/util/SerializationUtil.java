package com.xpcomrade.study.snpzrpc.util;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xpcomrade on 2016/7/25.
 * Copyright (c) 2016, xpcomrade@gmail.com All Rights Reserved.
 * Description: TODO(Protostuff序列化与反序列化工具). <br/>
 */
public class SerializationUtil {

    private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<>();

    private static Objenesis objenesis = new ObjenesisStd(true);

    private SerializationUtil(){

    }

    private static <T> Schema<T> getSchema(Class<T> tClass) {
        Schema<T> schema = (Schema<T>)cachedSchema.get(tClass);
        if (schema == null) {
            schema = RuntimeSchema.createFrom(tClass);
            if (schema != null) {
                cachedSchema.put(tClass, schema);
            }
        }

        return schema;
    }

    public static <T> byte[] serialize(T obj) {
        Class<T> cls = (Class<T>)obj.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema<T> schema = getSchema(cls);
            return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            buffer.clear();
        }
    }

    public static <T> T deserialize(byte[] data, Class<T> cls) {
        try {
            T message = (T) objenesis.newInstance(cls);
            Schema<T> schema = getSchema(cls);
            ProtostuffIOUtil.mergeFrom(data, message, schema);
            return message;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }




}
