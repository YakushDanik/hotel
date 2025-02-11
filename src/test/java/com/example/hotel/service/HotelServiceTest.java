package com.example.hotel.service;

import com.example.hotel.dto.HotelDTO;
import com.example.hotel.model.*;
import com.example.hotel.repository.HotelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HotelService hotelService;

    @Test
    public void testGetAllHotels() {
        Hotel hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("Test Hotel");
        hotel.setDescription("Test Description");
        hotel.setAddress(new Address(9, "Pobediteley Avenue", "Minsk", "Belarus", "220004"));
        hotel.setContacts(new Contacts("+375 17 309-80-00", "test@example.com"));

        when(hotelRepository.findAll()).thenReturn(List.of(hotel));

        List<HotelDTO> result = hotelService.getAllHotels();

        assertEquals(1, result.size());
        assertEquals("Test Hotel", result.get(0).getName());
        assertEquals("Test Description", result.get(0).getDescription());
        assertEquals("9 Pobediteley Avenue, Minsk, 220004, Belarus", result.get(0).getAddress());
        assertEquals("+375 17 309-80-00", result.get(0).getPhone());
    }

    @Test
    public void testGetHotelById() {
        Hotel hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("Test Hotel");
        hotel.setDescription("Test Description");
        hotel.setAddress(new Address(9, "Pobediteley Avenue", "Minsk", "Belarus", "220004"));
        hotel.setContacts(new Contacts("+375 17 309-80-00", "test@example.com"));

        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));

        Hotel result = hotelService.getHotelById(1L);

        assertNotNull(result);
        assertEquals("Test Hotel", result.getName());
        assertEquals("Test Description", result.getDescription());

        Address address = result.getAddress();
        assertEquals(9, address.getHouseNumber());
        assertEquals("Pobediteley Avenue", address.getStreet());
        assertEquals("Minsk", address.getCity());
        assertEquals("Belarus", address.getCountry());
        assertEquals("220004", address.getPostCode());

        assertEquals("+375 17 309-80-00", result.getContacts().getPhone());
        assertEquals("test@example.com", result.getContacts().getEmail());
    }

    @Test
    public void testSearchHotelsByCity() {
        Hotel hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("Test Hotel");
        hotel.setDescription("Test Description");
        hotel.setAddress(new Address(9, "Pobediteley Avenue", "Minsk", "Belarus", "220004"));
        hotel.setContacts(new Contacts("+375 17 309-80-00", "test@example.com"));

        when(hotelRepository.findByCity("Minsk")).thenReturn(List.of(hotel));

        List<HotelDTO> result = hotelService.searchHotels("Minsk", null, null, null, null);

        assertEquals(1, result.size());
        assertEquals("Test Hotel", result.get(0).getName());
        assertEquals("Test Description", result.get(0).getDescription());
        assertEquals("9 Pobediteley Avenue, Minsk, 220004, Belarus", result.get(0).getAddress());
        assertEquals("+375 17 309-80-00", result.get(0).getPhone());
    }

    @Test
    public void testCreateHotel() {
        Hotel hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("Test Hotel");
        hotel.setDescription("Test Description");
        hotel.setAddress(new Address(9, "Pobediteley Avenue", "Minsk", "Belarus", "220004"));
        hotel.setContacts(new Contacts("+375 17 309-80-00", "test@example.com"));

        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);
        when(hotelRepository.findAll()).thenReturn(List.of(hotel));

        List<HotelDTO> result = hotelService.createHotel(hotel);

        assertEquals(1, result.size());
        assertEquals("Test Hotel", result.get(0).getName());
        assertEquals("Test Description", result.get(0).getDescription());
        assertEquals("9 Pobediteley Avenue, Minsk, 220004, Belarus", result.get(0).getAddress());
        assertEquals("+375 17 309-80-00", result.get(0).getPhone());
    }

    @Test
    public void testAddAmenities() {
        Hotel hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("Test Hotel");
        hotel.setAmenities(new ArrayList<>(Collections.singletonList("Free WiFi"))); // Используем изменяемый список

        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);

        Hotel result = hotelService.addAmenities(1L, List.of("Free Parking"));

        assertNotNull(result);
        assertEquals(2, result.getAmenities().size());
        assertTrue(result.getAmenities().contains("Free WiFi"));
        assertTrue(result.getAmenities().contains("Free Parking"));
    }
}