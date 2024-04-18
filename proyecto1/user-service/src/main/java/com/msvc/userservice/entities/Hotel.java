package com.msvc.userservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {
    private Long id;
    private String name;
    private String information;
    private String location;
}
