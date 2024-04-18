package com.mcsv.qualificationservice.service;

import com.mcsv.qualificationservice.exception.ResourceExceptionFieldNotValid;
import com.mcsv.qualificationservice.exception.ResourceExceptionNotFound;
import com.mcsv.qualificationservice.entity.Qualification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IQualificationService {
    ResponseEntity<List<Qualification>> getAll();
    ResponseEntity<Qualification> getById(String id) throws ResourceExceptionNotFound;
    ResponseEntity<List<Qualification>> getQualificationByUserId(Long id) throws ResourceExceptionNotFound;
    ResponseEntity<List<Qualification>> getQualificationByHotelId(Long id) throws ResourceExceptionNotFound;
    ResponseEntity<Qualification> save(Qualification qualification) throws ResourceExceptionFieldNotValid;
    ResponseEntity<Qualification> updateById(Qualification qualification, String id) throws ResourceExceptionNotFound, ResourceExceptionFieldNotValid;
    ResponseEntity<?> deleteById(String id) throws ResourceExceptionNotFound;
}
