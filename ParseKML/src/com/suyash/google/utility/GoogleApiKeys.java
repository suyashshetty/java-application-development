package com.suyash.google.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GoogleApiKeys {

	private static List<String> googleApiKeys;
	private static int pIndex = 0;

	public GoogleApiKeys() {

		if (googleApiKeys == null || googleApiKeys.size() == 0) {

			googleApiKeys = new ArrayList<String>();
			Scanner scan;
			try {
				scan = new Scanner(new File("keys.txt"));

				while (scan.hasNextLine()) {
					googleApiKeys.add(scan.nextLine());
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public String getKey(int index) {

		if (index <= googleApiKeys.size()) {
			if ((index - 1) > pIndex)
				pIndex = index - 1;
			
			return googleApiKeys.get(pIndex);
		} else
			return null;

	}

}
