package com.example.databaseProject.utility;

import java.util.Comparator;

import com.example.databaseProject.model.Hotel;

public class LowestPriceHotel implements Comparator<Hotel> {

	@Override
	public int compare(Hotel hotel1, Hotel hotel2) {
		return (int) (hotel1.getTariff() - hotel2.getTariff());
	}

}
