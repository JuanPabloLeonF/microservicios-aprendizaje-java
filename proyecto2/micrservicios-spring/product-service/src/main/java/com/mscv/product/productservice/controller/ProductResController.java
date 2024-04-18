package com.mscv.product.productservice.controller;

import com.mscv.product.productservice.dto.ProductRequest;
import com.mscv.product.productservice.dto.ProductResponse;
import com.mscv.product.productservice.service.IProductService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin("*")
public class ProductResController {
    private final IProductService iProductService;

    public ProductResController(IProductService iProductService) {
        this.iProductService = iProductService;
    }

    @GetMapping("/getAll")
    public @ResponseBody List<ProductResponse> getAll() {
        return iProductService.getAllProducts();
    }

    @PostMapping("/create")
    public @ResponseBody void createProduct(@RequestBody ProductRequest productRequest) {
        iProductService.createProduct(productRequest);
    }
}
