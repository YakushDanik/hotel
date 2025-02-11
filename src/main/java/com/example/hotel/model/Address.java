package com.example.hotel.model;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Address {
    private int houseNumber;
    private String street;
    private String city;
    private String country;
    private String postCode;
}




