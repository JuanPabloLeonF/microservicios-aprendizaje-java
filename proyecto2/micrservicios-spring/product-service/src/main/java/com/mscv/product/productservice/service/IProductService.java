package com.mscv.product.productservice.service;

import com.mscv.product.productservice.dto.ProductRequest;
import com.mscv.product.productservice.dto.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IProductService {
    void createProduct(ProductRequest productRequest);
    List<ProductResponse> getAllProducts();
}
