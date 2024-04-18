package com.msvc.userservice.external.servicesHotel;

import com.msvc.userservice.entities.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "hotel-service")
public interface IHotelServiceFeign {
    @GetMapping("/hotels/get/{id}")
    ResponseEntity<Hotel> getHotel(@PathVariable("id") Long idHotel);
}
