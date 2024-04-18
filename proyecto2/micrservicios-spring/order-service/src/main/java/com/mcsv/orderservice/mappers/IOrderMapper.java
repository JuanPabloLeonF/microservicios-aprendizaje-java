package com.mcsv.orderservice.mappers;

import com.mcsv.orderservice.dto.OrderRequest;
import com.mcsv.orderservice.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface IOrderMapper {
    @Mappings({
            @Mapping(target = "numberOrder", expression = "java(java.util.UUID.randomUUID().toString())"),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "orderLineItems", source = "orderRequest.orderLineItemsDto")
    })
    OrderEntity orderRequestToOrderEntity(OrderRequest orderRequest);
}
