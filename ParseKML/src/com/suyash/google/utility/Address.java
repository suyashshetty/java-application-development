package com.suyash.google.utility;

public class Address {

	private String address;
	private String city;
	private String zipcode;
	private String coordinates;
	private String latitude;
	private String longitude;

	public Address(String address, String city, String zipcode, String coordinates, String latitude, String longitude) {

		this.address = address;
		this.city = city;
		this.zipcode = zipcode;
		this.coordinates = coordinates;
		this.latitude = latitude;
		this.longitude = longitude;

	}

	public boolean isValid() {

		if (this.address.contains("?"))
			return false;

		if (address.isEmpty() || zipcode.isEmpty() || city.isEmpty() || coordinates.isEmpty())
			return false;
		
		//if (city.contains("[a-zA-Z]+") == false)
		//	return false;
		
		if (!zipcode.matches("[0-9]+")) 
			return false;

		return true;
	}

}
