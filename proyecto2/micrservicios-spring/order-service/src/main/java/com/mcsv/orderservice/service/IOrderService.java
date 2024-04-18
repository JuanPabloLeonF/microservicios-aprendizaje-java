package com.mcsv.orderservice.service;

import com.mcsv.orderservice.dto.OrderRequest;
import org.springframework.stereotype.Service;

@Service
public interface IOrderService {
    String placeOrder(OrderRequest orderRequest);
}
