package com.suyash.google.utility;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class ParseKml {

	public ParseKml() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		try {

			File folder = new File("D://Data//googlearth");

			File[] listOfFiles = folder.listFiles();

			List<Address> addresses = new ArrayList<Address>();

			String csvFile = "D://Data//googlearth/addr.csv";
			File f = new File(csvFile);
			if (!f.exists())
				f.createNewFile();
			FileWriter writer = new FileWriter(csvFile, true);
			// CSVUtils.writeLine(writer,
			// Arrays.asList("Address", "City", "ZipCode", "Co-Ordinates",
			// "Latitude", "Longitude"));

			for (int i = 0; i < listOfFiles.length; i++) {

				String filename = listOfFiles[i].getAbsolutePath();

				if (filename.endsWith(".kml") || filename.endsWith(".KML")) {
					System.out.println("----------------------------");

					System.out.println(filename);
					System.out.println("----------------------------");

					File inputFile = new File(filename);

					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

					Document doc = dBuilder.parse(inputFile);

					doc.getDocumentElement().normalize();

					System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

					NodeList nList = doc.getElementsByTagName("Placemark");

					System.out.println("----------------------------");

					for (int temp = 0; temp < nList.getLength(); temp++) {

						Node nNode = nList.item(temp);

						if (nNode.getNodeType() == Node.ELEMENT_NODE) {

							Element eElement = (Element) nNode;

							System.out.println("\nName : "

									+ eElement.getElementsByTagName("name").item(0).getTextContent());

							String address = eElement.getElementsByTagName("address").item(0).getTextContent();
							String[] tokens = address.split("\\d{6}");
							String addr = tokens[0].replace(", Maharashtra", "").trim();
							if (addr.endsWith(",")) {
								System.out.println("Ends With commas");
								addr = addr.substring(0, addr.length() - 1);
							}
							// addr = addr + "|";
							String city = addr.substring(addr.lastIndexOf(',') + 1);
							String zipCode = address.replace(tokens[0], "").trim();
							System.out.println("Address : " + addr);
							System.out.println("City : " + city.trim());
							System.out.println("ZipCode : " + zipCode);

							String coordinates = eElement.getElementsByTagName("coordinates").item(0).getTextContent();
							coordinates = coordinates.substring(0, coordinates.lastIndexOf(",") - 1);
							String lati = coordinates.split(",")[0];
							String longi = coordinates.split(",")[1];
							System.out.println("Co-Ordinates : " + coordinates);
							System.out.println("Latitude : " + lati);
							System.out.println("Longitude : " + longi);

							List<String> list = new ArrayList<>();
							list.add(addr.replace("\n", ""));
							list.add(city.replace("\n", ""));
							list.add(zipCode.replace("\n", ""));
							list.add(coordinates.replace("\n", ""));
							list.add(lati.replace("\n", ""));
							list.add(longi.replace("\n", ""));
							
							Address addrVal = new Address(address, city, zipCode, coordinates, lati, longi);
							
							if (addrVal.isValid())
								CSVUtils.writeLine(writer, list);
							writer.flush();

						}
					}

				}
			}

			writer.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

}
