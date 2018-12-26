package com.example.databaseProject.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.databaseProject.model.Hotel;
import com.example.databaseProject.service.serviceImpl.HotelServiceImpl;
import com.example.databaseProject.utility.LowestPriceHotel;


@RestController
@CrossOrigin("*")
@RequestMapping("/hotel")
public class HotelController {
	@Autowired
	HotelServiceImpl hotelService;
	// get hotel  by name and city
	@PostMapping(value="/getHotel", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Hotel getHotel(@RequestBody String hotelParam[]) {
		Optional<Hotel> hotelTest=hotelService.getAllHotels().stream().filter(hotel1->hotel1.getHotelName().equals(hotelParam[1])).filter(hotel2->hotel2.getHotelCity().equals(hotelParam[0])).findFirst();
		if(hotelTest.isPresent())
			return hotelTest.get();
		else
			return null;
	
		// response entity need to return from here
	}
	// request for making entry in table
	@PostMapping(value = "/addHotel", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Hotel addHotel(@RequestBody Hotel hotel) {
		hotel.setHotelId();
		return hotelService.addHotel(hotel);
		// response entity need to return from here
	}

	// get hotel entry by id
	@GetMapping(value = "/{hotelId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Hotel getHotelById(@PathVariable(value = "hotelId") String hotelId) {
		Hotel hotel = hotelService.getHotelById(hotelId);
		if (hotel != null) {
			return hotel;
		} else {
			// add response entity
			return null;
		}
	}

	// get all hotels
	@GetMapping(value = "/allHotels", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Hotel> getAllHotels() {
		return hotelService.getAllHotels();
	}

	// get id of hotel to delete the hotel from the table
	@DeleteMapping(value = "/{hotelId}")
	public void deleteHotelById(@PathVariable String hotelId) {
		hotelService.deleteHotel(hotelId);

	}

	// get all hotels from particular city
	@GetMapping(value = "/city/{hotelCity}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Hotel> getAllHotelFromCity(@PathVariable String hotelCity) {
		return hotelService.getAllHotels().stream().filter(hotel -> hotel.getHotelCity().equals(hotelCity))
				.collect(Collectors.toList());
	}

	// get all hotels by lowest tariff ad in particular city
	@GetMapping(value = "/byLowestPrice/{hotelCity}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Hotel> getAllHotelByLowestPrice(@PathVariable String hotelCity) {
	
		List<Hotel> hotelSet = hotelService.getAllHotels().stream().filter(hotel->hotel.getHotelCity().equals(hotelCity)).collect(Collectors.toList());
		Collections.sort(hotelSet, new LowestPriceHotel());
		return hotelSet;
	}

	// get hotel by name
	@GetMapping(value = "/getHotelByName/{hotelName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Hotel getHotelByName(@PathVariable String hotelName) {
		Optional<Hotel> hotelTest=hotelService.getAllHotels().stream().filter(hotel -> hotel.getHotelName().equals(hotelName)).findFirst();
		if(hotelTest.isPresent())
			return hotelTest.get();
		else
			return null;

	}

}
