package com.mscv.inventoryservice.service;

import com.mscv.inventoryservice.dto.InventoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IInventoryService {
    ResponseEntity<List<InventoryResponse>>  isInStock(List<String> stringList);
}
