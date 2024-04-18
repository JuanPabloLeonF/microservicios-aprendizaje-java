package com.mcsv.orderservice.controller;

import com.mcsv.orderservice.dto.OrderRequest;
import com.mcsv.orderservice.service.IOrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/orders")
@CrossOrigin("*")
public class OrderRestController {
    private final IOrderService iOrderService;

    public OrderRestController(IOrderService iOrderService) {
        this.iOrderService = iOrderService;
    }

    @PostMapping("/placeOrder")
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public @ResponseBody CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest) {
        return CompletableFuture.supplyAsync(() -> iOrderService.placeOrder(orderRequest));
    }

    public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest, RuntimeException exception) {
        return CompletableFuture.supplyAsync(() -> "Ha ocurrido un erro al realizar un pedido");
    }
}
