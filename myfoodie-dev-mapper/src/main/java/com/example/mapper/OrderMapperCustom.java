package com.example.mapper;

import com.example.pojo.OrderStatus;
import com.example.pojo.vo.MyOrdersVO;
import com.example.pojo.vo.OrderVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface OrderMapperCustom {
    List<MyOrdersVO> queryMyOrders(@Param("paramsMap") Map<String,Object> map);
    int getMyOrderStatusCounts(@Param("paramsMap") Map<String, Object> map);

    List<OrderStatus> getMyOrderTrend(@Param("paramsMap") Map<String, Object> map);
}
