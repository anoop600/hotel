package com.example.databaseProject.controller;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.databaseProject.model.Booking;
import com.example.databaseProject.model.Customer;
import com.example.databaseProject.model.Hotel;
import com.example.databaseProject.service.serviceImpl.BookingServiceImpl;
import com.example.databaseProject.service.serviceImpl.CustomerServiceImpl;
import com.example.databaseProject.service.serviceImpl.HotelServiceImpl;


@RestController
@CrossOrigin("*")
@RequestMapping("/booking")
public class BookingController {

	@Autowired
	BookingServiceImpl bookingService;
	@Autowired
	CustomerServiceImpl customerService;
	@Autowired
	HotelServiceImpl hotelService;

	@PostMapping(value="/verify", consumes= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE},produces= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Booking> verfiyDetails(@RequestBody String checkParam[]) {
		 Hotel hotel=null;
		Customer customer=null;
		
		Optional<Hotel> hotelTest=hotelService.getAllHotels().stream().filter(hotel1->hotel1.getHotelName().equals(checkParam[0])).filter(hotel2->hotel2.getHotelCity().equals(checkParam[1])).findFirst();
		if(hotelTest.isPresent()) {
			hotel=hotelTest.get();
		}else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
			
		Optional<Customer> customerTest=customerService.getAllCustomers().stream().filter(cust1->cust1.getEmail().equals(checkParam[2])).findFirst();
		if(customerTest.isPresent()) {
			customer=customerTest.get();
		}
		else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		Booking booking=null;
		
		int count = 0;
		//check if rooms are available
		if(hotel.getTotalRooms()>=Integer.parseInt(checkParam[6])) {
			String hid=hotel.getHotelId();
			List<Booking> bk=bookingService.getAllBookings().stream().filter(booking1->booking1.getHotel().getHotelId().equals(hid)).collect(Collectors.toList());
			bk.forEach(System.out::println);
			if(bk.size()==0) {
				booking= new Booking();
				booking.setCheckIn(checkParam[4]);
				booking.setCheckOut(checkParam[5]);
				booking.setNoOfRoomsBooked(Integer.parseInt(checkParam[6]));
				booking.setCustomer(customer);
				booking.setHotel(hotel);
				return new ResponseEntity<>(booking, HttpStatus.OK);
			}
			count=hotel.getTotalRooms();
			for(Booking b:bk) {
				
				System.out.println(count);
				count=count-b.getNoOfRoomsBooked();
				System.out.println(count);
				System.out.println();
				
				long duration=Date.valueOf(checkParam[4]).getTime()-Date.valueOf(b.getCheckOut()).getTime();
				long diffInDays = TimeUnit.MILLISECONDS.toDays(duration);
				System.out.println(diffInDays);
				if(diffInDays>0) {
					count=count+b.getNoOfRoomsBooked();
					System.out.println(count);
					
				}else {
					
					//occupiedRooms+=b.getNoOfRoomsBooked();
					
				}
			}
			if(count>=Integer.parseInt(checkParam[6])) {
				//make entry in booking table
				
//				if(occupiedRooms<=hotel.getTotalRooms()) {
//					System.out.println(occupiedRooms+emptyRooms);
//					System.out.println(hotel.getTotalRooms());
				booking= new Booking();
				booking.setCheckIn(checkParam[4]);
				booking.setCheckOut(checkParam[5]);
				booking.setNoOfRoomsBooked(Integer.parseInt(checkParam[6]));
				booking.setCustomer(customer);
				booking.setHotel(hotel);
				//bookingService.addBooking(booking);

				return new ResponseEntity<>(booking, HttpStatus.OK);
//				}
//				else {
//					return new ResponseEntity<>("room not booked", HttpStatus.BAD_REQUEST);
//					 
//				}
			}
			else {
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
 
			}
		}else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			 }
		
	
	}
	// request for making entry in table
	@PostMapping(value = "/addBooking/{hotelId}/{customerId}", consumes= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE},produces= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public String addBooking(@PathVariable(value = "customerId") String customerId,@PathVariable(value = "hotelId") String hotelId,@RequestBody Booking booking) {
		booking.setCustomer(customerService.getCustomerById(customerId));
		booking.setHotel(hotelService.getHotelById(hotelId));
		if(bookingService.addBooking(booking)!=null) {
			return "inserted successfully";
		}else {
			return "insertion unsuccessfull";
		}
		// response entity need to return from here
	}
	
	// request for making entry in table
		@PostMapping(value = "/addBooking",consumes= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE},produces= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
		public ResponseEntity<String> addBookingObj(@RequestBody Booking booking) {
			if(bookingService.addBooking(booking)!=null) {
				return new ResponseEntity<>("inserted successfully",HttpStatus.OK);
			}else {
				return new ResponseEntity<>("insertion unsuccessfull",HttpStatus.BAD_REQUEST);
				
			}
			// response entity need to return from here
		}

	// get booking entry by id
	@GetMapping(value = "/{bookingId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Booking getBookingById(@PathVariable(value = "bookingId") int bookingId) {
		Booking booking = bookingService.getBookingById(bookingId);
		if (booking != null) {
			return booking;
		} else {
			// add response entity
			return null;
		}
	}

	// get all booking
	@GetMapping(value = "/allBooking", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Booking> getAllBookings() {
		return bookingService.getAllBookings();
	}

	// get id of booking to delete the customer from the table
	@DeleteMapping(value = "/{bookingId}")
	public void deleteBookingById(@PathVariable int bookingId) {
		bookingService.deleteBooking(bookingId);

	}

}
