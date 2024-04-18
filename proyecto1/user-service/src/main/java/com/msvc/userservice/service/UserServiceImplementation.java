package com.msvc.userservice.service;

import com.msvc.userservice.entities.Hotel;
import com.msvc.userservice.entities.Qualification;
import com.msvc.userservice.entities.User;
import com.msvc.userservice.exception.ResourceExceptionFieldNotValid;
import com.msvc.userservice.exception.ResourceExceptionNotFound;
import com.msvc.userservice.external.servicesHotel.IHotelServiceFeign;
import com.msvc.userservice.repository.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImplementation implements IUserService {

    private Logger logger = LoggerFactory.getLogger(IUserService.class);
    private final RestTemplate restTemplate;
    private final IUserRepository iUserRepository;
    private final IHotelServiceFeign iHotelServiceFeign;

    public UserServiceImplementation(RestTemplate restTemplate, IUserRepository iUserRepository, IHotelServiceFeign iHotelServiceFeign) {
        this.restTemplate = restTemplate;
        this.iUserRepository = iUserRepository;
        this.iHotelServiceFeign = iHotelServiceFeign;
    }

    @Override
    public ResponseEntity<List<User>> getAll() {
        return new ResponseEntity<>(iUserRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> save(User user) throws ResourceExceptionNotFound {
        if (validateFields(user)) {
            return new ResponseEntity<>(iUserRepository.save(user), HttpStatus.CREATED);
        } else {
            throw new ResourceExceptionNotFound("Los campos no pueden estar vacios");
        }
    }

    @Override
    public ResponseEntity<?> deleteById(Long id) throws ResourceExceptionNotFound {
        Optional<User> user = iUserRepository.findById(id);
        if (user.isPresent()) {
            iUserRepository.delete(user.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new ResourceExceptionNotFound("No se encontro el usuario");
        }
    }

    @Override
    public ResponseEntity<User> findById(Long id) throws ResourceExceptionNotFound {
        Optional<User> user = iUserRepository.findById(id);
        if (user.isPresent()) {
            Qualification[] qualificationList = restTemplate.getForObject("http://qualification-service/qualifications/getByUser/" + user.get().getId(), Qualification[].class);
            List<Qualification> qualificationsConvertList = Arrays.stream(qualificationList).toList();
            List<Qualification> qualifications = qualificationsConvertList.stream().map(qualification -> {
                //ResponseEntity<Hotel> hotelEntity = restTemplate.getForEntity("http://hotel-service/hotels/get/" + qualification.getHotelId(), Hotel.class);
                ResponseEntity<Hotel> hotelResponseEntity = iHotelServiceFeign.getHotel(qualification.getHotelId());
                //logger.info("respuesta de estado: {}", hotel);
                qualification.setHotel(hotelResponseEntity.getBody());
                return qualification;
            }).collect(Collectors.toList());
            user.get().setQualifications(qualifications);
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            throw new ResourceExceptionNotFound("No se encontro el usuario");
        }
    }

    @Override
    public ResponseEntity<User> update(User user, Long id) throws ResourceExceptionNotFound, ResourceExceptionFieldNotValid {
        Optional<User> userDB = iUserRepository.findById(id);
        if (userDB.isPresent()) {
            if (validateFields(user)) {
                userDB.get().setName(user.getName());
                userDB.get().setEmail(user.getEmail());
                userDB.get().setInformation(user.getInformation());
                return new ResponseEntity<>(iUserRepository.save(userDB.get()), HttpStatus.OK);
            } else {
                throw new ResourceExceptionFieldNotValid("Los campos no pueden estar vacios");
            }
        } else {
            throw new ResourceExceptionNotFound("No se encontro el usuario");
        }
    }

    private static Boolean validateFields(User user) {
        if (user.getName().isEmpty() || user.getName().isBlank() ) {
            return Boolean.FALSE;
        } else if (user.getEmail().isEmpty() || user.getEmail().isBlank()) {
            return Boolean.FALSE;
        } else if (user.getInformation().isEmpty() || user.getInformation().isBlank()) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }
}
