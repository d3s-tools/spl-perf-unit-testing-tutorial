/*
 * Copyright 2013 Charles University in Prague
 * Copyright 2013 Vojtech Horky
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.cuni.mff.d3s.spl.tutorial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/** Utility functions to read purchases. */
public class PurchaseReader {
	/** When parsing from text file, we expect date in this format. */
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	/** Reads purchases from a semicolon/line separated text input.
	 * 
	 * The input contains customer login, bought item, date of the purchase,
	 * amount of items bought and price per single piece.
	 * 
	 * @param aggregator Where to store the parsed records.
	 * @param purchases Where to read the files from.
	 * 
	 * @throws IOException
	 */
	public static void read(final PurchaseAggregator aggregator, final Reader purchases) throws IOException {
		final BufferedReader reader = new BufferedReader(purchases);
		while (true) {
			final String line = reader.readLine();
			if (line == null) {
				break;
			}
			
			final String[] parts = line.split(";");
			if (parts.length < 5) {
				continue;
			}
			
			try {
				String customer = parts[0];
				String item = parts[1];
				Date date = dateFormat.parse(parts[2]);
				int amount = Integer.valueOf(parts[3]);
				double price = Double.valueOf(parts[4]);

				aggregator.add(customer, item, date, amount, price);
			} catch (NumberFormatException e) {
			} catch (ParseException e) {
			}
		}
	}
}
