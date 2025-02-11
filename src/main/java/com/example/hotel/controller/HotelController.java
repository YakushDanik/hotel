package com.example.hotel.controller;

import com.example.hotel.dto.HotelDTO;
import com.example.hotel.model.Hotel;
import com.example.hotel.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/property-view")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @Operation(summary = "Get all hotels", description = "Retrieves a list of all hotels")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of hotels retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HotelDTO.class)))
    })
    @GetMapping("/hotels")
    public List<HotelDTO> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @Operation(summary = "Get hotel by ID", description = "Retrieves details of a specific hotel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotel found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Hotel.class))),
            @ApiResponse(responseCode = "404", description = "Hotel not found")
    })
    @GetMapping("/hotels/{id}")
    public Hotel getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id);
    }

    @Operation(summary = "Search hotels by city", description = "Finds hotels based on city and optional filters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotels found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HotelDTO.class)))
    })
    @GetMapping("/search")
    public List<HotelDTO> searchHotels(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String amenity,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String country) {
        return hotelService.searchHotels(city, brand, amenity, name, country);
    }

    @Operation(summary = "Create a new hotel", description = "Adds a new hotel to the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Hotel created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Hotel.class)))
    })
    @PostMapping("/hotels")
    public List<HotelDTO> createHotel(@RequestBody Hotel hotel) {
        return hotelService.createHotel(hotel);
    }

    @Operation(summary = "Add amenities to a hotel", description = "Adds a list of amenities to an existing hotel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Amenities added",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Hotel.class))),
            @ApiResponse(responseCode = "404", description = "Hotel not found")
    })
    @PostMapping("/hotels/{id}/amenities")
    public Hotel addAmenities(@PathVariable Long id, @RequestBody List<String> amenities) {
        return hotelService.addAmenities(id, amenities);
    }

    @Operation(summary = "Get hotel count by city", description = "Returns a histogram of hotels by city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotel histogram retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class)))
    })
    @GetMapping("/histogram/{param}")
    public Map<String, Long> getHistogram(@PathVariable String param) {
        return hotelService.getHistogram(param);
    }
}