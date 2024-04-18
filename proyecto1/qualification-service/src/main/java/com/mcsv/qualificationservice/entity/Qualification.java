package com.mcsv.qualificationservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "qualifications")
public class Qualification {

    @Id
    private String id;
    private Long userId;
    private Long hotelId;
    private Integer qualification;
    private String observations;
}
