package com.example.databaseProject.service.serviceImpl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.databaseProject.model.Hotel;
import com.example.databaseProject.repository.HotelRepository;

@Service
public class HotelServiceImpl {
	@Autowired
	HotelRepository hotelRepo;

	// create entry for hotel
	public Hotel addHotel(@Valid Hotel hotel) {
		return hotelRepo.save(hotel);
	}

	// read entry from hotel database using id
	public Hotel getHotelById(String hotelId) {
		return hotelRepo.findOne(hotelId);
	}

	// Retrieve all the entries of the database
	public List<Hotel> getAllHotels() {
		return hotelRepo.findAll();
	}

	// delete hotel from the database
	public void deleteHotel(String hotelId) {
		
		hotelRepo.delete(hotelRepo.findOne(hotelId));
		
	}
}
