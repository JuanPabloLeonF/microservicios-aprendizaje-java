package com.mscv.inventoryservice.mappers;

import com.mscv.inventoryservice.dto.InventoryResponse;
import com.mscv.inventoryservice.entity.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface IInventoryMapper {

    @Mappings({
        @Mapping(target = "isInStock", expression = "java(inventory.getQuantity() > 0)"),
        @Mapping(target = "codeSku", source = "inventory.codeSku")
    })
    InventoryResponse inventorytoInventoryResponse(Inventory inventory);
}
