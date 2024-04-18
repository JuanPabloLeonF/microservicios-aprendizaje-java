package com.mcsv.hotelservice.repository;

import com.mcsv.hotelservice.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHotelRepository extends JpaRepository<Hotel, Long> {
}
