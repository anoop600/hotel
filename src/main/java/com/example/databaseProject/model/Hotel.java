package com.example.databaseProject.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.example.databaseProject.utility.Utility;

@Entity
@Table(name = "Hotels")
/*@EntityListeners(AuditingEntityListener.class)*/
public class Hotel {
	@Id
	@NotNull
	@Length(min = 5, max = 5)
	private String hotelId;

	@Length(min = 5, max = 15)
	@NotNull
	private String hotelName;

	@NotNull
	private Double tariff;

	@NotNull
	@Min(1)
	@Max(5)
	private int totalRooms;

	@Length(min = 3, max = 15)
	@NotNull
	private String hotelCity;

	@NotNull
	@Min(1)
	@Max(5)
	private int hotelRating;

	public Hotel() {
		super();
	}

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId() {
		this.hotelId = "H" + Utility.generateId("hotel");
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public Double getTariff() {
		return tariff;
	}

	public void setTariff(Double tariff) {
		this.tariff = tariff;
	}

	public int getTotalRooms() {
		return totalRooms;
	}

	public void setTotalRooms(int totalRooms) {
		this.totalRooms = totalRooms;
	}

	public String getHotelCity() {
		return hotelCity;
	}

	public void setHotelCity(String hotelCity) {
		this.hotelCity = hotelCity;
	}

	public int getHotelRating() {
		return hotelRating;
	}

	public void setHotelRating(int hotelRating) {
		this.hotelRating = hotelRating;
	}

	@Override
	public String toString() {
		return "Hotel [hotelId=" + hotelId + ", hotelName=" + hotelName + ", tariff=" + tariff + ", totalRooms="
				+ totalRooms + ", hotelCity=" + hotelCity + ", hotelRating=" + hotelRating + "]";
	}

}
