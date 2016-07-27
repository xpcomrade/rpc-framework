package com.xpcomrade.study.snpzrpc.util;

/**
 * Created by xpcomrade on 2016/7/27.
 * Copyright (c) 2016, xpcomrade@gmail.com All Rights Reserved.
 * Description: TODO(这里用一句话描述这个类的作用). <br/>
 */
public class Phone {
    private String series;
    private Double price;

    public Phone(String series, Double price) {
        this.series = series;
        this.price = price;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "series='" + series + '\'' +
                ", price=" + price +
                '}';
    }
}
