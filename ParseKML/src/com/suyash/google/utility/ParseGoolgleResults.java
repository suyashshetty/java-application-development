package com.suyash.google.utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import se.walkercrou.places.Place;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import se.walkercrou.places.exception.GooglePlacesException;

public class ParseGoolgleResults {

	public ParseGoolgleResults() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

		GenSearchTerms gen = new GenSearchTerms();
		try {

			/*
			 * System.out.println("Enter string to search on maps"); Scanner
			 * scan = new Scanner(System.in);
			 */

			String srchTerm = gen.getSearchTerm();
			while (srchTerm != null) {
				if (srchTerm.trim().length() > 0 && srchTerm.contains("|")) {

					String ipStr = srchTerm.substring(0, srchTerm.indexOf('|'));

					System.out.println(ipStr);

					List<Place> output = PlacesService.autocomplete(ipStr);
					if (output.size() == 0) {
						srchTerm = gen.getSearchTerm();
						continue;
					}
					if (null == output) {
						break;
					} // srchTerm = gen.getSearchTerm();continue;}
					String fileSrch = ipStr.replace(" ", "_") + "_";
					String csvFile = "D://Data//googlearth/GoogleAddr_" + fileSrch + System.currentTimeMillis()
							+ ".csv";
					File f = new File(csvFile);
					if (!f.exists())
						f.createNewFile();
					FileWriter writer = new FileWriter(csvFile, true);

					for (int i = 0; i < output.size(); i++) {

						Random random = new Random();

						TimeUnit.SECONDS.sleep((long) 0.5);
						try {
							Place opDetailed = output.get(i).getDetails();
							String address = opDetailed.getAddress();
							String addr = address.replace("India", "").trim();
							addr = addr.replace(", Maharashtra", "").trim();
							String[] tokens = addr.split("\\d{6}");

							if (addr.endsWith(",")) {
								System.out.println("Ends With commas");
								addr = addr.substring(0, addr.length() - 1);
							}

							// addr = addr + "|";
							String zipCode = addr.replace(tokens[0], "").trim();
							String city = addr.substring(addr.lastIndexOf(',') + 1).replace(zipCode, "");
							String district = srchTerm.substring(srchTerm.indexOf('|') + 1);

							addr = addr.replace(zipCode, "");
							addr = addr.replace(city, "");

							if (addr.endsWith(",")) {
								System.out.println("Ends With commas");
								addr = addr.substring(0, addr.length() - 1);
							}

							System.out.println("\nAddress (Orig) : " + address);
							System.out.println("Address (File) : " + addr);
							System.out.println("City : " + city.trim());
							System.out.println("District : + " + district.trim());
							System.out.println("ZipCode : " + zipCode);

							String lati = Double.toString(opDetailed.getLatitude());
							String longi = Double.toString(opDetailed.getLongitude());
							String coordinates = lati + "," + longi;
							System.out.println("Co-Ordinates : " + coordinates);
							System.out.println("Latitude : " + lati);
							System.out.println("Longitude : " + longi);

							List<String> list = new ArrayList<>();
							list.add(addr.replace("\n", ""));
							list.add(city.replace("\n", ""));
							list.add(district.replace("\n", ""));
							list.add(zipCode.replace("\n", ""));
							list.add(coordinates.replace("\n", ""));
							list.add(lati.replace("\n", ""));
							list.add(longi.replace("\n", ""));

							Address addrVal = new Address(addr, city, zipCode, coordinates, lati, longi);

							if (addrVal.isValid())
								CSVUtils.writeLine(writer, list);
							writer.flush();
						} catch (GooglePlacesException e) {

							continue;

						}
					}

					writer.close();

				}
				srchTerm = gen.getSearchTerm();
			}

		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

}
