package com.msvc.userservice.controller;

import com.msvc.userservice.entities.User;
import com.msvc.userservice.exception.ResourceExceptionFieldNotValid;
import com.msvc.userservice.exception.ResourceExceptionNotFound;
import com.msvc.userservice.service.IUserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

@Slf4j
@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {

    private final IUserService iUserService;

    public UserController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @GetMapping("/getAll")
    public @ResponseBody ResponseEntity<List<User>> getAll() {
        return iUserService.getAll();
    }

    @GetMapping("/get/{id}")
    //@CircuitBreaker(name = "ratingHotelService", fallbackMethod = "ratingHotelFallBack")
    @Retry(name = "ratingHotelServiceRetry", fallbackMethod = "ratingHotelFallBack")
    public @ResponseBody ResponseEntity<User> findById(@PathVariable("id") Long id) throws ResourceExceptionNotFound {
        return iUserService.findById(id);
    }

    @PostMapping("/save")
    public @ResponseBody ResponseEntity<User> save(@RequestBody User user) throws ResourceExceptionNotFound {
        return iUserService.save(user);
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody ResponseEntity<?> deleteById(@PathVariable("id") Long id) throws ResourceExceptionNotFound {
        return iUserService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public @ResponseBody ResponseEntity<User> update(@RequestBody User user, @PathVariable("id") Long id) throws ResourceExceptionNotFound, ResourceExceptionFieldNotValid {
        return iUserService.update(user, id);
    }

    public @ResponseBody ResponseEntity<User> ratingHotelFallBack(Long id, Exception exception) {
        log.info("El respaldo se ejecuta por que el servicio esta inactivo: {}", exception.getMessage());
        User user = new User();
        user.setEmail("root1@gmail.com");
        user.setName("root1");
        user.setInformation("este usuario se crea por defecto cuando un servicio se cae");
        user.setId(1234L);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
