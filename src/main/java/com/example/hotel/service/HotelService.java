package com.example.hotel.service;

import com.example.hotel.dto.HotelDTO;
import com.example.hotel.model.Address;
import com.example.hotel.model.Hotel;
import com.example.hotel.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;

    public List<HotelDTO> getAllHotels() {
        return hotelRepository.findAll().stream()
                .map(this::convertToShortInfo)
                .collect(Collectors.toList());
    }

    private HotelDTO convertToShortInfo(Hotel hotel) {
        HotelDTO shortInfo = new HotelDTO();
        shortInfo.setId(hotel.getId());
        shortInfo.setName(hotel.getName());
        shortInfo.setDescription(hotel.getDescription());

        Address address = hotel.getAddress();
        if (address != null) {
            String country = address.getCountry() != null ? address.getCountry() : ""; // Проверка на null
            String formattedAddress = String.format("%d %s, %s, %s, %s",
                    address.getHouseNumber(),
                    address.getStreet(),
                    address.getCity(),
                    address.getPostCode(),
                    country);
            shortInfo.setAddress(formattedAddress);
        } else {
            shortInfo.setAddress(null);
        }

        shortInfo.setPhone(hotel.getContacts().getPhone());
        return shortInfo;
    }

    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
    }
    public List<HotelDTO> searchHotels(String city, String brand, String amenity, String name, String country) {
        if (city != null) {
            return hotelRepository.findByCity(city)
                    .stream()
                    .map(this::convertToShortInfo)
                    .collect(Collectors.toList());
        } else if (brand != null) {
            return hotelRepository.findByBrand(brand)
                    .stream()
                    .map(this::convertToShortInfo)
                    .collect(Collectors.toList());
        } else if (amenity != null) {
            return hotelRepository.findByAmenity(amenity)
                    .stream()
                    .map(this::convertToShortInfo)
                    .collect(Collectors.toList());
        } else if(name !=null){
            return hotelRepository.findByName(name)
                    .stream()
                    .map(this::convertToShortInfo)
                    .collect(Collectors.toList());
        }else if(country !=null){
            return hotelRepository.findByCountry(country)
                    .stream()
                    .map(this::convertToShortInfo)
                    .collect(Collectors.toList());
        }
        return hotelRepository.findAll().stream()
                .map(this::convertToShortInfo)
                .collect(Collectors.toList());
    }

    public List<HotelDTO> createHotel(Hotel hotel) {
        hotelRepository.save(hotel);
        return hotelRepository.findAll().stream()
                .map(this::convertToShortInfo)
                .collect(Collectors.toList());
    }

    public Hotel addAmenities(Long id, List<String> amenities) {
        Hotel hotel = getHotelById(id);
        hotel.getAmenities().addAll(amenities);
        return hotelRepository.save(hotel);
    }

    public Map<String, Long> getHistogram(String param) {
        return switch (param) {
            case "name" -> hotelRepository.countHotelsByName().stream()
                    .collect(Collectors.toMap(
                            arr-> (String) arr[0],
                            arr -> ((Number) arr[1]).longValue()
                    ));
            case "brand" -> hotelRepository.countHotelsByBrand().stream()
                    .collect(Collectors.toMap(
                            arr -> (String) arr[0],
                            arr -> ((Number) arr[1]).longValue()
                    ));
            case "city" -> hotelRepository.countHotelsByCity().stream()
                    .collect(Collectors.toMap(
                            arr -> (String) arr[0],
                            arr -> ((Number) arr[1]).longValue()
                    ));
            case "country" -> hotelRepository.countHotelsByCountry().stream()
                    .collect(Collectors.toMap(
                            arr -> (String) arr[0],
                            arr -> ((Number) arr[1]).longValue()
                    ));
            case "amenities" -> hotelRepository.countHotelsByAmenities().stream()
                    .collect(Collectors.toMap(
                            arr -> (String) arr[0],
                            arr -> ((Number) arr[1]).longValue()
                    ));
            default -> throw new IllegalArgumentException("Unsupported parameter: " + param);
        };
    }

}

