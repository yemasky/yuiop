package com.api.tour.supplier;

import java.util.HashMap;

public class TouricoSearchHotels {
	private String Destination;
	private String HotelCityName;
	private String HotelLocationName;
	private String HotelName;
	private String CheckIn;
	private String CheckOut;
	private String MaxPrice;
	private String StarLevel;
	private String AvailableOnly = "true";
	private String PropertyType = "NotSet";
	private String ExactDestination = "true";
	private HashMap<String, Object> RoomsInformation;
	
	public String getDestination() {
		return Destination;
	}
	public void setDestination(String destination) {
		Destination = destination;
	}
	public String getHotelCityName() {
		return HotelCityName;
	}
	public void setHotelCityName(String hotelCityName) {
		HotelCityName = hotelCityName;
	}
	public String getHotelLocationName() {
		return HotelLocationName;
	}
	public void setHotelLocationName(String hotelLocationName) {
		HotelLocationName = hotelLocationName;
	}
	public String getHotelName() {
		return HotelName;
	}
	public void setHotelName(String hotelName) {
		HotelName = hotelName;
	}
	public String getCheckIn() {
		return CheckIn;
	}
	public void setCheckIn(String checkIn) {
		CheckIn = checkIn;
	}
	public String getCheckOut() {
		return CheckOut;
	}
	public void setCheckOut(String checkOut) {
		CheckOut = checkOut;
	}
	public String getMaxPrice() {
		return MaxPrice;
	}
	public void setMaxPrice(String maxPrice) {
		MaxPrice = maxPrice;
	}
	public String getStarLevel() {
		return StarLevel;
	}
	public void setStarLevel(String starLevel) {
		StarLevel = starLevel;
	}
	public String getAvailableOnly() {
		return AvailableOnly;
	}
	public void setAvailableOnly(String availableOnly) {
		AvailableOnly = availableOnly;
	}
	public String getPropertyType() {
		return PropertyType;
	}
	public void setPropertyType(String propertyType) {
		PropertyType = propertyType;
	}
	public String getExactDestination() {
		return ExactDestination;
	}
	public void setExactDestination(String exactDestination) {
		ExactDestination = exactDestination;
	}
	public HashMap<String, Object> getRoomsInformation() {
		return RoomsInformation;
	}
	public void setRoomsInformation(HashMap<String, Object> roomsInformation) {
		RoomsInformation = roomsInformation;
	}

	
}
