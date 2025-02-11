package com.example.hotel.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArrivalTime {
    private String checkIn;
    private String checkOut;
}