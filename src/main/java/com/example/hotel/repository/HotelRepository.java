package com.example.hotel.repository;

import com.example.hotel.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    @Query("SELECT h FROM Hotel h WHERE LOWER(h.address.city) = LOWER(:city)")
    List<Hotel> findByCity(@Param("city") String city);

    @Query("SELECT h FROM Hotel h WHERE LOWER(h.brand) = LOWER(:brand)")
    List<Hotel> findByBrand(@Param("brand") String brand);

    @Query("SELECT h FROM Hotel h WHERE LOWER(h.name) = LOWER(:name)")
    List<Hotel> findByName(@Param("name") String name);

    @Query("SELECT h FROM Hotel h WHERE LOWER(h.address.country) = LOWER(:country)")
    List<Hotel> findByCountry(@Param("country") String country);

    @Query("SELECT h FROM Hotel h JOIN h.amenities a WHERE LOWER(a) = LOWER(:amenity)")
    List<Hotel> findByAmenity(@Param("amenity") String amenity);

    @Query("SELECT h.brand, COUNT(h) FROM Hotel h GROUP BY h.brand")
    List<Object[]> countHotelsByBrand();

    @Query("SELECT h.address.city, COUNT(h) FROM Hotel h GROUP BY h.address.city")
    List<Object[]> countHotelsByCity();

    @Query("SELECT h.address.country, COUNT(h) FROM Hotel h GROUP BY h.address.country")
    List<Object[]> countHotelsByCountry();

    @Query("SELECT a, COUNT(h) FROM Hotel h JOIN h.amenities a GROUP BY a")
    List<Object[]> countHotelsByAmenities();

    @Query("SELECT h.name, COUNT(h) FROM Hotel h GROUP BY h.name")
    List<Object[]> countHotelsByName();
}