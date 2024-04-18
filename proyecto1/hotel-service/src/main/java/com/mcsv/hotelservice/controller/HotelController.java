package com.mcsv.hotelservice.controller;

import com.mcsv.hotelservice.entity.Hotel;
import com.mcsv.hotelservice.exception.ResourceExceptionFieldNotValid;
import com.mcsv.hotelservice.exception.ResourceExceptionNotFound;
import com.mcsv.hotelservice.service.IHotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hotels")
@CrossOrigin("*")
public class HotelController {

    private final IHotelService iHotelService;

    public HotelController(IHotelService iHotelService) {
        this.iHotelService = iHotelService;
    }

    @GetMapping("getAll")
    public @ResponseBody ResponseEntity<List<Hotel>> getAll() {
        return iHotelService.getAll();
    }

    @GetMapping("get/{id}")
    public @ResponseBody ResponseEntity<Hotel> get(@PathVariable("id") Long id) throws ResourceExceptionNotFound {
        return iHotelService.findById(id);
    }

    @PostMapping("save")
    public @ResponseBody ResponseEntity<Hotel> save(@RequestBody  Hotel hotel) throws ResourceExceptionFieldNotValid {
        return iHotelService.save(hotel);
    }

    @PutMapping("update/{id}")
    public @ResponseBody ResponseEntity<Hotel> update(@PathVariable("id") Long id, @RequestBody  Hotel hotel) throws ResourceExceptionFieldNotValid, ResourceExceptionNotFound {
        return iHotelService.updateById(hotel, id);
    }

    @DeleteMapping("delete/{id}")
    public @ResponseBody ResponseEntity<Hotel> delete(@PathVariable("id") Long id) throws ResourceExceptionNotFound {
        return iHotelService.delete(id);
    }
}
