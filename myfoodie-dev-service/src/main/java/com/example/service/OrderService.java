package com.example.service;


import com.example.pojo.OrderStatus;
import com.example.pojo.bo.SubmitOrderBO;
import com.example.pojo.vo.OrderVO;

public interface OrderService {

    /**
     * 用于创建订单相关信息
     * @param submitOrderBO
     */
    OrderVO createOrder(SubmitOrderBO submitOrderBO);


    void updateOrderStatus(String merchantOrderId, Integer type);

    OrderStatus queryOrderStatusInfo(String orderId);
}
