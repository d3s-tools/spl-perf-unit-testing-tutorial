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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class PurchaseSummarySplTest {
	public static Iterable<Object[]> chunks(int records, int customers, int maxChunkSize) {
		List<Object[]> result = new ArrayList<>(records);
		
		Random customerGen = new Random(0);
		Random chunkGen = new Random(0);
		Random priceGen = new Random(0);
		Date date = new Date();
		int chunkSize = 0;
		String chunkCustomer = null;
		
		for (int i = 0; i < records; i++) {
			if (chunkSize == 0) {
				chunkSize = chunkGen.nextInt(maxChunkSize - 1) + 1;
				chunkCustomer = String.format("c%d", customerGen.nextInt(customers));
			}
			
			int amount = priceGen.nextInt(5) + 1;
			double price = priceGen.nextDouble() * 10;
			
			result.add(new Object[] { chunkCustomer, "itemXY", date, amount, price });
			
			chunkSize--;
		}
		
		return result;
	}
}