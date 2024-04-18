package com.mscv.inventoryservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mscv.inventoryservice.dto.InventoryResponse;
import com.mscv.inventoryservice.service.IInventoryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory")
@CrossOrigin("*")
public class InventoryRestController {

    private final IInventoryService inventoryService;

    @GetMapping("/check")
    public @ResponseBody ResponseEntity<List<InventoryResponse>>  isInStock(@RequestParam("codeSku") List<String> codeSku) {
        return inventoryService.isInStock(codeSku);
    }
}
