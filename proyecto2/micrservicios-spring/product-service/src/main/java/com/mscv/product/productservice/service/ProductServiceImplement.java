package com.mscv.product.productservice.service;

import java.util.List;
import java.util.stream.Collectors;

import com.mscv.product.productservice.mappers.IProductMapper;
import org.springframework.stereotype.Service;

import com.mscv.product.productservice.dto.ProductRequest;
import com.mscv.product.productservice.dto.ProductResponse;
import com.mscv.product.productservice.entity.ProductEntity;
import com.mscv.product.productservice.repository.IProductRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImplement implements IProductService {
    private final IProductRepository iProductRepository;
    private final IProductMapper iProductMapper;

    public ProductServiceImplement(IProductRepository iProductRepository, IProductMapper iProductMapper) {
        this.iProductRepository = iProductRepository;
        this.iProductMapper = iProductMapper;
    }

    @Override
    public void createProduct(ProductRequest productRequest) {
        ProductEntity productEntity = iProductRepository.save(iProductMapper.productRequestToProductEntity(productRequest));
        log.info("Product created successfully: {}", productEntity);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return iProductRepository.findAll().stream().map(iProductMapper::productEntityToProductResponse).toList();
    }
}
