package com.mcsv.hotelservice.service;

import com.mcsv.hotelservice.entity.Hotel;
import com.mcsv.hotelservice.exception.ResourceExceptionFieldNotValid;
import com.mcsv.hotelservice.exception.ResourceExceptionNotFound;
import com.mcsv.hotelservice.repository.IHotelRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelServiceImplementation implements IHotelService {

    private final IHotelRepository iHotelRepository;

    public HotelServiceImplementation(IHotelRepository iHotelRepository) {
        this.iHotelRepository = iHotelRepository;
    }

    @Override
    public ResponseEntity<List<Hotel>> getAll() {
        return new ResponseEntity<>(iHotelRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Hotel> findById(Long id) throws ResourceExceptionNotFound {
        Optional<Hotel> hotel = iHotelRepository.findById(id);
        if (hotel.isPresent()) {
            return new ResponseEntity<>(hotel.get(), HttpStatus.OK);
        } else {
            throw new ResourceExceptionNotFound("Hotel no encontrado con el id: " + id);
        }
    }

    @Override
    public ResponseEntity<Hotel> save(Hotel hotel) throws ResourceExceptionFieldNotValid {
        if (validateFields(hotel)) {
            return new ResponseEntity<>(iHotelRepository.save(hotel), HttpStatus.CREATED);
        } else {
            throw new ResourceExceptionFieldNotValid("Campos vacios");
        }
    }

    @Override
    public ResponseEntity<Hotel> updateById(Hotel hotel, Long id) throws ResourceExceptionNotFound, ResourceExceptionFieldNotValid {
        Optional<Hotel> hotelOptional = iHotelRepository.findById(id);
        if (hotelOptional.isPresent()) {
            if (validateFields(hotel)) {
                hotelOptional.get().setName(hotel.getName());
                hotelOptional.get().setInformation(hotel.getInformation());
                hotelOptional.get().setLocation(hotel.getLocation());
                return new ResponseEntity<>(iHotelRepository.save(hotelOptional.get()), HttpStatus.OK);
            } else {
                throw new ResourceExceptionFieldNotValid("Campos vacios");
            }
        } else {
            throw new ResourceExceptionNotFound("Hotel no encontrado con el id: " + id);
        }
    }

    @Override
    public ResponseEntity<Hotel> delete(Long id) throws ResourceExceptionNotFound {
        Optional<Hotel> hotel = iHotelRepository.findById(id);
        if (hotel.isPresent()) {
            iHotelRepository.delete(hotel.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new ResourceExceptionNotFound("Hotel no encontrado con el id: " + id);
        }
    }

    private static Boolean validateFields(Hotel hotel) {
        if (hotel.getName().isEmpty() || hotel.getName().isBlank()) {
            return Boolean.FALSE;
        } else if (hotel.getInformation().isEmpty() || hotel.getInformation().isBlank()) {
            return Boolean.FALSE;
        } else if (hotel.getLocation().isEmpty() || hotel.getLocation().isBlank()) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }
}
