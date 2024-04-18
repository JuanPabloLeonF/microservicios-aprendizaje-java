package com.mcsv.hotelservice.service;

import com.mcsv.hotelservice.entity.Hotel;
import com.mcsv.hotelservice.exception.ResourceExceptionFieldNotValid;
import com.mcsv.hotelservice.exception.ResourceExceptionNotFound;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface IHotelService {
    ResponseEntity<List<Hotel>> getAll();
    ResponseEntity<Hotel> findById(Long id) throws ResourceExceptionNotFound;
    ResponseEntity<Hotel> save(Hotel hotel) throws ResourceExceptionFieldNotValid;
    ResponseEntity<Hotel> updateById(Hotel hotel, Long id) throws ResourceExceptionNotFound, ResourceExceptionFieldNotValid;
    ResponseEntity<Hotel> delete(Long id) throws ResourceExceptionNotFound;
}
