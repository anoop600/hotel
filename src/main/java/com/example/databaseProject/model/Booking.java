package com.example.databaseProject.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Entity
public class Booking {

	@Id
	@NotNull
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int bookingId;
	
	@NotNull
	//@JsonSerialize(using = LocalDateTimeSerializer.class)
	//@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date checkIn;
	
	@NotNull
	//@JsonSerialize(using = LocalDateTimeSerializer.class)
	//@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date checkOut;
	
	@Min(1)
	@Max(5)
	@NotNull
	private int noOfRoomsBooked;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="customerId")
	private Customer customer;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="hotelId")
	private Hotel hotel;

	public int getBookingId() {
		return bookingId;
	}

	/*public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}*/

	public String getCheckIn() {
		return checkIn.toString();
	}

	public void setCheckIn(String checkIn) {
		this.checkIn = Date.valueOf(checkIn);
	}

	public String getCheckOut() {
		return checkOut.toString();
	}

	public void setCheckOut(String checkOut) {
		this.checkOut = Date.valueOf(checkOut);
	}

	public int getNoOfRoomsBooked() {
		return noOfRoomsBooked;
	}

	public void setNoOfRoomsBooked(int noOfRoomsBooked) {
		this.noOfRoomsBooked = noOfRoomsBooked;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	@Override
	public String toString() {
		return "Booking [bookingId=" + bookingId + ", checkIn=" + checkIn + ", checkOut=" + checkOut
				+ ", noOfRoomsBooked=" + noOfRoomsBooked + ", customer=" + customer + ", hotel=" + hotel + "]";
	}
	
	
	
}
