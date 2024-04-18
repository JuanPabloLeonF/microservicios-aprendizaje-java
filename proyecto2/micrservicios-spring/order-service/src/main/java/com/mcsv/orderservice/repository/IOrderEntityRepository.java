package com.mcsv.orderservice.repository;

import com.mcsv.orderservice.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderEntityRepository extends JpaRepository<OrderEntity, Long> {
}