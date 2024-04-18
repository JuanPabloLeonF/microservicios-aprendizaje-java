package com.mscv.product.productservice.mappers;

import com.mscv.product.productservice.dto.ProductRequest;
import com.mscv.product.productservice.dto.ProductResponse;
import com.mscv.product.productservice.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IProductMapper {
    ProductResponse productEntityToProductResponse(ProductEntity productEntity);

    @Mapping(target = "id", ignore = true)
    ProductEntity productRequestToProductEntity(ProductRequest productRequest);
}
