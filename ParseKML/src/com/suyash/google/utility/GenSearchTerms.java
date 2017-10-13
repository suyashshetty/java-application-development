package com.suyash.google.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class GenSearchTerms {

	public static int cityIndex = 0;
	public static int termIndex = 0;
	public static String cIndex = "cityIndex=";
	public static String tIndex = "termIndex=";

	public GenSearchTerms() {

	}

	private void getIndex() {
		System.out.println("Initializing Search Term Generation Module");
		
		try {
			Scanner index = new Scanner(new File("indexes.txt"));
			while (index.hasNextLine()) {

				String line = index.nextLine();
				if (line.startsWith(cIndex))
					cityIndex = Integer.parseInt(line.replace(cIndex, ""));

				if (line.startsWith(tIndex))
					termIndex = Integer.parseInt(line.replace(tIndex, ""));

			}
			index.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getSearchTerm() {

		String srchCity = "";
		String srchTerm = "";

		getIndex();

		try {
			Scanner s = new Scanner(new File("terms.txt"));

			skipLines(s, termIndex);

			if (s.hasNextLine()) {
				srchTerm = s.nextLine();
				termIndex++;

				try {
					Scanner sx = new Scanner(new File("city.txt"));

					skipLines(sx, cityIndex);

					if (sx.hasNextLine()) {
						srchCity = sx.nextLine();
						sx.close();
					} else {
						sx.close();
						return null;
					}

				} catch (Exception e) {

					System.out.println(e);
				}
			} else {
				termIndex = 0;
				cityIndex++;
			}
			s.close();
		} catch (Exception e) {

			System.out.println(e);
		}

		exitGen();

		return srchTerm + " " + srchCity;
	}

	public static void skipLines(Scanner s, int lineNum) {
		for (int i = 0; i < lineNum; i++) {
			if (s.hasNextLine())
				s.nextLine();
		}
	}

	public void exitGen() {

		System.out.println("Exiting Search Term Generation Module");
		try {
			PrintWriter writer = new PrintWriter("indexes.txt");
			writer.println(tIndex + termIndex);
			writer.println(cIndex + cityIndex);
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		GenSearchTerms s = new GenSearchTerms();

		String searchTerm = "";
		do {
			searchTerm = s.getSearchTerm();
			System.out.println(searchTerm);
		} while (searchTerm != null);
	}
}
