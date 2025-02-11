package com.example.hotel.model;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Contacts {
    private String phone;
    private String email;
}
