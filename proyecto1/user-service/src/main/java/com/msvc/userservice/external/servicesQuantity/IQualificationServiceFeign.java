package com.msvc.userservice.external.servicesQuantity;

import com.msvc.userservice.entities.Qualification;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "qualification-service")
public interface IQualificationServiceFeign {
    @GetMapping("/qualifications/getByUser/{id}")
    ResponseEntity<List<Qualification>> getAllQualificationByIdUser(@PathVariable("id") Long idUser);

    @PostMapping("/qualifications/save")
    ResponseEntity<Qualification> saveQualification(@RequestBody Qualification qualification);

    @PutMapping("/qualifications/update/{id}")
    ResponseEntity<Qualification> updateQualificationById(@RequestBody Qualification qualification, @PathVariable("id") Long id);

    @DeleteMapping("/qualifications/delete/{id}")
    ResponseEntity<?> deleteQualificationById(@PathVariable("id") Long id);
}
