package com.mcsv.orderservice.service;

import brave.Span;
import brave.Tracer;
import com.mcsv.orderservice.dto.InventoryResponse;
import com.mcsv.orderservice.dto.OrderLineItemsDto;
import com.mcsv.orderservice.dto.OrderRequest;
import com.mcsv.orderservice.entity.OrderEntity;
import com.mcsv.orderservice.mappers.IOrderMapper;
import com.mcsv.orderservice.repository.IOrderEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImplement implements IOrderService {

    private final IOrderEntityRepository iOrderEntityRepository;
    private final IOrderMapper iOrderMapper;
    private final WebClient.Builder webClientBuilder;
    private final Tracer tracer;

    @Override
    @Transactional(readOnly = false)
    public String placeOrder(OrderRequest orderRequest) {
        OrderEntity orderEntity = iOrderMapper.orderRequestToOrderEntity(orderRequest);

        List<String> codeSku = orderRequest.getOrderLineItemsDto().stream()
                .map(OrderLineItemsDto::getCodeSku).toList();

        System.out.println("codeSku = " + codeSku);

        Span inventoryServiceLookup = tracer.nextSpan().name("InventoryServiceLookup");

        try(Tracer.SpanInScope isLookUp = tracer.withSpanInScope(inventoryServiceLookup.start())) {
            inventoryServiceLookup.tag("call", "inventory-service");

            InventoryResponse[] inventoryResponses = webClientBuilder.build().get()
                    .uri("http://inventory-service/inventory/check", uriBuilder -> uriBuilder.queryParam("codeSku", codeSku).build())
                    .retrieve().bodyToMono(InventoryResponse[].class).block();

            Boolean allProductsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::getIsInStock);

            System.out.println("inventoryResponses = " + Arrays.toString(inventoryResponses));
            if (allProductsInStock) {
                iOrderEntityRepository.save(orderEntity);
                return "Order placed successfully";
            } else {
                throw new IllegalArgumentException("Product is not in stock, please try again later");
            }
        } finally {
            inventoryServiceLookup.flush();
        }
    }
}
