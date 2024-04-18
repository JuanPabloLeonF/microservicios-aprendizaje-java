package com.mscv.inventoryservice.service;

import java.util.List;

import com.mscv.inventoryservice.dto.InventoryResponse;
import com.mscv.inventoryservice.entity.Inventory;
import com.mscv.inventoryservice.mappers.IInventoryMapper;
import com.mscv.inventoryservice.repository.IInventoryRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceImplementation implements IInventoryService {

    private final IInventoryRepository inventoryRepository;
    private final IInventoryMapper iInventoryMapper;

    @Override
    @Transactional(readOnly = true)
    @SneakyThrows
    public ResponseEntity<List<InventoryResponse>> isInStock(List<String> stringList) {
        log.info("espera esta esperando");
        Thread.sleep(1000);
        log.info("espera terminada");
        List<Inventory> inventories = inventoryRepository.findByCodeSkuIn(stringList);
        List<InventoryResponse> inventoryResponses = inventories.stream()
                .map(iInventoryMapper::inventorytoInventoryResponse).toList();
        return new ResponseEntity<>(inventoryResponses, HttpStatus.OK);
    }
}
