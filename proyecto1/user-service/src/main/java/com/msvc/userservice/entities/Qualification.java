package com.msvc.userservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Qualification {

    private String id;
    private Long userId;
    private Long hotelId;
    private Integer qualification;
    private String observations;
    private Hotel hotel;
}
