package com.suyash.google.utility;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class MergeAddresses {

	public MergeAddresses() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		try {

			File folder = new File("D://Data//googlearth");

			File[] listOfFiles = folder.listFiles();

			List<Address> addresses = new ArrayList<Address>();

			String csvFile = "D://Data//googlearth/aggr/addr" + System.currentTimeMillis() + ".csv";
			File f = new File(csvFile);
			if (!f.exists())
				f.createNewFile();
			PrintWriter writer = new PrintWriter(csvFile);
			// CSVUtils.writeLine(writer,
			// Arrays.asList("Address", "City", "ZipCode", "Co-Ordinates",
			// "Latitude", "Longitude"));

			for (int i = 0; i < listOfFiles.length; i++) {

				String filename = listOfFiles[i].getAbsolutePath();

				if (filename.endsWith(".csv") && filename.contains("GoogleAddr_")) {
					System.out.println("----------------------------");

					System.out.println(filename);
					System.out.println("----------------------------");

					File inputFile = new File(filename);
					Scanner scanfile = new Scanner(inputFile);
					while (scanfile.hasNextLine()) {
						writer.println(scanfile.nextLine());
						// System.out.println(scanfile.nextLine());
					}

				}
			}
			writer.flush();
			writer.close();

		} catch (

		Exception e) {

			e.printStackTrace();

		}

	}

}
