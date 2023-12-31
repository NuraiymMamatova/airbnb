package com.example.airbnbb7.db.repository;

import com.example.airbnbb7.db.entities.Booking;
import com.example.airbnbb7.dto.response.BookingResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("select new com.example.airbnbb7.dto.response.BookingResponse(b.id, b.price, b.checkIn, b.checkOut) from Booking b where b.id = :bookingId")
    Optional<BookingResponse> findBookingById(Long bookingId);

    @Query("select new com.example.airbnbb7.dto.response.BookingResponse(b.id, b.price, b.checkIn, b.checkOut) from Booking b where b.house.id = :houseId")
    List<BookingResponse> getBookingsByHouseId(Long houseId);

    @Query(value = "select users_id from booking_dates where id = :bookingId limit 1", nativeQuery = true)
    Long getUserIdByBookingId(Long bookingId);

    @Query("select b from Booking b where b.users.id = :userId")
    List<Booking> getBookingsByUserId(Long userId);

}