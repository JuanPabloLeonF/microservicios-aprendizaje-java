package com.mcsv.qualificationservice.repository;

import com.mcsv.qualificationservice.entity.Qualification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IQualificationRepository extends MongoRepository<Qualification, String> {

    Optional<Qualification> findById(String id);

    List<Qualification> findByUserId(Long userId);
    List<Qualification> findByHotelId(Long hotelId);

}
