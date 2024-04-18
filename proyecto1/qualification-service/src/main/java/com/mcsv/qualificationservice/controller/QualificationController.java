package com.mcsv.qualificationservice.controller;

import com.mcsv.qualificationservice.entity.Qualification;
import com.mcsv.qualificationservice.exception.ResourceExceptionFieldNotValid;
import com.mcsv.qualificationservice.exception.ResourceExceptionNotFound;
import com.mcsv.qualificationservice.service.IQualificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/qualifications")
@CrossOrigin("*")
public class QualificationController {

    private final IQualificationService iQualificationService;

    public QualificationController(IQualificationService iQualificationService) {
        this.iQualificationService = iQualificationService;
    }

    @GetMapping("/getAll")
    public @ResponseBody ResponseEntity<List<Qualification>> getAll() {
        return iQualificationService.getAll();
    }

    @GetMapping("/get/{id}")
    public @ResponseBody ResponseEntity<Qualification> get(@PathVariable("id") String id) throws ResourceExceptionNotFound {
        return iQualificationService.getById(id);
    }

    @GetMapping("/getByUser/{id}")
    public @ResponseBody ResponseEntity<List<Qualification>> getByUser(@PathVariable("id") Long id) throws ResourceExceptionNotFound {
        return iQualificationService.getQualificationByUserId(id);
    }

    @GetMapping("/getByHotel/{id}")
    public @ResponseBody ResponseEntity<List<Qualification>> getByHotel(@PathVariable("id") Long id) throws ResourceExceptionNotFound {
        return iQualificationService.getQualificationByHotelId(id);
    }

    @PostMapping("/save")
    public @ResponseBody ResponseEntity<Qualification> save(@RequestBody Qualification qualification) throws ResourceExceptionFieldNotValid {
        return iQualificationService.save(qualification);
    }

    @PutMapping("/update/{id}")
    public @ResponseBody ResponseEntity<Qualification> update(@PathVariable("id") String id, @RequestBody Qualification qualification) throws ResourceExceptionFieldNotValid, ResourceExceptionNotFound {
        return iQualificationService.updateById(qualification, id);
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody ResponseEntity<?> delete(@PathVariable("id") String id) throws ResourceExceptionNotFound {
        return iQualificationService.deleteById(id);
    }
}
