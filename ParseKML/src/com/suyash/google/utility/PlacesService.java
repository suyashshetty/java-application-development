package com.suyash.google.utility;

import se.walkercrou.places.GooglePlaces;
import se.walkercrou.places.Place;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import se.walkercrou.places.exception.NoResultsFoundException;
import se.walkercrou.places.exception.OverQueryLimitException;
import se.walkercrou.places.exception.GooglePlacesException;

public class PlacesService {

	private static final String LOG_TAG = "ExampleApp";
	
	private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";

	private static final String PLACES_LIBRARY = "http://maps.google.com/maps/api/js?sensor=false&libraries=places";

	private static final String TYPE_AUTOCOMPLETE = "/autocomplete";

	private static final String TYPE_DETAILS = "/details";

	private static final String TYPE_SEARCH = "/search";

	private static final String OUT_JSON = "/json";

	// KEY!

	public static List<Place> autocomplete(String input) {

		GoogleApiKeys keys = new GoogleApiKeys();
		int keyIndex = 1;
		String strApiKey = "";

		while (true) {
			try {
				strApiKey = keys.getKey(keyIndex);

				if (strApiKey == null)
					break;

				GooglePlaces client = new GooglePlaces(strApiKey);
				System.out.println();
				List<Place> places = client.getPlacesByQuery(input, GooglePlaces.MAXIMUM_RESULTS);

				return places;
			} catch (OverQueryLimitException ex) {
				System.out.println("Quota Over for :" + keyIndex + ": " + strApiKey);
				keyIndex++;
			} catch (se.walkercrou.places.exception.RequestDeniedException ex) {
				System.out.println("Request Denied for :" + keyIndex + ": " + strApiKey);
				keyIndex++;
			} catch (NoResultsFoundException ex) {
				System.out.println("No Results for :" + keyIndex + ": " + strApiKey);
				keyIndex++;
			} catch (GooglePlacesException ex) {
				System.out.println("Quota Over for :" + keyIndex + ": " + ex.getMessage());
				return new ArrayList<Place>();
			} // catch (InterruptedException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				// }
		}
		return null;

	}
}