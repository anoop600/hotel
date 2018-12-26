package com.example.databaseProject.service.serviceImpl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.databaseProject.model.Booking;
import com.example.databaseProject.repository.BookingRepository;

@Service
public class BookingServiceImpl {
	@Autowired
	BookingRepository bookingRepo;

	// create entry for Booking
	public Booking addBooking(@Valid Booking booking) {
		return bookingRepo.save(booking);
	}

	// read entry from Booking database using id
	public Booking getBookingById(int bookingId) {
		return bookingRepo.findOne(bookingId);
	}

	// Retrieve all the entries of the database
	public List<Booking> getAllBookings() {
		return bookingRepo.findAll();
	}

	// delete customer from the database
	public void deleteBooking(int bookingId) {

		bookingRepo.delete(bookingRepo.findOne(bookingId));

	}
}
