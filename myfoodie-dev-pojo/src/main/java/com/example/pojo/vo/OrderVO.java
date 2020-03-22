package com.example.pojo.vo;

import lombok.Data;

@Data
public class OrderVO {

    private String orderId;

    private MerchantOrdersVO merchantOrdersVO;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    }