package com.example.hotel.controller;

import com.example.hotel.model.*;
import com.example.hotel.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HotelRepository hotelRepository;

    @BeforeEach
    public void setup() {
        hotelRepository.deleteAll();
    }

    @Test
    public void testGetAllHotels() throws Exception {
        Hotel hotel = new Hotel();
        hotel.setName("Test Hotel");
        hotel.setDescription("Test Description");
        hotel.setAddress(new Address(9, "Pobediteley Avenue", "Minsk", "Belarus", "220004"));
        hotel.setContacts(new Contacts("+375 17 309-80-00", "test@example.com"));
        hotelRepository.save(hotel);

        mockMvc.perform(MockMvcRequestBuilders.get("/property-view/hotels"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Hotel"))
                .andExpect(jsonPath("$[0].description").value("Test Description"))
                .andExpect(jsonPath("$[0].address").value("9 Pobediteley Avenue, Minsk, 220004, Belarus"))
                .andExpect(jsonPath("$[0].phone").value("+375 17 309-80-00"));
    }



    @Test
    public void testSearchHotelsByCity() throws Exception {
        Hotel hotel = new Hotel();
        hotel.setName("Test Hotel");
        hotel.setDescription("Test Description");
        hotel.setAddress(new Address(9, "Pobediteley Avenue", "Minsk", "Belarus", "220004"));
        hotel.setContacts(new Contacts("+375 17 309-80-00", "test@example.com"));
        hotelRepository.save(hotel);

        mockMvc.perform(MockMvcRequestBuilders.get("/property-view/search")
                        .param("city", "Minsk"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Hotel"))
                .andExpect(jsonPath("$[0].description").value("Test Description"))
                .andExpect(jsonPath("$[0].address").value("9 Pobediteley Avenue, Minsk, 220004, Belarus"))
                .andExpect(jsonPath("$[0].phone").value("+375 17 309-80-00"));
    }

    @Test
    public void testCreateHotel() throws Exception {
        String hotelJson = "{ \"name\": \"Test Hotel\", \"description\": \"Test Description\", " +
                "\"address\": { \"houseNumber\": 9, \"street\": \"Pobediteley Avenue\", \"city\": \"Minsk\", " +
                "\"country\": \"Belarus\", \"postCode\": \"220004\" }, " + // Убедитесь, что country задан
                "\"contacts\": { \"phone\": \"+375 17 309-80-00\", \"email\": \"test@example.com\" } }";

        mockMvc.perform(MockMvcRequestBuilders.post("/property-view/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(hotelJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Hotel"))
                .andExpect(jsonPath("$[0].description").value("Test Description"))
                .andExpect(jsonPath("$[0].address").value("9 Pobediteley Avenue, Minsk, 220004, Belarus")) // Проверка адреса
                .andExpect(jsonPath("$[0].phone").value("+375 17 309-80-00"));
    }

    @Test
    public void testAddAmenities() throws Exception {
        Hotel hotel = new Hotel();
        hotel.setName("Test Hotel");
        hotel.setAmenities(Collections.singletonList("Free WiFi"));
        hotelRepository.save(hotel);

        String amenitiesJson = "[\"Free Parking\"]";

        mockMvc.perform(MockMvcRequestBuilders.post("/property-view/hotels/{id}/amenities", hotel.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(amenitiesJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amenities.length()").value(2))
                .andExpect(jsonPath("$.amenities[0]").value("Free WiFi"))
                .andExpect(jsonPath("$.amenities[1]").value("Free Parking"));
    }

    @Test
    public void testGetHistogramByCity() throws Exception {
        Hotel hotel = new Hotel();
        hotel.setName("Test Hotel");
        hotel.setAddress(new Address(9, "Pobediteley Avenue", "Minsk", "Belarus", "220004"));
        hotelRepository.save(hotel);

        mockMvc.perform(MockMvcRequestBuilders.get("/property-view/histogram/city"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Minsk").value(1));
    }
}