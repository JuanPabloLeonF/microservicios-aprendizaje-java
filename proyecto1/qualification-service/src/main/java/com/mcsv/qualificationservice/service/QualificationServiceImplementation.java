package com.mcsv.qualificationservice.service;

import com.mcsv.qualificationservice.exception.ResourceExceptionFieldNotValid;
import com.mcsv.qualificationservice.exception.ResourceExceptionNotFound;
import com.mcsv.qualificationservice.entity.Qualification;
import com.mcsv.qualificationservice.repository.IQualificationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QualificationServiceImplementation implements IQualificationService {

    private final IQualificationRepository iQualificationRepository;

    public QualificationServiceImplementation(IQualificationRepository iQualificationRepository) {
        this.iQualificationRepository = iQualificationRepository;
    }

    @Override
    public ResponseEntity<List<Qualification>> getAll() {
        return  new ResponseEntity<>(iQualificationRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Qualification> getById(String id) throws ResourceExceptionNotFound {
        Optional<Qualification> qualification = iQualificationRepository.findById(id);
        if (qualification.isPresent()) {
            return new ResponseEntity<>(qualification.get(), HttpStatus.OK);
        } else {
            throw new ResourceExceptionNotFound("Qualification no encontrado con el id: " + id);
        }
    }

    @Override
    public ResponseEntity<List<Qualification>> getQualificationByUserId(Long id) throws ResourceExceptionNotFound {
        return new ResponseEntity<>(iQualificationRepository.findByUserId(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Qualification>> getQualificationByHotelId(Long id) throws ResourceExceptionNotFound {
        return new ResponseEntity<>(iQualificationRepository.findByHotelId(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Qualification> save(Qualification qualification) throws ResourceExceptionFieldNotValid {
        if (validateFields(qualification)) {
            return new ResponseEntity<>(iQualificationRepository.save(qualification), HttpStatus.CREATED);
        } else {
            throw new ResourceExceptionFieldNotValid("Los campos no son validos");
        }
    }

    @Override
    public ResponseEntity<Qualification> updateById(Qualification qualification, String id) throws ResourceExceptionNotFound, ResourceExceptionFieldNotValid {
        Optional<Qualification> qualificationDb = iQualificationRepository.findById(id);
        if (qualificationDb.isPresent()) {
            if (validateFields(qualification)) {
                qualificationDb.get().setHotelId(qualification.getHotelId());
                qualificationDb.get().setUserId(qualification.getUserId());
                qualificationDb.get().setQualification(qualification.getQualification());
                qualificationDb.get().setObservations(qualification.getObservations());
                return new ResponseEntity<>(iQualificationRepository.save(qualificationDb.get()), HttpStatus.OK);
            } else {
                throw  new ResourceExceptionFieldNotValid("Los campos no son validos");
            }
        } else {
            throw new ResourceExceptionNotFound("Qualification no encontrado con el id: " + id);
        }
    }

    @Override
    public ResponseEntity<?> deleteById(String id) throws ResourceExceptionNotFound {
        Optional<Qualification> qualificationDb = iQualificationRepository.findById(id);
        if (qualificationDb.isPresent()) {
            iQualificationRepository.delete(qualificationDb.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new ResourceExceptionNotFound("Qualification no encontrado con el id: " + id);
        }
    }

    private static Boolean validateFields(Qualification qualification) {
        if (qualification.getHotelId() == null) {
            return Boolean.FALSE;
        } else if (qualification.getUserId() == null) {
            return Boolean.FALSE;
        } else if (qualification.getQualification() == null) {
            return Boolean.FALSE;
        } else if (qualification.getObservations().isEmpty() || qualification.getObservations().isBlank()) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }
}
