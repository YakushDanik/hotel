package com.example.hotel.dto;

import lombok.Data;

@Data
public class HotelDTO {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String phone;
}
