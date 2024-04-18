package com.msvc.userservice.service;

import com.msvc.userservice.entities.User;
import com.msvc.userservice.exception.ResourceExceptionFieldNotValid;
import com.msvc.userservice.exception.ResourceExceptionNotFound;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserService {
    ResponseEntity<List<User>> getAll();
    ResponseEntity<User> save(User user) throws ResourceExceptionNotFound;
    ResponseEntity<?> deleteById(Long id) throws ResourceExceptionNotFound;
    ResponseEntity<User> findById(Long id) throws ResourceExceptionNotFound;
    ResponseEntity<User> update(User user, Long id) throws ResourceExceptionNotFound, ResourceExceptionFieldNotValid;
}
